package com.example.bikerentalapp.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bikerentalapp.R
import com.example.bikerentalapp.api.data.ErrorResponse
import com.example.bikerentalapp.api.data.LoginRequest
import com.example.bikerentalapp.api.data.LoginResponse
import com.example.bikerentalapp.api.network.RetrofitInstances
import com.example.bikerentalapp.components.*
import com.example.bikerentalapp.model.SignInViewModel
import com.example.bikerentalapp.navigation.Screens
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.example.bikerentalapp.ui.theme.disablePrimaryColor
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignInScreen() {
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp

    val navController = LocalNavigation.current
    val accModel = UserAccount.current
    val dataStore = LocalDataStore.current

    val isLoading = remember { mutableStateOf(false) }
    val loginModel = viewModel<SignInViewModel>()

    val retrofit = RetrofitInstances.Auth
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_bg),
                contentDescription = "Login Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio((screenHeight/2.1F) / config.screenWidthDp.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    HeadingTextComponent(value = stringResource(id = R.string.sign_in))

                    Spacer(modifier = Modifier.padding(2.dp))

                    TextInput(
                        label = "Số điện thoại",
                        placeholder = "Nhập số điện thoại",
                        inputType = InputType.Phone,
                        value = loginModel.phoneNumber,
                        onValueChange = loginModel::updatePhoneNumber,
                        error = loginModel.phoneNumberError
                    )

                    TextInput(
                        label = "Mật khẩu",
                        placeholder = "Nhập mật khẩu",
                        inputType = InputType.Password,
                        value = loginModel.password,
                        onValueChange = loginModel::updatePassword,
                        error = loginModel.passwordError
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = loginModel.rememberMe,
                                onCheckedChange = loginModel::updateRememberMe,
                                colors = CheckboxDefaults.colors(
                                    checkedColor = PrimaryColor,
                                    checkmarkColor = Color.White
                                ),
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(start = 4.dp)
                                    .scale(0.8f)
                            )
                            Text(
                                text = "Lưu tài khoản",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontStyle = FontStyle.Normal,
                                    fontWeight = FontWeight.Medium
                                ),
                                color = PrimaryColor,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) { loginModel.updateRememberMe(!loginModel.rememberMe) }
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.forgot_password),
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Medium
                            ),
                            color = PrimaryColor,
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    navController.navigate(Screens.Auth.ForgotPassword)
                                },
                        )
                    }

                    ButtonComponent(
                        value = stringResource(id = R.string.sign_in),
                        onClick = {
                            loginModel.signIn {
                                isLoading.value = true
                                scope.launch {
                                    val res = retrofit.authAPI.login(
                                        LoginRequest(
                                            loginModel.phoneNumber,
                                            loginModel.password
                                        )
                                    )
                                    if (res.isSuccessful) {
                                        delay(100)
                                        val body = res.body() as LoginResponse
                                        if (loginModel.rememberMe) accModel.store(dataStore, body, loginModel.password)
                                        else accModel.store(body)
                                        navController.navigate(Screens.Main.Home) {
                                            popUpTo(Screens.Auth) { inclusive = true }
                                        }
                                    } else {
                                        isLoading.value = false
                                        val e = res.errorBody()?.string()
                                        val errorResponse = Gson().fromJson(e, ErrorResponse::class.java)
                                        when(errorResponse.message) {
                                            "Bad credentials" -> {
                                                loginModel.passwordError = "Mật khẩu không đúng"
                                            }
                                            "Username ${loginModel.phoneNumber} not found" -> {
                                                loginModel.phoneNumberError = "Tài khoản không tồn tại"
                                            }
                                            else -> {
                                                makeToast(context, "Lỗi đăng nhập. Vui lòng thử lại")
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        color = ButtonColors(
                            contentColor = Color.White,
                            containerColor = PrimaryColor,
                            disabledContentColor = Color.Gray,
                            disabledContainerColor = disablePrimaryColor
                        )
                    )


                    Spacer(modifier = Modifier.weight(1f))

                    ClickableTextLoginComponent(
                        tryingToLogin = false,
                        onTextSelected = {
                            navController.navigate(Screens.Auth.SignUp)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    SignInScreen()
}