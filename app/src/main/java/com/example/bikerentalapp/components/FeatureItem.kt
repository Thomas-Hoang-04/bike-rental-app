package com.example.bikerentalapp.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.CallToAction
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
            icon = Icons.AutoMirrored.Filled.MenuBook,
            title = "Trạm xe",
            onClick = { onFeatureClick(HomeScreenClicks.StationScreenClick) }
        ),
        FeatureItem(
            icon = Icons.AutoMirrored.Filled.MenuBook,
            title = "Mua vé",
            onClick = { onFeatureClick(HomeScreenClicks.BuyingTicketsClick) }
        ),
        FeatureItem(
            icon = Icons.AutoMirrored.Filled.MenuBook,
            title = "Chuyến đi của tôi",
            onClick = { onFeatureClick(HomeScreenClicks.MyTripsScreenClick) }
        ),
        FeatureItem(
            icon = Icons.AutoMirrored.Filled.MenuBook,
            title = "Hồ sơ",
            onClick = { onFeatureClick(HomeScreenClicks.ProfileScreenClick) }
        ),
        FeatureItem(
            icon = Icons.AutoMirrored.Filled.MenuBook,
            title = "Tin tức",
            onClick = { onFeatureClick(HomeScreenClicks.NewsScreenClick) }
        ),
        FeatureItem(
            icon = Icons.AutoMirrored.Filled.MenuBook,
            title = "Bảng xếp hạng",
            onClick = { onFeatureClick(HomeScreenClicks.RankingScreenClick) }
        ),
        FeatureItem(
            icon = Icons.AutoMirrored.Filled.MenuBook,
            title = "Hướng dẫn sử dụng",
            onClick = { onFeatureClick(HomeScreenClicks.ManualUserScreenClick) }
        ),
        FeatureItem(
            icon = Icons.AutoMirrored.Filled.MenuBook,
            title = "Mời bạn bè",
            onClick = { onFeatureClick(HomeScreenClicks.InviteFriendsScreenClick) }
        ),
        FeatureItem(
            icon = Icons.Filled.CallToAction,
            title = "Khuyến mãi",
            onClick = { onFeatureClick(HomeScreenClicks.PromotionScreenClick) }
        )
    )
}
