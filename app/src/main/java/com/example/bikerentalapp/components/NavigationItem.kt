package com.example.bikerentalapp.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person3
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem (
    val label: String,
    val icon: ImageVector,
    val route: String
)

val navigationItems = listOf(
    NavigationItem(
        label = "Trang chủ",
        icon = Icons.Default.Home,
        route = "home"
    ),
    NavigationItem(
        label = "Trạm xe",
        icon = Icons.Default.LocationOn,
        route = "station"
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
        route = "notification"
    ),
    NavigationItem(
        label = "Hồ sơ",
        icon = Icons.Default.Person3,
        route = "profile"
    )
)