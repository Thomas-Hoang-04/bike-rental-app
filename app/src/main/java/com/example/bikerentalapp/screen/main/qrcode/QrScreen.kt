package com.example.bikerentalapp.screen.main.qrcode

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.camera.core.Preview
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor

@Composable
fun QrCodeScreen(viewModel: QRScannerViewModel, onQRCodeScanned: (String) -> Unit){
    val context = LocalContext.current
    val lifecycleOwner = context as LifecycleOwner
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val hasCameraPermission by viewModel.cameraPermissionGranted.collectAsState()
    val cameraPermission = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission() ) { isGranted ->
        viewModel.setCameraPermissionGranted(isGranted)
    }

    if(hasCameraPermission){
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { androidViewContext ->
                    val previewView = PreviewView(androidViewContext)

                    // Bind the CameraX lifecycle
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build().also {
                        it.surfaceProvider = previewView.surfaceProvider
                    }

                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also { analysis ->
                            analysis.setAnalyzer(Dispatchers.Default.asExecutor()) { imageProxy ->
                                viewModel.analyzeImage(imageProxy, onQRCodeScanned)
                            }
                        }

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    previewView
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }else SideEffect{
        cameraPermission.launch(Manifest.permission.CAMERA)
    }
}

@Composable
fun QrScreen() {
    val viewModel = remember { QRScannerViewModel() }

    QrCodeScreen(
        viewModel = viewModel,
        onQRCodeScanned = { scannedResult ->
            // Handle the scanned result
            Log.d("QRScanner", "Scanned QR Code: $scannedResult")
        }
    )
}