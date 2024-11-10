package com.example.bikerentalapp.screen.main.station

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
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
    val mapViewModel = remember { GoogleMapViewModel() }
    val permissionState by mapViewModel.locationPermissionGranted.collectAsState()
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        mapViewModel.setLocationPermissionGranted(isGranted)
    }
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(21.043303, 105.793921), 15f, 0f, 0f)
    }
// The screen with map of bike stations (Refer Trạm xe on TNGo)
    if(permissionState){
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            onMapLoaded = { isMapLoaded = true },
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                compassEnabled = false
            ),
            properties = MapProperties(isMyLocationEnabled = true, minZoomPreference = 11f, maxZoomPreference = 20f),
        ){
            if(isMapLoaded){
                StationCluster(
                    stationList = mapViewModel.stationList,
                )
            }
        }
    }else SideEffect{
        launcher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }
}
