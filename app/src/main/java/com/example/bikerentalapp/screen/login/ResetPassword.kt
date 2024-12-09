package com.example.bikerentalapp.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bikerentalapp.api.data.ResetPwdRequest
import com.example.bikerentalapp.api.network.RetrofitInstances
import com.example.bikerentalapp.components.ButtonComponent
import com.example.bikerentalapp.components.HeadingTextComponent
import com.example.bikerentalapp.components.LoadingScreen
import com.example.bikerentalapp.components.TextInput
import com.example.bikerentalapp.ui.theme.PrimaryColor
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(
    username: String
) {
    val newPassword = remember { mutableStateOf("") }
    val retypeNewPassword = remember { mutableStateOf("") }

    val passwordError = remember { mutableStateOf<String?>(null) }
    val retypePasswordError = remember { mutableStateOf<String?>(null) }

    val isPasswordValid = newPassword.value.isNotBlank()
            && newPassword.value == retypeNewPassword.value

    val retrofit = RetrofitInstances.Auth
    val scope = rememberCoroutineScope()
    val isLoading = remember { mutableStateOf(false) }

    if (isLoading.value) {
        LoadingScreen()
    }

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeadingTextComponent("Đặt lại mật khẩu")

            Spacer(modifier = Modifier.height(24.dp))

            TextInput(
                label = "Mật khẩu mới",
                placeholder = "Nhập mật khẩu mới",
                value = newPassword.value,
                required = true,
                error = passwordError.value,
                onValueChange = { newPassword.value = it },
                onFocusChange = { focused ->
                    if (focused) {
                        passwordError.value = when {
                            newPassword.value.isBlank() -> "Mật khẩu không được để trống"
                            newPassword.value.length < 6 -> "Mật khẩu phải có ít nhất 6 ký tự"
                            else -> null
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextInput(
                label = "Nhập lại mật khẩu mới",
                placeholder = "Nhập lại mật khẩu mới",
                value = newPassword.value,
                error = retypePasswordError.value,
                required = true,
                onValueChange = { newPassword.value = it },
                onFocusChange = { focused ->
                    if (focused) {
                        retypePasswordError.value = when {
                            retypeNewPassword.value.isBlank() -> "Mật khẩu không được để trống"
                            retypeNewPassword.value != newPassword.value -> "Mật khẩu không khớp"
                            else -> null
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ButtonComponent(
                value = "Đặt lại mật khẩu",
                onClick = {
                    isLoading.value = true
                    scope.launch {
                        val res = retrofit.authAPI.forgotPassword(
                            ResetPwdRequest(
                                username,
                                newPassword = newPassword.value
                            )
                        )
                        if (res.isSuccessful) {
                            // TODO
                        } else {
                            isLoading.value = false
                            // TODO
                        }
                    }
                },
                enabled = isPasswordValid,
                color = ButtonDefaults.buttonColors(
                    containerColor = if (isPasswordValid) PrimaryColor else Color.Gray,
                    contentColor = if (isPasswordValid) Color.White else Color.Gray
                )
            )
        }
    }
}

@Preview
@Composable
fun ResetPasswordPreview() {
    ResetPasswordScreen("")
}