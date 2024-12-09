package com.example.bikerentalapp.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikerentalapp.api.data.OTPPurpose
import com.example.bikerentalapp.api.data.OTPRequest
import com.example.bikerentalapp.api.data.OTPResponse
import com.example.bikerentalapp.api.data.OTPStatus
import com.example.bikerentalapp.api.network.RetrofitInstances
import com.example.bikerentalapp.components.HeadingTextComponent
import com.example.bikerentalapp.components.LoadingScreen
import com.example.bikerentalapp.components.makeToast
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.example.bikerentalapp.ui.theme.TextColor
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ForgotPassword(
    onClick: (ForgotPasswordClicks, String) -> Unit,
) {
    val phoneNumber = remember { mutableStateOf("") }
    val isPhoneNumberValid = phoneNumber.value.matches(Regex("^0[1-9][0-9]{8}\$"))
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }
    val retrofit = RetrofitInstances.Auth

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
                onClick = { onClick(ForgotPasswordClicks.BackToSignIn, "") },
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
            verticalArrangement = Arrangement.Center
        ) {
            HeadingTextComponent(value = "Nhập số điện thoại")
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "Chúng tôi sẽ gửi cho bạn một mã OTP để xác thực",
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                OutlinedTextField(
                    value = phoneNumber.value,
                    onValueChange = {phoneNumber.value = it},
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {},
                    label = null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryColor,
                        cursorColor = PrimaryColor
                    )
                )

                if (phoneNumber.value.isEmpty()) {
                    Text(
                        text = "Nhập số điện thoại",
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterStart)
                    )
                }
            }

            Spacer(modifier = Modifier.padding(6.dp))

            Button(
                onClick = {
                        onClick(ForgotPasswordClicks.OTPConfirm, phoneNumber.value)
//                    isLoading.value = true
//                    coroutineScope.launch {
//                        val res = retrofit.authAPI.sendOTP(
//                            OTPRequest(
//                                username = phoneNumber.value,
//                                "+84964704623",
//                                OTPPurpose.RESET_PASSWORD)
//                        )
//                        if (res.isSuccessful) {
//                            val body: OTPResponse = res.body()!!
//                            if (body.status == OTPStatus.SUCCESS) {
//                                isLoading.value = false
//                                delay(100)
//                                onClick(ForgotPasswordClicks.OTPConfirm, phoneNumber.value)
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
                    .padding(4.dp)
                    .height(48.dp)
                ,
                enabled = isPhoneNumberValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isPhoneNumberValid) PrimaryColor else Color.LightGray,
                    contentColor = if (isPhoneNumberValid) Color.White else Color.Gray
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Tiếp tục",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    )
                )
            }
        }
    }

    if (isLoading.value) {
        LoadingScreen()
    }
}

sealed class ForgotPasswordClicks {
    data object BackToSignIn: ForgotPasswordClicks()
    data object OTPConfirm: ForgotPasswordClicks()
}

@Preview
@Composable
fun ForgotPasswordPreview() {
    ForgotPassword {
        _, _ ->
    }
}