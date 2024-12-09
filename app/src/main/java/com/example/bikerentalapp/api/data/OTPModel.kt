package com.example.bikerentalapp.api.data

import com.google.gson.annotations.SerializedName

data class OTPRequest(
    val username: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val purpose: OTPPurpose
)

data class OTPResponse(
    val status: OTPStatus,
    val message: String
)

data class OTPValidationRequest(
    val otp: String,
    val username: String
)

@Suppress("unused")
enum class OTPStatus {
    SUCCESS, FAILED, INVALID, EXPIRED
}

@Suppress("unused")
enum class OTPPurpose { RESET_PASSWORD, SIGNUP }