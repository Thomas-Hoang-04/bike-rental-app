package com.example.bikerentalapp.screen.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bikerentalapp.R
import com.example.bikerentalapp.api.data.*
import com.example.bikerentalapp.api.network.RetrofitInstances
import com.example.bikerentalapp.components.*
import com.example.bikerentalapp.model.SignUpViewModel
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    isOTPVerified: Boolean,
    viewModel: SignUpViewModel,
    onClick: (SignUpClicks, String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val retrofit = RetrofitInstances.Auth
    val verified = remember { mutableStateOf(isOTPVerified) }
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(isOTPVerified) }

    if (isLoading.value) {
        LoadingScreen()
    }

    if (verified.value) {
        isLoading.value = true
        scope.launch {
            val res = retrofit.authAPI.signup(
                UserCreateRequest(
                    username = viewModel.phoneNumber,
                    password = viewModel.password,
                    details = UserDetails(
                        name = viewModel.name,
                        phoneNum = viewModel.phoneNumber,
                        email = viewModel.email,
                        dob = viewModel.dateOfBirth,
                    )
                )
            )
            if (res.isSuccessful) {
                verified.value = false
                delay(150)
                onClick(SignUpClicks.SignUpSuccess, "")
            } else {
                val e = res.errorBody()?.string()
                val eBody = Gson().fromJson(e, ErrorResponse::class.java)
                isLoading.value = false
                verified.value = false
                delay(50)
                makeToast(context, eBody.message)
                Log.d("SignUpScreen", "Error: ${eBody.message}")
            }
        }
    } else {
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.padding(top = 28.dp))

                HeadingTextComponent(value = stringResource(id = R.string.sign_up))

                Spacer(modifier = Modifier.padding(4.dp))

                HorizontalDivider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 6.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextInput(
                        label = "Số điện thoại",
                        placeholder = "Nhập số điện thoại",
                        inputType = InputType.Phone,
                        required = true,
                        value = viewModel.phoneNumber,
                        onValueChange = viewModel::updatePhoneNumber,
                        error = viewModel.phoneNumberError,
                        onFocusChange = { focused ->
                            if (focused) viewModel.updateFocusField(FocusField.PHONE_NUMBER)
                        },
                    )

                    TextInput(
                        label = "Tên của bạn",
                        placeholder = "Nhập tên của bạn",
                        inputType = InputType.Text,
                        required = true,
                        value = viewModel.name,
                        onValueChange = viewModel::updateName,
                        error = viewModel.nameError,
                        onFocusChange = { focused ->
                            if (focused) viewModel.updateFocusField(FocusField.NAME)
                        }
                    )

                    TextInput(
                        label = "Email",
                        placeholder = "Nhập email của bạn",
                        inputType = InputType.Email,
                        value = viewModel.email,
                        onValueChange = viewModel::updateEmail,
                        error = viewModel.emailError,
                        onFocusChange = { focused ->
                            if (focused) viewModel.updateFocusField(FocusField.EMAIL)
                        }
                    )

                    TextInput(
                        label = "Ngày sinh",
                        placeholder = "DD/MM/YYYY",
                        inputType = InputType.Text,
                        required = true,
                        value = viewModel.dateOfBirth,
                        onValueChange = viewModel::updateDateOfBirth,
                        error = viewModel.dateOfBirthError,
                        onFocusChange = { focused ->
                            if (focused) {
                                showDatePicker = true
                                viewModel.updateFocusField(FocusField.DATE_OF_BIRTH)
                            }
                        },
                        readOnly = true
                    )

                    if (showDatePicker) {
                        DatePickerDialog(
                            onDateSelected = { date ->
                                viewModel.updateDateOfBirth(date)
                                showDatePicker = false
                            },
                            onDismiss = { showDatePicker = false }
                        )
                    }

                    TextInput(
                        label = "Mật khẩu",
                        placeholder = "Nhập mật khẩu",
                        inputType = InputType.Password,
                        required = true,
                        value = viewModel.password,
                        onValueChange = viewModel::updatePassword,
                        error = viewModel.passwordError,
                        onFocusChange = { focused ->
                            if (focused) viewModel.updateFocusField(FocusField.PASSWORD)
                        }
                    )

                    TextInput(
                        label = "Nhập lại mật khẩu",
                        placeholder = "Nhập lại mật khẩu",
                        inputType = InputType.Password,
                        required = true,
                        value = viewModel.confirmPassword,
                        onValueChange = viewModel::updateConfirmPassword,
                        error = viewModel.confirmPasswordError,
                        onFocusChange = { focused ->
                            if (focused) viewModel.updateFocusField(FocusField.CONFIRM_PASSWORD)
                        }
                    )

                    TextInput(
                        label = "Mã giới thiệu từ bạn bè",
                        placeholder = "Nhập mã giới thiệu",
                        inputType = InputType.Text,
                        value = viewModel.referralCode,
                        onValueChange = viewModel::updateReferralCode,
                        onFocusChange = { focused ->
                            if (focused) viewModel.updateFocusField(FocusField.REFERRAL_CODE)
                        }
                    )
                }

                Spacer(modifier = Modifier.padding(2.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val termsOfUse = stringResource(id = R.string.term_of_use)
                    val policy = stringResource(id = R.string.policy)

                    val texts = listOf(
                        AnnotatedText.Plain("Bằng cách tiếp tục, bạn đã đọc và đồng ý với "),
                        AnnotatedText.Clickable(termsOfUse, onClick = { onClick(SignUpClicks.TermsOfUse, "") }),
                        AnnotatedText.Plain(" và "),
                        AnnotatedText.Clickable(policy, onClick = { onClick(SignUpClicks.PrivacyPolicy, "") }),
                        AnnotatedText.Plain(" của chúng tôi.")
                    )

                    Box (
                        modifier = Modifier.padding(start = 0.dp)
                    ) {
                        Checkbox(
                            checked = viewModel.isTermAccepted,
                            onCheckedChange = viewModel::updateTermAccepted,
                            colors = CheckboxDefaults.colors(
                                checkedColor = PrimaryColor,
                                uncheckedColor = Color.Gray
                            ),
                            modifier = Modifier.padding(0.dp)
                        )
                    }

                    ClickableTextComponent(texts = texts)
                }

                Spacer(modifier = Modifier.padding(4.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.sign_up),
                    onClick = {
                        isLoading.value = true
                        viewModel.signUp {
                            val res = retrofit.authAPI.sendOTP(
                                OTPRequest(
                                    username = viewModel.phoneNumber,
                                    "+84964704623",
                                    OTPPurpose.SIGNUP)
                            )
                            if (res.isSuccessful) {
                                isLoading.value = false
                                delay(100)
                                onClick(SignUpClicks.OTPVerify, viewModel.phoneNumber)
                            } else {
                                val e = res.errorBody()?.string()
                                val eBody = Gson().fromJson(e, OTPResponse::class.java)
                                isLoading.value = false
                                delay(50)
                                makeToast(context, eBody.message)
                            }
                        }
                    },
                    enabled = viewModel.validateAll(),
                    color = ButtonDefaults.buttonColors(
                        containerColor = if (viewModel.validateAll()) PrimaryColor else Color.LightGray,
                        contentColor = if (viewModel.validateAll()) Color.White else Color.Gray
                    ),
                )

                Spacer(modifier = Modifier.weight(1f))

                ClickableTextLoginComponent(
                    tryingToLogin = true,
                    onTextSelected = { onClick(SignUpClicks.SignIn, "") },
                )
            }
        }
    }
}

sealed class SignUpClicks {
    data object SignIn: SignUpClicks()
    data object SignUpSuccess: SignUpClicks()
    data object TermsOfUse: SignUpClicks()
    data object PrivacyPolicy: SignUpClicks()
    data object OTPVerify: SignUpClicks()
}