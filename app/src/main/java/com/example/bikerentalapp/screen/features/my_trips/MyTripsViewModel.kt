package com.example.bikerentalapp.screen.features.my_trips

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikerentalapp.api.data.TripDetails
import com.example.bikerentalapp.api.network.RetrofitInstances
import com.example.bikerentalapp.model.AccountViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class MyTripsViewModel(accountViewModel: AccountViewModel) : ViewModel() {
    private val _tripDetailsList = MutableStateFlow<List<TripDetails>>(emptyList())
    val tripDetailsList = _tripDetailsList.asStateFlow()

    init {
        viewModelScope.launch {
            val response = RetrofitInstances.Query(accountViewModel.token.value).queryAPI.getTrips(accountViewModel.username.value)
            if(response.isSuccessful) {
                _tripDetailsList.update { response.body()?.data ?: emptyList() }
                Log.d("MyTripsViewModel", "${response.body()?.data}")
            }else{
                Log.d("MyTripsViewModel", "${response.errorBody()?.string()}")
            }
        }
    }

    fun formatTripTime(startTime: String, endTime: String): String {
        val startDateTime = OffsetDateTime.parse(startTime)
        val endDateTime = OffsetDateTime.parse(endTime)
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        val startFormattedTime = startDateTime.format(timeFormatter)
        val endFormattedTime = endDateTime.format(timeFormatter)
        val formattedDate = startDateTime.format(dateFormatter)

        return "$startFormattedTime - $endFormattedTime, $formattedDate"
    }

    fun decodePolyline(encodedPolyline: String):String{
        val decodedPolyline = PolyUtil.decode(encodedPolyline)
        return Json.encodeToString(decodedPolyline.map { it.toSerializable() })
    }
}