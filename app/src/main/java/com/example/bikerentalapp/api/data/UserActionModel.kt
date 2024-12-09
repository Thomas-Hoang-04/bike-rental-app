package com.example.bikerentalapp.api.data

data class TopUpRequest(
    val from: String,
    val to: String = from,
    val amount: Int
)

data class BikeRenting(
    val plate: String,
    val latitude: Double,
    val longitude: Double,
    val action: BikeAction,
    val battery: Int?
)