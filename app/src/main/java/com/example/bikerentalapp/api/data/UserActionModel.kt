package com.example.bikerentalapp.api.data

import com.google.gson.annotations.SerializedName

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

data class ResetPwdRequest(
    val username: String,
    @SerializedName("old_password")
    val oldPassword: String? = null,
    @SerializedName("new_password")
    val newPassword: String,
)