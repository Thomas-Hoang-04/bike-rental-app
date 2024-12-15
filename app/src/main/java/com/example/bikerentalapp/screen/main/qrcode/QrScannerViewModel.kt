package com.example.bikerentalapp.screen.main.qrcode

import android.media.Image
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikerentalapp.api.data.Bike
import com.example.bikerentalapp.api.network.RetrofitInstances
import com.example.bikerentalapp.model.AccountViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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

class QrResultViewModel(private val accountViewModel: AccountViewModel,private val bikePlate : String) : ViewModel(){
    private val _bike : MutableStateFlow<Bike?> = MutableStateFlow(null)
    val bike = _bike.asStateFlow()
    init {
        viewModelScope.launch {
            val response = RetrofitInstances.Query(accountViewModel.token.value).queryAPI.getBikeByPlate(bikePlate)
            if(response.isSuccessful){
                try {
                    _bike.value = response.body()?.data?.first()
                    Log.d("QrResultViewModel", "Bike: $_bike")
                }catch (e: Exception){
                    Log.d("QrResultViewModel", "Error: ${e.message}")
                }
            }else{
                Log.d("QrResultViewModel", "Error: ${response.errorBody()?.string()}")
            }
        }
    }
}
