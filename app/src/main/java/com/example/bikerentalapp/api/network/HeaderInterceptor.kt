package com.example.bikerentalapp.api.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(
    private val tokenProvider: () -> String?
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalReq = chain.request()

        val excludePaths = listOf("/auth/login", "/auth/signup", "/otp/send", "/otp/verify")

        if (excludePaths.any { originalReq.url().encodedPath().contains(it) })
            return chain.proceed(originalReq)


        val req = originalReq.newBuilder().apply {
            tokenProvider()?.let { token ->
                addHeader("Authorization", "Bearer $token")
            }
            addHeader("Content-Type", "application/json")
        }.build()

        return chain.proceed(req)
    }
}