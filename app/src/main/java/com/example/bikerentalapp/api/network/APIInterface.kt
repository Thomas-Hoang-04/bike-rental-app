package com.example.bikerentalapp.api.network

import com.example.bikerentalapp.api.data.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import java.util.UUID

interface APIInterface {

    @GET("stations/available")
    suspend fun getStations(): Response<QueryResponse<String, Station>>

    @GET("stations/{id}")
    suspend fun getStationById(@Path("id") id: UUID): Response<QueryResponse<UUID, Station>>

    @GET("nearby")
    suspend fun getNearbyStation(@Body query: NearbyStation): Response<QueryResponse<Double, Station>>

    @GET("bike/id/{id}")
    suspend fun getBikeById(@Path("id") id: UUID): Response<QueryResponse<UUID, Bike>>

    @GET("bike/plate/{plate}")
    suspend fun getBikeByPlate(@Path("plate") plate: String): Response<QueryResponse<String, Bike>>

    @PATCH("bike/rent")
    suspend fun rentBike(@Body rent: BikeRenting): Response<CRUDResponse<Bike>>
}