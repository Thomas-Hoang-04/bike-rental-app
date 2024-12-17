package com.example.bikerentalapp.screen.main.qrcode

import android.media.Image
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikerentalapp.api.data.Bike
import com.example.bikerentalapp.api.data.BikeAction
import com.example.bikerentalapp.api.data.BikeRenting
import com.example.bikerentalapp.api.data.Geolocation
import com.example.bikerentalapp.api.data.TicketTypes
import com.example.bikerentalapp.api.data.TripRequest
import com.example.bikerentalapp.api.network.RetrofitInstances
import com.example.bikerentalapp.model.AccountViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.OffsetDateTime
import java.time.ZoneId

class QRScannerViewModel : ViewModel() {
    private val scanner: BarcodeScanner = BarcodeScanning.getClient()
    private val _cameraPermissionGranted = MutableStateFlow(false)
    val cameraPermissionGranted = _cameraPermissionGranted.asStateFlow()
    private val _isTorchOn = MutableStateFlow(false)
    var cameraControl: CameraControl? = null

    private var scannedOnce = false

    fun setCameraPermissionGranted(granted: Boolean) {
        _cameraPermissionGranted.value = granted
    }

    @OptIn(ExperimentalGetImage::class)
    fun analyzeImage(imageProxy: ImageProxy, onResult: (String) -> Unit) {
        val mediaImage: Image? = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if(barcodes.isNotEmpty()) {
                        if(!scannedOnce) {
                            onResult(barcodes[0].rawValue ?: "")
                            scannedOnce = true
                        }
                    }
                }
                .addOnFailureListener { }
                .addOnCompleteListener { imageProxy.close() }
        } else {
            imageProxy.close()
        }
    }

    fun toggleTorch() {
        _isTorchOn.value = !_isTorchOn.value
        cameraControl?.enableTorch(_isTorchOn.value)
    }

    fun isValidFormat(input: String): Boolean {
        val regex = Regex("^(E?X\\d{2}[A-Z]-\\d{3}\\.\\d{2})$")

        val sanitizedInput = input.trim().replace("\uFEFF", "")
        val firstLine = sanitizedInput.lineSequence().first().replace("\r", "").replace("\n", "").trim()
        return regex.matches(firstLine)
    }
}

class QrResultViewModel(private val accountViewModel: AccountViewModel, qrCodeContent : String) : ViewModel(){
    private val _bike : MutableStateFlow<Bike?> = MutableStateFlow(null)
    val bike = _bike.asStateFlow()
    private val sanitizedInput = qrCodeContent.trim().replace("\uFEFF", "")
    val firstLine = sanitizedInput.lineSequence().first().replace("\r", "").replace("\n", "").trim()
    private val secondLine = sanitizedInput.lineSequence().elementAtOrNull(1)?.trim()?.split(" ")
    init {
        viewModelScope.launch {
            val response = RetrofitInstances.Query(accountViewModel.token.value).queryAPI.getBikeByPlate(firstLine)
            if(response.isSuccessful){
                try {
                    _bike.value = response.body()?.data?.first()
//                    Log.d("QrResultViewModel", "Bike: ${_bike.value}")
                }catch (e: Exception){
                    Log.d("QrResultViewModel", "Error: ${e.message}")
                }
            }else{
                Log.d("QrResultViewModel", "Error: ${response.errorBody()?.string()}")
            }
        }
    }

    fun rentBike(onRentBikeSuccessfully: () -> Unit){
        val bikeRenting = BikeRenting(
            plate = firstLine,
            latitude = secondLine?.get(0)?.toDouble() ?: 0.0,
            longitude = secondLine?.get(1)?.toDouble() ?: 0.0,
            action = BikeAction.RENT,
            battery = bike.value?.battery,
        )
        viewModelScope.launch {
            val response = RetrofitInstances.Query(accountViewModel.token.value).queryAPI.rentBike(bikeRenting)
            if(response.isSuccessful){
                onRentBikeSuccessfully()
            }else{
                Log.d("QrResultViewModel", "Error: ${response.errorBody()?.string()}")
            }
        }
    }
}

class TrackingMapViewModel(
    private val accountViewModel: AccountViewModel,
    private val startTime : OffsetDateTime = OffsetDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")),
    qrCodeContent: String
):ViewModel(){
    private var _endTime : OffsetDateTime? = null
    private val sanitizedInput = qrCodeContent.trim().replace("\uFEFF", "")
    val firstLine = sanitizedInput.lineSequence().first().replace("\r", "").replace("\n", "").trim()
    private val secondLine = sanitizedInput.lineSequence().elementAtOrNull(1)?.trim()?.split(" ")
    private val thirdLine = sanitizedInput.lineSequence().elementAtOrNull(2)?.trim()
    var fee = 0
    var route = listOf<Geolocation>()
    val id = generateRandomString()

    fun returnBike(bikeId: String,onCreateTripSuccessfully: () -> Unit){
        viewModelScope.launch{
            val bikeRenting = BikeRenting(
                plate = bikeId,
                latitude = secondLine?.get(0)?.toDouble() ?: 0.0,
                longitude = secondLine?.get(1)?.toDouble() ?: 0.0,
                action = BikeAction.RETURN,
                battery = 0
            )
            val response = RetrofitInstances.Query(accountViewModel.token.value).queryAPI.rentBike(bikeRenting)
            if(response.isSuccessful){
                _endTime =  OffsetDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))
                val duration = Duration.ofMinutes(Duration.between(startTime, _endTime).toMinutes()).toString()

                val tripRequest = TripRequest(
                    username = accountViewModel.username.value,
                    bikePlate = bikeId,
                    startTime = startTime.toString(),
                    endTime = _endTime.toString(),
                    travelTime = duration,
                    startAddress = "$thirdLine",
                    endAddress = "$thirdLine",
                    fee = fee,
                    id = id,
                    ticketType = TicketTypes.SINGLE,
                    route = route
                )

                val tripResponse = RetrofitInstances.Query(accountViewModel.token.value).queryAPI.createTrip(tripRequest)

                if(tripResponse.isSuccessful) {
                    onCreateTripSuccessfully()
                    Log.d("TrackingMapViewModel", "$tripRequest")
                }else{
                    Log.d("TrackingMapViewModel", "Error: ${tripResponse.errorBody()?.string()}")
                }
            }else{
                Log.d("TrackingMapViewModel", "Error: ${response.errorBody()?.string()}")
            }
        }
    }

//    fun returnBike(bikeId: String, onReturnBikeSuccessfully: () -> Unit){
//        viewModelScope.launch{
//            val bikeRenting = BikeRenting(
//                plate = bikeId,
//                latitude = secondLine?.get(0)?.toDouble() ?: 0.0,
//                longitude = secondLine?.get(1)?.toDouble() ?: 0.0,
//                action = BikeAction.RETURN,
//                battery = 0
//            )
//            val response = RetrofitInstances.Query(accountViewModel.token.value).queryAPI.rentBike(bikeRenting)
//            if(response.isSuccessful){
//                onReturnBikeSuccessfully()
//            }else{
//                Log.d("TrackingMapViewModel", "Error: ${response.errorBody()?.string()}")
//            }
//        }
//    }
//
//    fun createTrips(bikeId: String,onCreateTripSuccessfully: () -> Unit){
//        val id = generateRandomString()
//        viewModelScope.launch {
//            _endTime = OffsetDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))
//            val duration = Duration.ofMinutes(Duration.between(startTime, _endTime).toMinutes()).toString()
//            val tripRequest = TripRequest(
//                username = accountViewModel.username.value,
//                bikePlate = bikeId,
//                startTime = startTime.toString(),
//                endTime = _endTime.toString(),
//                travelTime = duration,
//                startAddress = "$thirdLine",
//                endAddress = "$thirdLine",
//                fee = fee,
//                id = id,
//                ticketType = TicketTypes.SINGLE,
//                route = route
//            )
//
//            val tripResponse = RetrofitInstances.Query(accountViewModel.token.value).queryAPI.createTrip(tripRequest)
//
//            if(tripResponse.isSuccessful) {
//                onCreateTripSuccessfully()
//                Log.d("TrackingMapViewModel", "$tripRequest")
//            }else{
//                Log.d("TrackingMapViewModel", "Error: ${tripResponse.errorBody()?.string()}")
//            }
//        }
//    }

    private fun generateRandomString(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..15)
            .map { chars.random() }
            .joinToString("")
    }

}
