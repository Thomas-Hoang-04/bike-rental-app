package com.example.bikerentalapp.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.BookOnline
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bikerentalapp.navigation.Screens

data class FeatureItem (
    val icon: ImageVector,
    val title: String,
    val route: Any
)

val features = listOf(
    FeatureItem(
        icon = Icons.Filled.Place,
        title = "Trạm xe",
        route = Screens.Main.Station
    ),
    FeatureItem(
        icon = Icons.Filled.BookOnline,
        title = "Mua vé",
        route = Screens.Features.BuyingTickets
    ),
    FeatureItem(
        icon = Icons.AutoMirrored.Filled.DirectionsBike,
        title = "Chuyến đi của tôi",
        route = Screens.Features.MyTrips
    ),
    FeatureItem(
        icon = Icons.Filled.AccountCircle,
        title = "Hồ sơ",
        route = Screens.Main.Profile
    ),
    FeatureItem(
        icon = Icons.Filled.Newspaper,
        title = "Tin tức",
        route = Screens.Features.News
    ),
    FeatureItem(
        icon = Icons.Filled.Leaderboard,
        title = "Bảng xếp hạng",
        route = Screens.Features.Ranking
    ),
    FeatureItem(
        icon = Icons.Filled.Book,
        title = "Hướng dẫn sử dụng",
        route = Screens.Features.ManualUser
    ),
    FeatureItem(
        icon = Icons.Filled.Groups,
        title = "Mời bạn bè",
        route = Screens.Features.InviteFriends
    ),
    FeatureItem(
        icon = Icons.Filled.LocalOffer,
        title = "Khuyến mãi",
        route = Screens.Features.Promotion
    )
)

