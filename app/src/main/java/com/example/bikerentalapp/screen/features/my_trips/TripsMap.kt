package com.example.bikerentalapp.screen.features.my_trips

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState

@Composable
fun TripsMap(polylinePoints: List<LatLng>, time : String,id : String,distance: String,duration : String ) {
    val sheetState = rememberFlexibleBottomSheetState(
        isModal = false,
        flexibleSheetSize = FlexibleSheetSize(
            fullyExpanded = 0.6f,
            intermediatelyExpanded = 0.3f,
            slightlyExpanded = 0.15f,
        ),
        allowNestedScroll = false,
    )
    var showBottomSheet by remember { mutableStateOf(true) }

    // Map setup
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(polylinePoints.first().latitude ,polylinePoints.first().longitude), 15f)
    }

    Scaffold {
        paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
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
                        width = 20f,
                        geodesic = true
                    )
                }
            }

            if(showBottomSheet){
                // Bottom sheet
                FlexibleBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState,
                    containerColor = Color.White,
                ){
                    BottomSheetContent(time, id,distance,duration)
                }
            }
        }
    }
}

@Composable
fun BottomSheetContent(time: String, id: String,distance: String,duration: String) {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Trip Details
        RowContent("Thời gian:",time)
        RowContent(leading = "Thời lượng di chuyển:", trailing = duration)
        RowContent("Khoảng cách:", "$distance km")
        RowContent(leading = "Hình thức:", trailing = "Vé lượt")
        RowContent(leading = "Mã chuyến đi:", trailing = id)
    }
}

@Composable
fun RowContent(leading :String,trailing : String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Text(
            text = leading,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(start = 10.dp)
        )

        Text(
            text = trailing,
            color = Color.Gray,
            modifier = Modifier.padding(end = 10.dp)
        )
    }
}
