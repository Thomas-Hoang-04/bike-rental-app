package com.example.bikerentalapp.screen.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.bikerentalapp.model.AccountViewModel
import com.example.bikerentalapp.model.SignInViewModel
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.example.bikerentalapp.ui.theme.disablePrimaryColor
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    onClick: (SignInClicks) -> Unit,
    viewModel: SignInViewModel = viewModel(),
    accModel: AccountViewModel
) {
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp

//    val isLoading = remember { mutableStateOf(false) }

    val retrofit = RetrofitInstances.Auth
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val username = accModel.username.collectAsState()
    val token = accModel.token.collectAsState()
    val details = accModel.details.collectAsState()

//    if (isLoading.value) {
//        LoadingScreen()
//    }

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
                        value = viewModel.phoneNumber,
                        onValueChange = viewModel::updatePhoneNumber,
                        error = viewModel.phoneNumberError
                    )

                    TextInput(
                        label = "Mật khẩu",
                        placeholder = "Nhập mật khẩu",
                        inputType = InputType.Password,
                        value = viewModel.password,
                        onValueChange = viewModel::updatePassword,
                        error = viewModel.passwordError
                    )

                    ButtonComponent(
                        value = stringResource(id = R.string.sign_in),
                        onClick = {
                            viewModel.signIn {
                                scope.launch {
                                    val res = retrofit.authAPI.login(
                                        LoginRequest(
                                            viewModel.phoneNumber,
                                            viewModel.password
                                        )
                                    )
                                    if (res.isSuccessful) {
                                        delay(100)
                                        val body = res.body() as LoginResponse
                                        accModel.setUsername(body.targetUser.username)
                                        accModel.setDetails(body.targetUser.details)
                                        accModel.setToken(body.accessToken)
//                                        Log.d("Username", username.value)
//                                        Log.d("Token", token.value)
//                                        Log.d("Details", details.value.toString())
                                        onClick(SignInClicks.SignInSuccess)
                                    } else {
//                                        isLoading.value = false
                                        val e = res.errorBody()?.string()
                                        val errorResponse = Gson().fromJson(e, ErrorResponse::class.java)
                                        when(errorResponse.message) {
                                            "Bad credentials" -> {
                                                viewModel.passwordError = "Mật khẩu không đúng"
                                            }
                                            "Username ${viewModel.phoneNumber} not found" -> {
                                                viewModel.phoneNumberError = "Tài khoản không tồn tại"
                                            }
                                            else -> {
                                                makeToast(context, "Lỗi đăng nhập. Vui lòng thử lại")
                                                Log.d("Error", errorResponse.message)
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
                                onClick(SignInClicks.ForgotPassword)
                            }
                            .fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    ClickableTextLoginComponent(
                        tryingToLogin = false,
                        onTextSelected = {
                            onClick(SignInClicks.SignUp)
                        }
                    )
                }
            }
        }
    }
}

sealed class SignInClicks {
    data object SignUp: SignInClicks()
    data object ForgotPassword: SignInClicks()
    data object SignInSuccess: SignInClicks()
}

@Preview
@Composable
fun SignInPreview() {
    SignInScreen({}, accModel = AccountViewModel())
}