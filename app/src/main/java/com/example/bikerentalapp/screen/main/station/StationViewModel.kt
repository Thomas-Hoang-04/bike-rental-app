package com.example.bikerentalapp.screen.main.station

import androidx.lifecycle.ViewModel
import com.example.bikerentalapp.model.Station
import com.example.bikerentalapp.model.stations
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GoogleMapViewModel : ViewModel() {

    //Example of list of stations
    val stationList = stations
    private val _locationPermissionGranted = MutableStateFlow(false)
    val locationPermissionGranted = _locationPermissionGranted.asStateFlow()
    private val _searchState = MutableStateFlow(SearchStationState())
    val searchState = _searchState.asStateFlow()

    fun setLocationPermissionGranted(granted: Boolean) {
        _locationPermissionGranted.value = granted
    }

    fun searchStation(query: String) {
        val filteredStations = stations.filter {
            it.id.lowercase(Locale.getDefault()).contains(query)
        }

        if (query == "") {
            _searchState.value = SearchStationState(null)
        } else {
            _searchState.update {
                it.copy(stationList = filteredStations)
            }
        }

    }

    fun selectDevice(selectedDevice: Station,cameraPositionState: CameraPositionState,coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(
                    LatLng(selectedDevice.lat, selectedDevice.lng),
                    17f
                ),
                durationMs = 1000,
            )
        }
        _searchState.value = SearchStationState(null)
    }
}

data class SearchStationState(
    val stationList: List<Station>? = null
)