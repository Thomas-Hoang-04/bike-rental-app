package com.example.bikerentalapp.screen.features.my_trips

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun TripsMap(polylinePoints : List<LatLng>,time : String,id : String) {
    val sheetState = rememberFlexibleBottomSheetState(
        isModal = false,
        flexibleSheetSize = FlexibleSheetSize(
            fullyExpanded = 0.8f,
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
                    BottomSheetContent(time, id)
                }
            }
        }
    }
}

@Composable
fun BottomSheetContent(time: String, id: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Trip Details
        Text("Thời gian", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(time, color = Color.Gray)

        Spacer(modifier = Modifier.height(8.dp))

        Text("Thời lượng di chuyển",style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text("1 phút", color = Color.Gray)

        Spacer(modifier = Modifier.height(8.dp))

        Text("Hình thức",style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text("Vé lượt", color = Color.Gray)

        Spacer(modifier = Modifier.height(8.dp))

        Text("Mã chuyến đi",style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(id, color = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sao chép", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(5.dp))

    }
}
