package com.example.bikerentalapp.api.data

import java.util.*

enum class BikeStatus {
    IN_USE,
    AVAILABLE,
    CHARGING,
}

enum class BikeType {
    MANUAL, ELECTRIC
}

@Suppress("unused")
enum class BikeAction {
    RENT,
    RETURN
}

data class Bike(
    val id: UUID,
    val plate: String,
    val type: BikeType,
    val battery: Int,
    val status: BikeStatus,
    val location: UUID?,
)