package com.example.bikerentalapp.screen.main.qrcode

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor

@Composable
fun QrScreen(navController: NavController) {
    val viewModel = remember { QRScannerViewModel() }

    CameraScannerScreen(viewModel = viewModel, onQRCodeScanned = { qrCodeContent ->
        navController.navigate("qr_result/$qrCodeContent"){
            popUpTo(navController.graph.startDestinationId){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    })
}

@Composable
fun CameraScannerScreen(viewModel: QRScannerViewModel, onQRCodeScanned: (String) -> Unit){
    val context = LocalContext.current
    val lifecycleOwner = context as LifecycleOwner
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val hasCameraPermission by viewModel.cameraPermissionGranted.collectAsState()
    val cameraPermission = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission() ) { isGranted ->
        viewModel.setCameraPermissionGranted(isGranted)
    }

    var cameraControl: CameraControl? by remember { mutableStateOf(null) }
    var isTorchOn by remember { mutableStateOf(false) } // Torch state

    if(hasCameraPermission){
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                // Top Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .weight(0.5f)
                ) {
                    Text(
                        text = "Quét mã",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                // Camera Preview
                Box(
                    modifier = Modifier
                        .weight(2.5f)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
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
                                val camera = cameraProvider.bindToLifecycle(
                                    lifecycleOwner,
                                    cameraSelector,
                                    preview,
                                    imageAnalysis
                                )
                                cameraControl = camera.cameraControl
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                            previewView
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Bottom Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CircularButtonWithText(
                        icon = Icons.Default.Keyboard,
                        text = "Nhập mã",
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    CircularButtonWithText(
                        icon = Icons.Default.FlashOn,
                        text = "Đèn pin",
                        onClick = {
                            isTorchOn = !isTorchOn
                            cameraControl?.enableTorch(isTorchOn)
                        }
                    )
                }
            }
        }
    }else SideEffect{
        cameraPermission.launch(Manifest.permission.CAMERA)
    }
}

@Composable
fun CircularButtonWithText(icon:ImageVector,text:String,onClick : ()->Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .border(1.dp,Color.Gray, CircleShape)
                .size(80.dp)
        ) {
            Icon(icon, modifier = Modifier.size(30.dp), contentDescription = null)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text,
            style = MaterialTheme.typography.bodyLarge)
    }
}