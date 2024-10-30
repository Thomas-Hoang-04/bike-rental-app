package com.example.bikerentalapp.model

data class User(
    val phoneNumber: String,
    val name: String,
    val email: String?,
    val dateOfBirth: String,
    val password: String,
    val referralCode: String?
)