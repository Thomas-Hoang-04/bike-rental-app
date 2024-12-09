package com.example.bikerentalapp.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikerentalapp.components.HeadingTextComponent
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.example.bikerentalapp.ui.theme.TextColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import com.example.bikerentalapp.api.data.*
import com.example.bikerentalapp.api.network.RetrofitInstances
import com.example.bikerentalapp.components.LoadingScreen
import com.example.bikerentalapp.components.makeToast
import com.google.gson.Gson

@Composable
fun OTPScreen(
    phoneNumber: String,
    purpose: OTPPurpose,
    onClick: (OTPClicks) -> Unit
) {
    var otpValues by remember { mutableStateOf(List(5) { "" }) }
    val focusRequesters = remember { List(5) { FocusRequester() } }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val isOTPComplete = otpValues.all { it.isNotEmpty() }
    val isWaiting = remember { mutableStateOf(true) }
    val isLoading = remember { mutableStateOf(false) }
    val time = remember { mutableStateOf(60) }
    val retrofit = RetrofitInstances.Auth

    LaunchedEffect(isOTPComplete) {
        if (isOTPComplete) {
            focusManager.clearFocus()
            (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
                (context as? Activity)?.currentFocus?.let { view ->
                    hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        }
    }

    LaunchedEffect(isWaiting.value) {
        while (isWaiting.value) {
            delay(1000)
            time.value--
            if (time.value == 0) {
                isWaiting.value = false
            }
        }
    }


    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {
            IconButton(
                onClick = { onClick(OTPClicks.BackToSignIn) },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to Sign in Screen"
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFFE8F5E9), CircleShape)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Confirm Icon",
                    tint = PrimaryColor,
                    modifier = Modifier
                        .size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            HeadingTextComponent(value = "Xác thực")

            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                text = "Kiểm tra mã OTP trong tin nhắn SMS để xác thực",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 40.dp),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    lineHeight = 20.sp
                ),
                color = TextColor,
                textAlign = TextAlign.Center,
                fontSize = TextStyle(
                    platformStyle = PlatformTextStyle(includeFontPadding = false),
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.Both
                    )
                ).fontSize
            )

            Spacer(modifier = Modifier.padding(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                otpValues.forEachIndexed { index, value ->
                    var isFocused by remember { mutableStateOf(false) }

                    BasicTextField(
                        value = value,
                        onValueChange = { newValue ->
                            if (newValue.length <= 1) {
                                val newOtpValues = otpValues.toMutableList()
                                newOtpValues[index] = newValue
                                otpValues = newOtpValues

                                if (newValue.isNotEmpty() && index < 4) {
                                    scope.launch {
                                        delay(10)
                                        focusRequesters[index + 1].requestFocus()
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .size(50.dp)
                            .border(
                                width = 2.dp,
                                color = if (isFocused) PrimaryColor else Color.Gray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .focusRequester(focusRequesters[index])
                            .onFocusChanged {
                                isFocused = it.isFocused
                            }
                            .onKeyEvent {
                                if (it.nativeKeyEvent.keyCode == 67) {
                                    if (index > 0) {
                                        val newOtpValues = otpValues.toMutableList()
                                        newOtpValues[index] = ""
                                        otpValues = newOtpValues
                                        focusRequesters[index - 1].requestFocus()
                                    }
                                }
                                false
                            },
                        textStyle = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                innerTextField()
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    when (purpose) {
                                    OTPPurpose.RESET_PASSWORD -> onClick(OTPClicks.PasswordResetConfirm)
                                    OTPPurpose.SIGNUP -> onClick(OTPClicks.OTPConfirm)
                                }
//                    isLoading.value = true
//                    scope.launch {
//                        val otp = otpValues.joinToString("")
//                        val res = retrofit.authAPI.verifyOTP(
//                            OTPValidationRequest(otp, phoneNumber)
//                        )
//                        if (res.isSuccessful) {
//                            val body: OTPResponse = res.body()!!
//                            if (body.status == OTPStatus.SUCCESS) {
//                                isLoading.value = false
//                                when (purpose) {
//                                    OTPPurpose.RESET_PASSWORD -> onClick(OTPClicks.PasswordResetConfirm)
//                                    OTPPurpose.SIGNUP -> onClick(OTPClicks.OTPConfirm)
//                                }
//                            } else {
//                                makeToast(context, body.message)
//                            }
//                        } else {
//                            val e = res.errorBody()?.string()
//                            val eBody = Gson().fromJson(e, OTPResponse::class.java)
//                            makeToast(context, eBody.message)
//                            isLoading.value = false
//                        }
//                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(start = 6.dp, end = 6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isOTPComplete) PrimaryColor else Color.Gray,
                    contentColor = Color.White,
                ),
                shape = RoundedCornerShape(6.dp),
                enabled = isOTPComplete
            ) {
                Text(
                    text = "Xác thực",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text(
                    text = "Không nhận được mã?",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isWaiting.value) "Gửi lại sau ${time.value} giây" else "Gửi lại",
                    style = TextStyle(
                        color = PrimaryColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier
                        .clickable(!isWaiting.value) {
//                            isLoading.value = true
//                            scope.launch {
//                                val res = retrofit.authAPI.sendOTP(
//                                    OTPRequest(username = phoneNumber, "+84964704623", purpose)
//                                )
//                                if (res.isSuccessful) {
//                                    val body: OTPResponse = res.body()!!
//                                    makeToast(context, body.message)
//                                    isLoading.value = false
//                                    time.value = 60
//                                    isWaiting.value = true
//                                } else {
//                                    val e = res.errorBody()?.string()
//                                    val eBody = Gson().fromJson(e, OTPResponse::class.java)
//                                    makeToast(context, eBody.message)
//                                    isLoading.value = false
//                                }
//                            }
                        }
                )
            }
        }
    }
    if (isLoading.value) {
        LoadingScreen()
    }
}

sealed class OTPClicks {
    data object BackToSignIn: OTPClicks()
    data object OTPConfirm: OTPClicks()
    data object PasswordResetConfirm: OTPClicks()
}

@Preview
@Composable
fun OTPScreenPreview() {
    OTPScreen("", OTPPurpose.RESET_PASSWORD) {
        _ ->
    }
}