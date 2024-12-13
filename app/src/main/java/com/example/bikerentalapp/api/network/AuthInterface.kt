package com.example.bikerentalapp.api.network

import com.example.bikerentalapp.api.data.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthInterface {
    @POST("auth/login")
    suspend fun login(@Body login: LoginRequest): Response<LoginResponse>

    @POST("auth/signup")
    suspend fun signup(@Body signup: UserCreateRequest): Response<CRUDResponse<Boolean>>

    @POST("otp/send")
    suspend fun sendOTP(@Body otp: OTPRequest): Response<OTPResponse>

    @POST("otp/verify")
    suspend fun verifyOTP(@Body otp: OTPValidationRequest): Response<OTPResponse>

    @PATCH("auth/forgot-password")
    suspend fun forgotPassword(@Body reset: ResetPwdRequest): Response<CRUDResponse<Boolean>>

    @PATCH("auth/reset-password")
    suspend fun resetPassword(
        @Header("Authorization") token: String,
        @Body reset: ResetPwdRequest
    ): Response<CRUDResponse<Boolean>>
}