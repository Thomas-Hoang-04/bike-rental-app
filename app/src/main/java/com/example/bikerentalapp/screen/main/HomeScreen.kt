package com.example.bikerentalapp.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import com.example.bikerentalapp.R
import com.example.bikerentalapp.components.GridLayout
import com.example.bikerentalapp.components.SearchBar
import com.example.bikerentalapp.components.features
import com.example.bikerentalapp.components.navigationItems
import com.example.bikerentalapp.ui.theme.PrimaryColor

@Composable
fun HomeScreen(
    onFeatureClick: (HomeScreenClicks) -> Unit
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    var searchStation by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                NavigationBar(
                    containerColor = Color.White,
                    contentColor = PrimaryColor
                ) {
                    navigationItems.take(2).forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    style = TextStyle(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontStyle = FontStyle.Normal
                                    )
                                )
                            },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                when (index) {
                                    0 -> onFeatureClick(HomeScreenClicks.HomeScreenClick)
                                    1 -> onFeatureClick(HomeScreenClicks.StationScreenClick)
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PrimaryColor,
                                unselectedIconColor = Color.Gray,
                                selectedTextColor = PrimaryColor,
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color.White
                            )
                        )
                    }

                    NavigationBarItem(
                        icon = { Box(modifier = Modifier.size(20.dp)) },
                        label = { Text("") },
                        selected = false,
                        onClick = { },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.White
                        )
                    )

                    navigationItems.takeLast(2).forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    style = TextStyle(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontStyle = FontStyle.Normal
                                    )
                                )
                            },
                            selected = selectedItem == index + 2,
                            onClick = {
                                selectedItem = index + 2
                                when (index) {
                                    2 -> onFeatureClick(HomeScreenClicks.NotificationScreenClick)
                                    3 -> onFeatureClick(HomeScreenClicks.ProfileScreenClick)
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PrimaryColor,
                                unselectedIconColor = Color.Gray,
                                selectedTextColor = PrimaryColor,
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color.White
                            )
                        )
                    }
                }

                FloatingActionButton(
                    onClick = { onFeatureClick(HomeScreenClicks.ScanQRClick) },
                    modifier = Modifier
                        .offset(y = (-6).dp)
                        .size(56.dp),
                    containerColor = PrimaryColor,
                    contentColor = Color.White
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = R.drawable.qr_scan,
                            imageLoader = LocalContext.current.imageLoader
                        ),
                        contentDescription = "Scan QR Code",
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryColor)
                    .wrapContentHeight()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Xin chào",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .padding(4.dp)
                    )

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = PrimaryColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SearchBar(
                        hint = "Tìm kiếm trạm xe",
                        searchText = searchStation,
                        onSearchChange = { searchStation = it }
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = 72.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Tài khoản chính",
                                    style = TextStyle(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Black
                                    )
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "50.000 đồng",
                                    style = TextStyle(
                                        fontSize = 13.sp,
                                        color = Color.Black
                                    )
                                )
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp),
                            thickness = 1.dp,
                            color = Color.Gray
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            IconButton(onClick = { /*TODO*/ }) {

                            }

                            IconButton(onClick = { /*TODO*/ }) {

                            }

                            IconButton(onClick = { /*TODO*/ }) {

                            }
                        }
                    }

                }
            }


            Spacer(modifier = Modifier.height(80.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Tiện ích",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Normal
                    ),
                    modifier = Modifier.padding(start = 10.dp)
                )

                GridLayout(
                    items = features(onFeatureClick),
                    column = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 10.dp)
                )
            }

        }
    }
}

sealed class HomeScreenClicks {
    // Navigation clicks
    data object HomeScreenClick: HomeScreenClicks()
    data object StationScreenClick: HomeScreenClicks()
    data object NotificationScreenClick: HomeScreenClicks()
    data object ProfileScreenClick: HomeScreenClicks()
    data object ScanQRClick: HomeScreenClicks()

    // Feature clicks
    data object BuyingTicketsClick: HomeScreenClicks()
    data object MyTripsScreenClick: HomeScreenClicks()
    data object NewsScreenClick: HomeScreenClicks()
    data object RankingScreenClick: HomeScreenClicks()
    data object ManualUserScreenClick: HomeScreenClicks()
    data object InviteFriendsScreenClick: HomeScreenClicks()
    data object PromotionScreenClick: HomeScreenClicks()
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen {}
}
