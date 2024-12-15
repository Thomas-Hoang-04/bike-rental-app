package com.example.bikerentalapp.screen.main.station

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikerentalapp.api.data.NearbyStation
import com.example.bikerentalapp.api.data.Station
import com.example.bikerentalapp.api.network.RetrofitInstances
import com.example.bikerentalapp.model.AccountViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class GoogleMapViewModel(private val accountViewModel: AccountViewModel) : ViewModel() {

    //Example of list of stations
    var stations: List<Station> = emptyList()
    private val _locationPermissionGranted = MutableStateFlow(false)
    val locationPermissionGranted = _locationPermissionGranted.asStateFlow()
    private val _searchState = MutableStateFlow(SearchStationState())
    val searchState = _searchState.asStateFlow()
    private val _currentUserLocation = MutableStateFlow(NearbyStation(21.05154541,105.82423695,5000.0))
    private var filteredStations: List<Station> = emptyList()

    init {
        viewModelScope.launch {
            val response = RetrofitInstances.Query(accountViewModel.token.value).queryAPI.getStations()
            if(response.isSuccessful){
                try {
                    stations = response.body()?.data!!
                }catch (e: Exception){
                    Log.d("GoogleMapViewModel", "Error: ${e.message}")
                }
            }else{
                Log.d("GoogleMapViewModel", "Error: ${response.errorBody()}")
            }
        }
    }

    fun setLocationPermissionGranted(granted: Boolean) {
        _locationPermissionGranted.value = granted
    }

    fun searchStation(query: String) {
        if(query != ""){
            filteredStations = stations.filter {
                it.name.lowercase(Locale.getDefault()).contains(query) || it.address.lowercase(Locale.getDefault()).contains(query)
            }
            _searchState.update {
                it.copy(stationList = filteredStations)
            }
        }else{
            _searchState.value = SearchStationState(null)
        }
    }

    fun selectDevice(selectedDevice: Station, cameraPositionState: CameraPositionState, coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(
                    LatLng(selectedDevice.coordinates.lat, selectedDevice.coordinates.lng),
                    17f
                ),
                durationMs = 1000,
            )
        }
        _searchState.value = SearchStationState(null)
    }

    fun updateCurrentUserLocation(latitude: Double, longitude: Double){
        _currentUserLocation.update {
            it.copy(latitude = latitude, longitude = longitude)
        }
    }

    fun getNearbyStation(){
        viewModelScope.launch {
            val response = RetrofitInstances.Query(accountViewModel.token.value).queryAPI.getNearbyStation(_currentUserLocation.value.latitude,_currentUserLocation.value.longitude, _currentUserLocation.value.radius)
            if(response.isSuccessful){
                try {
                    filteredStations = response.body()?.data!!
                }catch (e: Exception){
                    Log.d("GoogleMapViewModel", "Error: ${e.message}")
                }
            }else{
                Log.d("GoogleMapViewModel", "Error: ${response.errorBody()}")
            }
            _searchState.update {
                it.copy(stationList = filteredStations)
            }
        }
    }
}

data class SearchStationState(
    val stationList: List<Station>? = null
)