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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun OTPScreen(
    onClick: (OTPClicks) -> Unit
) {
    var otpValues by remember { mutableStateOf(List(5) { "" }) }
    val focusRequesters = remember { List(5) { FocusRequester() } }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val isOTPComplete = otpValues.all { it.isNotEmpty() }

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
                text = "Chúng tôi sẽ gửi cho bạn một mã OTP để xác thực",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 40.dp),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal
                ),
                color = TextColor,
                textAlign = TextAlign.Center
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

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    // TODO:Handle OTP verification logic here
                    val otp = otpValues.joinToString("")
                    onClick(OTPClicks.OTPConfirm)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
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
                    text = "Gửi lại",
                    style = TextStyle(
                        color = PrimaryColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier
                        .clickable { /* Handle resend */ }
                )
            }
        }
    }
}

sealed class OTPClicks {
    data object BackToSignIn: OTPClicks()
    data object OTPConfirm: OTPClicks()
}

@Preview
@Composable
fun OTPScreenPreview() {
    OTPScreen {}
}