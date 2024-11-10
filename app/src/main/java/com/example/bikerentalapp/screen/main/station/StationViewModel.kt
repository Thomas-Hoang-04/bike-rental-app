package com.example.bikerentalapp.screen.main.station

import androidx.lifecycle.ViewModel
import com.example.bikerentalapp.model.Station
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GoogleMapViewModel : ViewModel() {

    //Example of list of stations
    val stationList = listOf(
        Station(21.043303, 105.793921, 1, 10),
        Station(21.045000, 105.795000, 2, 20),
        Station(21.046000, 105.796000, 3, 30),
        Station(21.047000, 105.797000, 4, 40),
        Station(21.048000, 105.798000, 5, 50),
        Station(21.049000, 105.799000, 6, 60),
        Station(21.050000, 105.800000, 7, 70),
        Station(21.051000, 105.801000, 8, 80),
        Station(21.052000, 105.802000, 9, 90),
        Station(21.053000, 105.803000, 10, 100),
        Station(21.054000, 105.804000, 11, 110),
        Station(21.055000, 105.805000, 12, 120),
        Station(21.056000, 105.806000, 13, 130),
        Station(21.057000, 105.807000, 14, 140),
        Station(21.058000, 105.808000, 15, 150),
        Station(21.059000, 105.809000, 16, 160),
        Station(21.060000, 105.810000, 17, 170),
        Station(21.061000, 105.811000, 18, 180),
        Station(21.062000, 105.812000, 19, 190),
        Station(21.063000, 105.813000, 20, 200)
    )

    private val _locationPermissionGranted = MutableStateFlow(false)
    val locationPermissionGranted = _locationPermissionGranted.asStateFlow()

    fun setLocationPermissionGranted(granted: Boolean) {
        _locationPermissionGranted.value = granted
    }

}