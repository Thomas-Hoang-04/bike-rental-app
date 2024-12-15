package com.example.bikerentalapp.api.data

import com.google.gson.annotations.SerializedName
import java.util.UUID

@Suppress("unused")
enum class StationStatus {
    ACTIVE,
    INACTIVE
}

data class NearbyStation(
    val latitude: Double,
    val longitude: Double,
    val radius: Double,
)

data class Station(
    @SerializedName("station_id")
    val stationID: UUID,

    @SerializedName("region_id")
    val regionID: String,

    @SerializedName("region_num")
    val regionNum: Int,
    val coordinates: Geolocation,
    var name: String,
    val address: String,
    val capacity: Int,
    val status: StationStatus,

    @SerializedName("bike_list")
    val bikeList: List<Bike>
)