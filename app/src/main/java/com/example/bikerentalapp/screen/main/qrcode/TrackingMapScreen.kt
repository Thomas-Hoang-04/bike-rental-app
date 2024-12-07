package com.example.bikerentalapp.screen.main.qrcode

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Looper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Battery5Bar
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bikerentalapp.components.CircularButtonWithText
import com.example.bikerentalapp.components.IconWithText
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.Locale

@Composable
fun TrackingMapScreen(bikeId: String) {
    val context = LocalContext.current
    val fusedClientLocation = remember{LocationServices.getFusedLocationProviderClient(context)}
    var polylinePoints by remember { mutableStateOf(listOf<LatLng>()) }
    val cameraPositionState = rememberCameraPositionState()

    //Time Counter
    var minutesElapsed by remember { mutableIntStateOf(0) }
    var isRunning by remember { mutableStateOf(true) }
    var showStopDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        if(isRunning){
            while (true){
                kotlinx.coroutines.delay(60000L)
                minutesElapsed ++
            }
        }
    }

    LaunchedEffect(Unit){
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            fusedClientLocation.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val userLocation = LatLng(it.latitude, it.longitude)
                    cameraPositionState.position =  CameraPosition.fromLatLngZoom(
                        userLocation, 15f
                    )
                }
            }
            fusedClientLocation.requestLocationUpdates(
                LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,2000).apply {
                    setMinUpdateIntervalMillis(1000)
                }.build(),
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        locationResult.locations.forEach { location ->
                            val newPoint = LatLng(location.latitude, location.longitude)
                            polylinePoints = polylinePoints + newPoint
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(newPoint, cameraPositionState.position.zoom)
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
                    width = 25f,
                    geodesic = true
                )
            }
        }
        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
        ){
            BottomCard(
                bikeId,
                minutesElapsed,
                onClickLockBikeButton = {
                    if(isRunning){
                        showStopDialog = true
                        isRunning = false
                    }else{
                        isRunning = true
                    }
                },
                isLock = !isRunning
            )
        }
        
        if(showStopDialog){
            AlertDialog(
                onDismissRequest = {
                    isRunning = true
                    showStopDialog = false },
                title = { Text("Khoa xe") },
                text = { Text("Ban co chac chan muon khoa xe lai khong?") },
                confirmButton = {
                    TextButton(onClick = {
                        showStopDialog = false
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        isRunning = true
                        showStopDialog = false }) {
                        Text("No")
                    }
                }
            )
        }
    }
}

@Composable
fun BottomCard(bikeId: String,minutes : Int,onClickReturnBikeButton : () -> Unit = {}, onClickLockBikeButton : () -> Unit = {},isLock : Boolean){
    val expanded = remember { mutableStateOf(true) }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.large,
        shadowElevation = 8.dp,
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text("$bikeId - dang di chuyen", style = MaterialTheme.typography.titleMedium, color = PrimaryColor)
                IconButton(
                    onClick = {
                        expanded.value = !expanded.value
                    }
                ) {
                    if(expanded.value)
                        Icon(Icons.Default.ArrowDropUp, contentDescription = "Drop up", tint = Color.Black, modifier = Modifier.size(40.dp))
                    else
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Drop up", tint = Color.Black, modifier = Modifier.size(40.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if(expanded.value){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconWithText(icon = Icons.Default.CreditCard, text = "10000")
                    IconWithText(icon = Icons.Default.Timer, text = String.format(Locale.ENGLISH," %02d phut",minutes))
                    IconWithText(icon = Icons.Default.Battery5Bar, text = "78%")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircularButtonWithText(
                    icon = Icons.Default.DocumentScanner,
                    text = "Thue them",
                    onClick = { /*TODO*/ },
                    color = Color.Gray.copy(alpha = 0.1f),
                    iconColor = PrimaryColor,
                    size = 50.dp
                )
                CircularButtonWithText(
                    icon = if(!isLock) Icons.Default.Lock else Icons.Default.LockOpen,
                    text = if(!isLock) "Khoa xe" else "Mo khoa",
                    onClick = {onClickLockBikeButton() },
                    color = PrimaryColor,
                    iconColor = Color.White,
                    size = 50.dp
                )
                CircularButtonWithText(
                    icon = Icons.Default.LocalParking,
                    text = "Tra xe",
                    onClick = { onClickReturnBikeButton() },
                    color = Color.Red.copy(alpha = 0.6f),
                    iconColor = Color.White,
                    size = 50.dp
                )
                CircularButtonWithText(
                    icon = Icons.Default.Settings,
                    text = "Cai dat",
                    onClick = { /*TODO*/ },
                    color = Color.Gray.copy(alpha = 0.1f),
                    iconColor = PrimaryColor,
                    size = 50.dp
                )
            }
        }
    }
}
