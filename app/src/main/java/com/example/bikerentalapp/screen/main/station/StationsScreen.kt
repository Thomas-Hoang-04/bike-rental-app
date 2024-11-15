package com.example.bikerentalapp.screen.main.station

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.outlined.Navigation
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.bikerentalapp.model.Station
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import kotlinx.coroutines.launch

@Composable
fun StationsScreen() {
    val context = LocalContext.current
    val mapViewModel = remember { GoogleMapViewModel() }
    val permissionState by mapViewModel.locationPermissionGranted.collectAsState()
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        mapViewModel.setLocationPermissionGranted(isGranted)
    }
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState()
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberFlexibleBottomSheetState(
        isModal = false,
        flexibleSheetSize = FlexibleSheetSize(
            fullyExpanded = 0.8f,
            intermediatelyExpanded = 0.45f,
            slightlyExpanded = 0.15f,
        ),
        allowNestedScroll = false,
    )
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedStation by remember { mutableStateOf<Station?>(null) }

    LaunchedEffect(permissionState) {
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val userLocation = LatLng(it.latitude, it.longitude)
                    cameraPositionState.position =  CameraPosition.fromLatLngZoom(
                        userLocation, 15f
                    )
                }
            }
        } else {
            // Request permission if not granted
            launcher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
// The screen with map of bike stations (Refer Trạm xe on TNGo)
    if(permissionState){
        Scaffold {
            paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize(),
                    onMapLoaded = { isMapLoaded = true },
                    cameraPositionState = cameraPositionState,
                    uiSettings = MapUiSettings(
                        zoomControlsEnabled = false,
                        compassEnabled = false
                    ),
                    properties = MapProperties(
                        isMyLocationEnabled = true,
                        minZoomPreference = 11f,
                        maxZoomPreference = 20f
                    ),
                ) {
                    if (isMapLoaded) {
                        val newZoom = cameraPositionState.position.zoom + 1
                        StationCluster(
                            stationList = mapViewModel.stationList,
                            onClusterClick = { cluster ->
                                coroutineScope.launch {
                                    cameraPositionState.animate(
                                        update = CameraUpdateFactory.newLatLngZoom(
                                            cluster.position, newZoom
                                        ),
                                        durationMs = 500,
                                    )
                                }
                                false
                            },
                            onStationClick = {
                                coroutineScope.launch {
                                    showBottomSheet = true
                                    cameraPositionState.animate(
                                        update = CameraUpdateFactory.newLatLngZoom(
                                            it.position, 17f
                                        ),
                                        durationMs = 800,
                                    )
                                }
                                selectedStation = (it as StationClusterItem).station
                                true
                            }
                        )
                    }
                }
                if (showBottomSheet) {
                    FlexibleBottomSheet(
                        onDismissRequest = {
                            showBottomSheet = false
                        },
                        sheetState = sheetState,
                        containerColor = Color.White,
                    ) {
                        selectedStation?.let {
                            station ->
                            StationDetailsBottomSheet(station =station)
                        }
                    }
                }
            }
        }
    }else SideEffect{
        launcher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }
}

@Composable
fun StationDetailsBottomSheet(station: Station) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = "Station:${station.id}",
            fontSize = 20.sp,
            color = PrimaryColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Distance: ${station.id} km",
                fontSize = 16.sp,
                color = Color.Gray
            )
            OutlinedButton(
                onClick = {
                    val uri = Uri.parse("google.navigation:q=${station.lat},${station.lng}&mode=d")
                    val mapIntent = Intent(Intent.ACTION_VIEW, uri).apply {
                        setPackage("com.google.android.apps.maps")
                    }

                    if (mapIntent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(mapIntent)
                    }
                },
                shape = RoundedCornerShape(12),
                border = BorderStroke(width = 1.dp, color = PrimaryColor),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Navigation,
                    contentDescription = "Navigate",
                    tint = PrimaryColor,
                    modifier = Modifier
                        .size(30.dp)
                        .rotate(50f)
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(station.listBicycle){
                bicycle ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.PedalBike,
                        contentDescription = null,
                        tint = PrimaryColor,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "ID: ${bicycle.id}",
                            color = Color.Black
                        )
                        Text(
                            text = "Type: ${bicycle.type}",
                            color = Color.Black
                        )
                        Text(
                            text = "Battery: ${bicycle.batteryPercentage}%",
                            color = Color.Gray
                        )
                        Text(
                            text = "Available: ${bicycle.isAvailable}",
                            color = if (bicycle.isAvailable) Color.Green else Color.Red
                        )
                    }
                }
            }
        }
    }
}
