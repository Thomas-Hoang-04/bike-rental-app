package com.example.bikerentalapp.api.data

import com.google.gson.annotations.SerializedName
import java.util.*

@Suppress("unused")
enum class TransactionStatus {
    SUCCESS,
    PENDING,
    FAILED
}

@Suppress("unused")
enum class TransactionPurpose {
    TOPUP, POINTSHARE, TRIP, REFUND
}

@Suppress("unused")
enum class TicketTypes {
    SINGLE,
    DAILY,
    MONTHLY
}

data class Geolocation(
    val lat: Double,
    val lng: Double
)

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

data class TransactionsDetails(
    val id: UUID,
    val username: String,
    @SerializedName("created_at")
    val createdAt: String,
    val amount: Int,
    val purpose: TransactionPurpose,
    val descriptions: String,
    val status: TransactionStatus
)

data class TransactionRequest(
    val username: String,
    val amount: Int,
    val purpose: TransactionPurpose,
    val descriptions: String,
)

data class TripDetails(
    val id: String,
    val username: String,

    @SerializedName("bike_plate")
    val bikePlate: String,

    @SerializedName("start_time")
    val startTime: String,

    @SerializedName("end_time")
    val endTime: String,

    @SerializedName("start_address")
    val startAddress: String,

    @SerializedName("end_address")
    val endAddress: String,

    @SerializedName("travel_time")
    val travelTime: String,

    @SerializedName("ticket_type")
    val ticketType: TicketTypes,

    @SerializedName("travel_route")
    val travelRoute: String,

    val distance: Double,
    val fee: Int,
)

data class TripRequest(
    val id: String,
    val username: String,

    @SerializedName("bike_plate")
    val bikePlate: String,

    @SerializedName("start_time")
    val startTime: String,

    @SerializedName("end_time")
    val endTime: String,

    @SerializedName("start_address")
    val startAddress: String,

    @SerializedName("end_address")
    val endAddress: String,

    @SerializedName("travel_time")
    val travelTime: String,

    @SerializedName("ticket_type")
    val ticketType: TicketTypes,

    val fee: Int,
    val route: List<Geolocation>
)
