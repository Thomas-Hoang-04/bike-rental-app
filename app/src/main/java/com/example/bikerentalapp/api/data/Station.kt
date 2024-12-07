package com.example.bikerentalapp.api.data

import com.google.gson.annotations.SerializedName
import java.util.*

enum class BikeStatus {
    IN_USE,
    AVAILABLE,
    CHARGING,
}

enum class BikeType {
    MANUAL, ELECTRIC
}

@Suppress("unused")
enum class StationStatus {
    ACTIVE,
    INACTIVE
}

@Suppress("unused")
enum class BikeAction {
    RENT,
    RETURN
}

@Suppress("unused")
enum class UserRole {
    USER,
    ADMIN
}

@Suppress("unused")
enum class OTPStatus {
    SUCCESS, FAILED, INVALID, EXPIRED
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
    val latitude: Double,
    val longitude: Double,
    var name: String,
    val address: String,
    val capacity: Int,
    val status: StationStatus,

    @SerializedName("bike_list")
    val bikeList: List<Bike>
)


data class Bike(
    val id: UUID,
    val plate: String,
    val type: BikeType,
    val battery: Int,
    val status: BikeStatus,
    val location: UUID?,
)

data class BikeRenting(
    val plate: String,
    val latitude: Double,
    val longitude: Double,
    val action: BikeAction,
    val battery: Int?
)

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
    val dob: String
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
    val role: UserRole?,
    val details: UserDetails
)

data class OTPRequest(
    val username: String,
    @SerializedName("phone_number")
    val phoneNumber: String
)

data class OTPResponse(
    val status: OTPStatus,
    val message: String
)

data class OTPValidationRequest(
    val otp: String,
    val username: String
)

//val stations = listOf(
//    Station(
//        lat = 21.0050,
//        lng = 105.8430,
//        id = "Station1",
//        address = "So 1 Tran Dai Nghia, Hai Ba Trung, Ha Noi, Viet Nam",
//        bikeList = listOf(
//            Bike(id = "B1", type = "Electric", battery = 75, status = BikeStatus.AVAILABLE),
//            Bike(id = "B1", type = "Electric", battery = 75, status = BikeStatus.AVAILABLE),
//            Bike(id = "B1", type = "Electric", battery = 75, status = BikeStatus.AVAILABLE),
//            Bike(id = "B1", type = "Electric", battery = 75, status = BikeStatus.AVAILABLE),
//            Bike(id = "B1", type = "Electric", battery = 75, status = BikeStatus.AVAILABLE),
//            Bike(id = "B1", type = "Electric", battery = 75, status = BikeStatus.AVAILABLE),
//            Bike(id = "B1", type = "Electric", battery = 75, status = BikeStatus.AVAILABLE),
//            Bike(id = "B1", type = "Electric", battery = 75, status = BikeStatus.AVAILABLE),
//            Bike(id = "B1", type = "Electric", battery = 75, status = BikeStatus.AVAILABLE),
//            Bike(id = "B1", type = "Electric", battery = 75, status = BikeStatus.AVAILABLE),
//            Bike(id = "B2", type = "Standard", battery = 0, status = BikeStatus.IN_USE)
//        )
//    ),
//    Station(
//        lat = 21.0285,
//        lng = 105.8542,
//        id = "Station2",
//        address = "So 2 Le Duan, Dong Da, Ha Noi, Viet Nam",
//        bikeList = listOf(
//            Bike(id = "B3", type = "Electric", battery = 60, status = BikeStatus.CHARGING),
//            Bike(id = "B4", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE)
//        )
//    ),
//    Station(
//        lat = 21.0300,
//        lng = 105.8530,
//        id = "Station3",
//        address = "So 3 Nguyen Trai, Thanh Xuan, Ha Noi, Viet Nam",
//        bikeList = listOf(
//            Bike(id = "B5", type = "Electric", battery = 90, status = BikeStatus.AVAILABLE),
//            Bike(id = "B5", type = "Electric", battery = 90, status = BikeStatus.AVAILABLE),
//            Bike(id = "B5", type = "Electric", battery = 90, status = BikeStatus.AVAILABLE),
//            Bike(id = "B5", type = "Electric", battery = 90, status = BikeStatus.AVAILABLE),
//            Bike(id = "B5", type = "Electric", battery = 90, status = BikeStatus.AVAILABLE),
//            Bike(id = "B5", type = "Electric", battery = 90, status = BikeStatus.AVAILABLE),
//            Bike(id = "B5", type = "Electric", battery = 90, status = BikeStatus.AVAILABLE),
//            Bike(id = "B5", type = "Electric", battery = 90, status = BikeStatus.AVAILABLE),
//            Bike(id = "B5", type = "Electric", battery = 90, status = BikeStatus.AVAILABLE),
//            Bike(id = "B5", type = "Electric", battery = 90, status = BikeStatus.AVAILABLE),
//            Bike(id = "B5", type = "Electric", battery = 90, status = BikeStatus.AVAILABLE),
//            Bike(id = "B5", type = "Electric", battery = 90, status = BikeStatus.AVAILABLE),
//            Bike(id = "B6", type = "Standard", battery = 0, status = BikeStatus.IN_USE)
//        )
//    ),
//    Station(
//        lat = 21.0310,
//        lng = 105.8540,
//        id = "Station4",
//        address = "So 4 Kim Ma, Ba Dinh, Ha Noi, Viet Nam",
//        bikeList = listOf(
//            Bike(id = "B7", type = "Electric", battery = 70, status = BikeStatus.CHARGING),
//            Bike(id = "B8", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE),
//            Bike(id = "B8", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE),
//            Bike(id = "B8", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE),
//            Bike(id = "B8", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE),
//            Bike(id = "B8", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE),
//            Bike(id = "B8", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE),
//            Bike(id = "B8", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE),
//            Bike(id = "B8", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE),
//            Bike(id = "B8", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE),
//            Bike(id = "B8", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE),
//            Bike(id = "B8", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE),
//            Bike(id = "B8", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE),
//            Bike(id = "B8", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE),
//            Bike(id = "B8", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE),
//        )
//    ),
//    Station(
//        lat = 21.0320,
//        lng = 105.8550,
//        id = "Station5",
//        address = "So 5 Hoang Hoa Tham, Tay Ho, Ha Noi, Viet Nam",
//        bikeList = listOf(
//            Bike(id = "B9", type = "Electric", battery = 50, status = BikeStatus.AVAILABLE),
//            Bike(id = "B10", type = "Standard", battery = 0, status = BikeStatus.IN_USE)
//        )
//    ),
//    Station(
//        lat = 21.0330,
//        lng = 105.8560,
//        id = "Station6",
//        address = "So 6 Lang Ha, Dong Da, Ha Noi, Viet Nam",
//        bikeList = listOf(
//            Bike(id = "B11", type = "Electric", battery = 85, status = BikeStatus.CHARGING),
//            Bike(id = "B12", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE)
//        )
//    ),
//    Station(
//        lat = 21.0340,
//        lng = 105.8570,
//        id = "Station7",
//        address = "So 7 Cau Giay, Cau Giay, Ha Noi, Viet Nam",
//        bikeList = listOf(
//            Bike(id = "B13", type = "Electric", battery = 75, status = BikeStatus.AVAILABLE),
//            Bike(id = "B14", type = "Standard", battery = 0, status = BikeStatus.IN_USE)
//        )
//    ),
//    Station(
//        lat = 21.0350,
//        lng = 105.8580,
//        id = "Station8",
//        address = "So 8 Tran Hung Dao, Hoan Kiem, Ha Noi, Viet Nam",
//        bikeList = listOf(
//            Bike(id = "B15", type = "Electric", battery = 65, status = BikeStatus.CHARGING),
//            Bike(id = "B16", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE)
//        )
//    ),
//    Station(
//        lat = 21.0360,
//        lng = 105.8590,
//        id = "Station9",
//        address = "So 9 Hai Ba Trung, Hoan Kiem, Ha Noi, Viet Nam",
//        bikeList = listOf(
//            Bike(id = "B17", type = "Electric", battery = 95, status = BikeStatus.AVAILABLE),
//            Bike(id = "B18", type = "Standard", battery = 0, status = BikeStatus.IN_USE)
//        )
//    ),
//    Station(
//        lat = 21.0370,
//        lng = 105.8600,
//        id = "Station10",
//        address = "So 10 Pham Van Dong, Tu Liem, Ha Noi, Viet Nam",
//        bikeList = listOf(
//            Bike(id = "B19", type = "Electric", battery = 55, status = BikeStatus.CHARGING),
//            Bike(id = "B20", type = "Standard", battery = 0, status = BikeStatus.AVAILABLE)
//        )
//    )
//)