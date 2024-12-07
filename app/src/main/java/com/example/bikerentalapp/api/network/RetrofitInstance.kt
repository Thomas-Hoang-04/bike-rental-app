package com.example.bikerentalapp.api.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://bikegorental.my/api/"

    private val queryClient = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor { null })
        .build()

    private val authInstance = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val queryInstance = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(queryClient)
        .build()

    val authAPI: AuthInterface = authInstance.create(AuthInterface::class.java)

    val queryAPI: APIInterface = queryInstance.create(APIInterface::class.java)
}