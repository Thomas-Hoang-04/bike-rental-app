package com.example.bikerentalapp.components

import androidx.compose.ui.graphics.vector.ImageVector

data class FeatureItem (
    val icon: ImageVector,
    val title: String,
    val onClick: () -> Unit
)

