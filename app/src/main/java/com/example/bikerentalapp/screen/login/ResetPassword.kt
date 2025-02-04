package com.example.bikerentalapp.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.bikerentalapp.api.data.ErrorResponse
import com.example.bikerentalapp.api.data.ResetPwdRequest
import com.example.bikerentalapp.api.network.RetrofitInstances
import com.example.bikerentalapp.components.ButtonComponent
import com.example.bikerentalapp.components.HeadingTextComponent
import com.example.bikerentalapp.components.InputType
import com.example.bikerentalapp.components.LoadingScreen
import com.example.bikerentalapp.components.LocalNavigation
import com.example.bikerentalapp.components.TextInput
import com.example.bikerentalapp.components.makeToast
import com.example.bikerentalapp.navigation.Screens
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(username: String) {
    val newPassword = remember { mutableStateOf("") }
    val retypeNewPassword = remember { mutableStateOf("") }

    val passwordError = remember { mutableStateOf<String?>(null) }
    val retypePasswordError = remember { mutableStateOf<String?>(null) }

    val retrofit = RetrofitInstances.Auth
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }
    val navController = LocalNavigation.current

    val validatePassword = {
        passwordError.value = when {
            newPassword.value.isBlank() -> "Mật khẩu không được để trống"
            newPassword.value.length < 6 -> "Mật khẩu phải có ít nhất 6 ký tự"
            else -> null
        }
        passwordError.value == null
    }

    val validateRetypePassword = {
        retypePasswordError.value = when {
            retypeNewPassword.value.isBlank() -> "Mật khẩu không được để trống"
            retypeNewPassword.value != newPassword.value -> "Mật khẩu không khớp"
            else -> null
        }
        retypePasswordError.value == null
    }

    val isPasswordValid by remember {
        derivedStateOf {
            validatePassword() && validateRetypePassword()
        }
    }

    if (isLoading.value) {
        LoadingScreen()
    }

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp, start = 6.dp)
        ) {
            IconButton(
                onClick = { navController.navigate(Screens.Auth.Login) },
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
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFFE8F5E9), CircleShape)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LockReset,
                    contentDescription = "Confirm Icon",
                    tint = PrimaryColor,
                    modifier = Modifier
                        .size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            HeadingTextComponent("Đặt lại mật khẩu")

            Spacer(modifier = Modifier.height(24.dp))

            TextInput(
                label = "Mật khẩu mới",
                placeholder = "Nhập mật khẩu mới",
                inputType = InputType.Password,
                value = newPassword.value,
                required = true,
                error = passwordError.value,
                onValueChange = {
                    newPassword.value = it
                    validatePassword()
                },
                onFocusChange = { focused ->
                    if (focused) {
                        validatePassword()
                    } else {
                        passwordError.value = null
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextInput(
                label = "Nhập lại mật khẩu mới",
                placeholder = "Nhập lại mật khẩu mới",
                inputType = InputType.Password,
                value = retypeNewPassword.value,
                error = retypePasswordError.value,
                required = true,
                onValueChange = {
                    retypeNewPassword.value = it
                    validateRetypePassword()
                },
                onFocusChange = { focused ->
                    if (focused) {
                        validateRetypePassword()
                    } else {
                        retypePasswordError.value = null
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                            delay(100)
                            // TODO: Add dialog to inform user that password has been reset
                            navController.navigate(Screens.Auth.Login) {
                                popUpTo(Screens.Auth.Login) { inclusive = true }
                            }
                        } else {
                            isLoading.value = false
                            val e = res.errorBody()?.string()
                            val eBody = Gson().fromJson(e, ErrorResponse::class.java)
                            makeToast(context, eBody.message)
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