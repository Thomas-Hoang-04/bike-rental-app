package com.example.bikerentalapp.model


enum class BikeStatus {
    IN_USE,
    AVAILABLE,
    CHARGING,
}

data class Station(
    val lat : Double,
    val lng : Double,
    val id: String,
    val farAway: Int,
    val address: String,
    val listBicycle : List<Bicycle>
)

data class Bicycle(
    val id: String,
    val type: String,
    val batteryPercentage: Int,
    val bikeStatus: BikeStatus
)

val stations = listOf(
    Station(
        lat = 21.0050,
        lng = 105.8430,
        id = "Station1",
        farAway = 500,
        address = "So 1 Tran Dai Nghia, Hai Ba Trung, Ha Noi, Viet Nam",
        listBicycle = listOf(
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 75, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 75, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 75, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 75, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 75, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 75, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 75, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 75, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 75, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 75, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B2", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.IN_USE)
        )
    ),
    Station(
        lat = 21.0285,
        lng = 105.8542,
        id = "Station2",
        farAway = 600,
        address = "So 2 Le Duan, Dong Da, Ha Noi, Viet Nam",
        listBicycle = listOf(
            Bicycle(id = "B3", type = "Electric", batteryPercentage = 60, bikeStatus = BikeStatus.CHARGING),
            Bicycle(id = "B4", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE)
        )
    ),
    Station(
        lat = 21.0300,
        lng = 105.8530,
        id = "Station3",
        farAway = 700,
        address = "So 3 Nguyen Trai, Thanh Xuan, Ha Noi, Viet Nam",
        listBicycle = listOf(
            Bicycle(id = "B5", type = "Electric", batteryPercentage = 90, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B5", type = "Electric", batteryPercentage = 90, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B5", type = "Electric", batteryPercentage = 90, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B5", type = "Electric", batteryPercentage = 90, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B5", type = "Electric", batteryPercentage = 90, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B5", type = "Electric", batteryPercentage = 90, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B5", type = "Electric", batteryPercentage = 90, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B5", type = "Electric", batteryPercentage = 90, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B5", type = "Electric", batteryPercentage = 90, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B5", type = "Electric", batteryPercentage = 90, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B5", type = "Electric", batteryPercentage = 90, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B5", type = "Electric", batteryPercentage = 90, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B6", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.IN_USE)
        )
    ),
    Station(
        lat = 21.0310,
        lng = 105.8540,
        id = "Station4",
        farAway = 800,
        address = "So 4 Kim Ma, Ba Dinh, Ha Noi, Viet Nam",
        listBicycle = listOf(
            Bicycle(id = "B7", type = "Electric", batteryPercentage = 70, bikeStatus = BikeStatus.CHARGING),
            Bicycle(id = "B8", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B8", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B8", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B8", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B8", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B8", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B8", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B8", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B8", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B8", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B8", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B8", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B8", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B8", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE),
        )
    ),
    Station(
        lat = 21.0320,
        lng = 105.8550,
        id = "Station5",
        farAway = 900,
        address = "So 5 Hoang Hoa Tham, Tay Ho, Ha Noi, Viet Nam",
        listBicycle = listOf(
            Bicycle(id = "B9", type = "Electric", batteryPercentage = 50, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B10", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.IN_USE)
        )
    ),
    Station(
        lat = 21.0330,
        lng = 105.8560,
        id = "Station6",
        farAway = 1000,
        address = "So 6 Lang Ha, Dong Da, Ha Noi, Viet Nam",
        listBicycle = listOf(
            Bicycle(id = "B11", type = "Electric", batteryPercentage = 85, bikeStatus = BikeStatus.CHARGING),
            Bicycle(id = "B12", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE)
        )
    ),
    Station(
        lat = 21.0340,
        lng = 105.8570,
        id = "Station7",
        farAway = 1100,
        address = "So 7 Cau Giay, Cau Giay, Ha Noi, Viet Nam",
        listBicycle = listOf(
            Bicycle(id = "B13", type = "Electric", batteryPercentage = 75, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B14", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.IN_USE)
        )
    ),
    Station(
        lat = 21.0350,
        lng = 105.8580,
        id = "Station8",
        farAway = 1200,
        address = "So 8 Tran Hung Dao, Hoan Kiem, Ha Noi, Viet Nam",
        listBicycle = listOf(
            Bicycle(id = "B15", type = "Electric", batteryPercentage = 65, bikeStatus = BikeStatus.CHARGING),
            Bicycle(id = "B16", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE)
        )
    ),
    Station(
        lat = 21.0360,
        lng = 105.8590,
        id = "Station9",
        farAway = 1300,
        address = "So 9 Hai Ba Trung, Hoan Kiem, Ha Noi, Viet Nam",
        listBicycle = listOf(
            Bicycle(id = "B17", type = "Electric", batteryPercentage = 95, bikeStatus = BikeStatus.AVAILABLE),
            Bicycle(id = "B18", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.IN_USE)
        )
    ),
    Station(
        lat = 21.0370,
        lng = 105.8600,
        id = "Station10",
        farAway = 1400,
        address = "So 10 Pham Van Dong, Tu Liem, Ha Noi, Viet Nam",
        listBicycle = listOf(
            Bicycle(id = "B19", type = "Electric", batteryPercentage = 55, bikeStatus = BikeStatus.CHARGING),
            Bicycle(id = "B20", type = "Standard", batteryPercentage = 0, bikeStatus = BikeStatus.AVAILABLE)
        )
    )
)