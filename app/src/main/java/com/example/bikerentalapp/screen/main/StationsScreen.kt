package com.example.bikerentalapp.screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun StationsScreen() {
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(21.043303, 105.793921), 12f, 0f, 0f)
    }
// The screen with map of bike stations (Refer Trạm xe on TNGo)
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        onMapLoaded = { isMapLoaded = true },
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            compassEnabled = false
        ),
        properties = MapProperties(isMyLocationEnabled = true),
    )
}
