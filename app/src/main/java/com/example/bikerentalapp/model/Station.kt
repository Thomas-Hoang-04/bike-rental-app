package com.example.bikerentalapp.model

data class Station(
    val lat : Double,
    val lng : Double,
    val id: Int,
    val listBicycle : List<Bicycle>
)

data class Bicycle(
    val id: String,
    val type: String,
    val batteryPercentage: Int,
    val isAvailable: Boolean
)

val stations = listOf(
    Station(
        lat = 21.030000,
        lng = 105.800000,
        id = 1,
        listBicycle = listOf(
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 80, isAvailable = true),
            Bicycle(id = "B2", type = "Standard", batteryPercentage = 0, isAvailable = false),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 80, isAvailable = true),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 80, isAvailable = true),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 80, isAvailable = true),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 80, isAvailable = true),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 80, isAvailable = true),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 80, isAvailable = true),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 80, isAvailable = true),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 80, isAvailable = true),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 80, isAvailable = true),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 80, isAvailable = true),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 80, isAvailable = true),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 80, isAvailable = true),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 80, isAvailable = true),
            Bicycle(id = "B1", type = "Electric", batteryPercentage = 80, isAvailable = true),


        )
    ),
    Station(
        lat = 21.031000,
        lng = 105.801000,
        id = 2,
        listBicycle = listOf(
            Bicycle(id = "B3", type = "Electric", batteryPercentage = 60, isAvailable = true),
            Bicycle(id = "B4", type = "Standard", batteryPercentage = 0, isAvailable = true)
        )
    ),
    Station(
        lat = 21.032000,
        lng = 105.802000,
        id = 3,
        listBicycle = listOf(
            Bicycle(id = "B5", type = "Electric", batteryPercentage = 90, isAvailable = false),
            Bicycle(id = "B6", type = "Standard", batteryPercentage = 0, isAvailable = true)
        )
    ),
    Station(
        lat = 21.033000,
        lng = 105.803000,
        id = 4,
        listBicycle = listOf(
            Bicycle(id = "B7", type = "Electric", batteryPercentage = 50, isAvailable = true),
            Bicycle(id = "B8", type = "Standard", batteryPercentage = 0, isAvailable = false)
        )
    ),
    Station(
        lat = 21.034000,
        lng = 105.804000,
        id = 5,
        listBicycle = listOf(
            Bicycle(id = "B9", type = "Electric", batteryPercentage = 70, isAvailable = true),
            Bicycle(id = "B10", type = "Standard", batteryPercentage = 0, isAvailable = true)
        )
    ),
    Station(
        lat = 21.035000,
        lng = 105.805000,
        id = 6,
        listBicycle = listOf(
            Bicycle(id = "B11", type = "Electric", batteryPercentage = 85, isAvailable = true),
            Bicycle(id = "B12", type = "Standard", batteryPercentage = 0, isAvailable = false)
        )
    ),
    Station(
        lat = 21.036000,
        lng = 105.806000,
        id = 7,
        listBicycle = listOf(
            Bicycle(id = "B13", type = "Electric", batteryPercentage = 75, isAvailable = true),
            Bicycle(id = "B14", type = "Standard", batteryPercentage = 0, isAvailable = true)
        )
    ),
    Station(
        lat = 21.037000,
        lng = 105.807000,
        id = 8,
        listBicycle = listOf(
            Bicycle(id = "B15", type = "Electric", batteryPercentage = 65, isAvailable = true),
            Bicycle(id = "B16", type = "Standard", batteryPercentage = 0, isAvailable = false)
        )
    ),
    Station(
        lat = 21.038000,
        lng = 105.808000,
        id = 9,
        listBicycle = listOf(
            Bicycle(id = "B17", type = "Electric", batteryPercentage = 55, isAvailable = true),
            Bicycle(id = "B18", type = "Standard", batteryPercentage = 0, isAvailable = true)
        )
    ),
    Station(
        lat = 21.039000,
        lng = 105.809000,
        id = 10,
        listBicycle = listOf(
            Bicycle(id = "B19", type = "Electric", batteryPercentage = 45, isAvailable = true),
            Bicycle(id = "B20", type = "Standard", batteryPercentage = 0, isAvailable = false)
        )
    )
)

