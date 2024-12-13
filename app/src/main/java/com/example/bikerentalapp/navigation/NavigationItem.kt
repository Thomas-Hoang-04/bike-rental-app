package com.example.bikerentalapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem (
    val label: String,
    val icon: ImageVector,
    val route: Any
)

val navigationItems = listOf(
    NavigationItem(
        label = "Trang chủ",
        icon = Icons.Default.Home,
        route = Screens.Main.Home
    ),
    NavigationItem(
        label = "Trạm xe",
        icon = Icons.Default.LocationOn,
        route = Screens.Main.Station
    ),
    //This item is hidden from the navigation bar and is only used for adding more space between items 2 and 3
    NavigationItem(
        label = "Example",
        icon = Icons.Default.Notifications,
        route = "Example"
    ),
    NavigationItem(
        label = "Thông báo",
        icon = Icons.Default.Notifications,
        route = Screens.Main.Notification
    ),
    NavigationItem(
        label = "Cài đặt",
        icon = Icons.Default.Settings,
        route = Screens.Main.Settings
    )
)