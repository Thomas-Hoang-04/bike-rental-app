package com.example.bikerentalapp.screen.main.qrcode

import android.media.Image
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class QRScannerViewModel : ViewModel() {
    private val scanner: BarcodeScanner = BarcodeScanning.getClient()
    private val _cameraPermissionGranted = MutableStateFlow(false)
    val cameraPermissionGranted = _cameraPermissionGranted.asStateFlow()

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
                    for (barcode in barcodes) {
                        barcode.rawValue?.let {
                            onResult(it) // Pass the scanned QR code to the callback
                        }
                    }
                }
                .addOnFailureListener { }
                .addOnCompleteListener { imageProxy.close() }
        } else {
            imageProxy.close()
        }
    }
}
