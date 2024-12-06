package com.example.bikerentalapp.screen.main.qrcode

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Looper
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun TrackingMapScreen(bikeId: String) {
    val context = LocalContext.current
    val fusedClientLocation = remember{LocationServices.getFusedLocationProviderClient(context)}
    var polylinePoints by remember { mutableStateOf(listOf<LatLng>()) }
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(Unit){
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            fusedClientLocation.requestLocationUpdates(
                LocationRequest.create().apply {
                    interval = 2000 // Update every 2 seconds
                    fastestInterval = 1000
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                },
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        locationResult.locations.forEach { location ->
                            val newPoint = LatLng(location.latitude, location.longitude)
                            polylinePoints = polylinePoints + newPoint
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(newPoint, 15f)
                        }
                    }
                },
                Looper.getMainLooper())
        }else{
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        GoogleMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier.fillMaxSize(),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                compassEnabled = false,
                myLocationButtonEnabled = false,
            ),
            properties = MapProperties(
                isMyLocationEnabled = true,
                minZoomPreference = 11f,
                maxZoomPreference = 20f
            ),
        ){
            if(polylinePoints.isNotEmpty()){
                Polyline(
                    points = polylinePoints,
                    color = PrimaryColor,
                    width = 5f,
                    geodesic = true
                )
                cameraPositionState.position = CameraPosition.fromLatLngZoom(polylinePoints.last(), 15f)
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            // Add other UI components here
            Text(bikeId)
        }
    }
}