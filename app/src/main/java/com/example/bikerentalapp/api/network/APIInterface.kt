package com.example.bikerentalapp.api.network

import com.example.bikerentalapp.api.data.*
import retrofit2.Response
import retrofit2.http.*
import java.util.UUID

interface APIInterface {
    // Station API
    @GET("stations/available")
    suspend fun getStations(): Response<QueryResponse<String, Station>>

    @GET("stations/{id}")
    suspend fun getStationById(@Path("id") id: UUID): Response<QueryResponse<UUID, Station>>

    @GET("nearby")
    suspend fun getNearbyStation(@Body query: NearbyStation): Response<QueryResponse<Double, Station>>

    // Bike API
    @GET("bike/id/{id}")
    suspend fun getBikeById(@Path("id") id: UUID): Response<QueryResponse<UUID, Bike>>

    @GET("bike/plate/{plate}")
    suspend fun getBikeByPlate(@Path("plate") plate: String): Response<QueryResponse<String, Bike>>

    @PATCH("bike/rent")
    suspend fun rentBike(@Body rent: BikeRenting): Response<CRUDResponse<Bike>>

    // User API
    @GET("user/me")
    suspend fun getUser(): Response<QueryResponse<String, User>>

    @POST("user/sharing")
    suspend fun pointSharing(@Body sharing: TopUpRequest): Response<CRUDResponse<TransactionStatus>>

    @POST("user/topup")
    suspend fun topUp(@Body topUp: TopUpRequest): Response<CRUDResponse<TransactionStatus>>

    @POST("user/transaction")
    suspend fun createTransaction(@Body transaction: TransactionRequest): Response<CRUDResponse<TransactionStatus>>

    @GET("user/transaction")
    suspend fun getTransactions(): Response<QueryResponse<String, TransactionsDetails>>

    @POST("user/trip")
    suspend fun createTrip(@Body trip: TripRequest): Response<CRUDResponse<TransactionStatus>>

    @GET("user/trip")
    suspend fun getTrips(): Response<QueryResponse<String, TripDetails>>
}