package com.example.bikerentalapp.screen.main.station

import androidx.lifecycle.ViewModel
import com.example.bikerentalapp.model.Station
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GoogleMapViewModel : ViewModel() {

    //Example of list of stations
    val stationList = listOf(
        Station(21.043303, 105.793921, 1, 10),
        Station(21.043303, 105.793921, 2, 20),
        Station(21.043303, 105.793921, 3, 30),
        Station(21.043303, 105.793921, 4, 40),
        Station(21.043303, 105.793921, 5, 50),
        Station(21.043303, 105.793921, 6, 60),
        Station(21.043303, 105.793921, 7, 70),
        Station(21.043303, 105.793921, 8, 80),
        Station(21.043303, 105.793921, 9, 90),
        Station(21.043303, 105.793921, 10, 100),
    )

    private val _locationPermissionGranted = MutableStateFlow(false)
    val locationPermissionGranted = _locationPermissionGranted.asStateFlow()

    fun setLocationPermissionGranted(granted: Boolean) {
        _locationPermissionGranted.value = granted
    }

}