package com.example.bikerentalapp.screen.features.my_trips

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.AirplaneTicket
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bikerentalapp.components.UserAccount
import com.example.bikerentalapp.ui.theme.PrimaryColor
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MyTripsScreen(navController: NavController) {
    val user = UserAccount.current
    val tripsViewModel = remember { MyTripsViewModel(user) }
    val trips by tripsViewModel.tripDetailsList.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(0.2f)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryColor)
                .padding(top = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
            ) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Close", tint = Color.White)
            }

            Text(
                text = "Chuyen di cua toi",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                modifier = Modifier.padding(start = 50.dp)
            )
        }

        LazyColumn {
            items(trips.size) { index ->
                val trip = trips[index]
                val time = tripsViewModel.formatTripTime(trip.startTime, trip.endTime)
                val polylinePoints = tripsViewModel.decodePolyline(trip.travelRoute)
                val encodedTime = URLEncoder.encode(time, StandardCharsets.UTF_8.toString())
                val duration = tripsViewModel.decodeDuration(trip.travelTime)
                val distance = "%.2f".format(trip.distance)
                TicketUI(trip.fee, trip.startAddress, trip.id, time) {
                    navController.navigate("tripsMap/$polylinePoints/$encodedTime/${trip.id}/$distance/$duration")
                }
            }
        }
    }
}

@Composable
fun TicketUI(fee : Int,startAddress: String,tripId : String,time : String,onClick : () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 12.dp, vertical = 15.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .clickable { onClick() }
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        // Top Row
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.AirplaneTicket , // Replace with your ticket icon
                    contentDescription = "Ticket",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Vé lượt",
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Text(
                text = "- $fee VND",
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Location Rows
        LocationRow(address = startAddress)
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, tint = Color.Gray, modifier = Modifier.padding(start = 6.dp))
        LocationRow(address = startAddress)

        Spacer(modifier = Modifier.height(12.dp))

        // Bottom Row
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = time,
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = tripId,
                color = Color.Gray,
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun LocationRow(address: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.PinDrop, // Replace with your location icon
            contentDescription = "Location",
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = address,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}


