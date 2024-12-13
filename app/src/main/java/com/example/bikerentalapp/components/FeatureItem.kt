package com.example.bikerentalapp.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.AirplaneTicket
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.BookOnline
import androidx.compose.material.icons.filled.CallToAction
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bikerentalapp.screen.main.HomeScreenClicks

data class FeatureItem (
    val icon: ImageVector,
    val title: String,
    val onClick: () -> Unit
)

val features = { onFeatureClick: (HomeScreenClicks) -> Unit ->
    listOf(
        FeatureItem(
            icon = Icons.Filled.Place,
            title = "Trạm xe",
            onClick = { onFeatureClick(HomeScreenClicks.StationScreenClick) }
        ),
        FeatureItem(
            icon = Icons.Filled.BookOnline,
            title = "Mua vé",
            onClick = { onFeatureClick(HomeScreenClicks.BuyingTicketsClick) }
        ),
        FeatureItem(
            icon = Icons.Filled.DirectionsBike,
            title = "Chuyến đi của tôi",
            onClick = { onFeatureClick(HomeScreenClicks.MyTripsScreenClick) }
        ),
        FeatureItem(
            icon = Icons.Filled.AccountCircle,
            title = "Hồ sơ",
            onClick = { onFeatureClick(HomeScreenClicks.ProfileScreenClick) }
        ),
        FeatureItem(
            icon = Icons.Filled.Newspaper,
            title = "Tin tức",
            onClick = { onFeatureClick(HomeScreenClicks.NewsScreenClick) }
        ),
        FeatureItem(
            icon = Icons.Filled.Leaderboard,
            title = "Bảng xếp hạng",
            onClick = { onFeatureClick(HomeScreenClicks.RankingScreenClick) }
        ),
        FeatureItem(
            icon = Icons.Filled.Book,
            title = "Hướng dẫn sử dụng",
            onClick = { onFeatureClick(HomeScreenClicks.ManualUserScreenClick) }
        ),
        FeatureItem(
            icon = Icons.Filled.Groups,
            title = "Mời bạn bè",
            onClick = { onFeatureClick(HomeScreenClicks.InviteFriendsScreenClick) }
        ),
        FeatureItem(
            icon = Icons.Filled.LocalOffer,
            title = "Khuyến mãi",
            onClick = { onFeatureClick(HomeScreenClicks.PromotionScreenClick) }
        )
    )
}
