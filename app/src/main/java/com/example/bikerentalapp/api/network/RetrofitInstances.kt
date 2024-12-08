package com.example.bikerentalapp.api.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

sealed class RetrofitInstances {
    companion object{
        private const val BASE_URL = "https://bikegorental.my/api/"
    }

    inner class Query(token: String) {
        private val queryClient = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor(token))
            .build()

        private val queryInstance = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(queryClient)
            .build()

        val queryAPI: APIInterface = queryInstance.create(APIInterface::class.java)
    }

    object Auth {
        private val authInstance = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val authAPI: AuthInterface = authInstance.create(AuthInterface::class.java)
    }
}