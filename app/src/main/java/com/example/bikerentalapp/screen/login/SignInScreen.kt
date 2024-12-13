package com.example.bikerentalapp.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.bikerentalapp.api.network.RetrofitInstances
import com.example.bikerentalapp.components.*
import com.example.bikerentalapp.model.SignInViewModel
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.example.bikerentalapp.ui.theme.disablePrimaryColor
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    onClick: (SignInClicks) -> Unit,
    viewModel: SignInViewModel = viewModel()
) {
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp

    val retrofit = RetrofitInstances.Auth
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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
                                        onClick(SignInClicks.SignInSuccess)
                                    } else {
                                        val e = res.errorBody()?.string()
                                        val errorResponse = Gson().fromJson(e, ErrorResponse::class.java)
                                        val errorMessage = when(errorResponse.message) {
                                            "Bad Credentials" -> "Tài khoản hoặc mật khẩu không đúng"
                                            else -> "Lỗi đăng nhập"
                                        }
                                        makeToast(context, errorMessage)
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
    SignInScreen({})
}