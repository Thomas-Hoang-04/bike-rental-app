package com.example.bikerentalapp.screen.features.my_trips

import kotlinx.serialization.Serializable
import com.google.android.gms.maps.model.LatLng

@Serializable
data class SerializableLatLng(
    val latitude: Double,
    val longitude: Double
)

fun LatLng.toSerializable(): SerializableLatLng {
    return SerializableLatLng(latitude, longitude)
}

fun SerializableLatLng.toLatLng(): LatLng {
    return LatLng(latitude, longitude)
}
