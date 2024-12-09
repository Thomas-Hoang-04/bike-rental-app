package com.example.bikerentalapp.api.data

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("user")
    val targetUser: User
)

data class UserDetails(
    val name: String,
    @SerializedName("phone_num")
    val phoneNum: String,
    val email: String,
    val dob: String,
    val balance: Int = 0
)

data class User(
    val id: UUID,
    val username: String,
    val role: UserRole,
    val details: UserDetails
)

data class UserCreateRequest(
    val username: String,
    val password: String,
    val role: UserRole = UserRole.USER,
    val details: UserDetails
)

@Suppress("unused")
enum class UserRole {
    USER,
    ADMIN
}

