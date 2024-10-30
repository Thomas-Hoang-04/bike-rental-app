package com.example.bikerentalapp.screen.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bikerentalapp.R
import com.example.bikerentalapp.components.*


@Composable
fun SignUpScreen(onClick: (SignUpClicks) -> Unit) {
    var isTermAccepted by remember { mutableStateOf(false) }
    val termOfUse = stringResource(id = R.string.term_of_use)
    val policy = stringResource(id = R.string.policy)
    Surface (
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.padding(top = 28.dp))

            HeadingTextComponent(value = stringResource(id = R.string.sign_up))

            Spacer(modifier = Modifier.padding(4.dp))

            HorizontalDivider (
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
                    required = true
                )

                TextInput(
                    label = "Tên của bạn",
                    placeholder = "Nhập tên của bạn",
                    inputType = InputType.Text,
                    required = true
                )

                TextInput(
                    label = "Email",
                    placeholder = "Nhập email của bạn",
                    inputType = InputType.Email
                )

                TextInput(
                    label = "Ngày sinh",
                    placeholder = "DD/MM/YYYY",
                    inputType = InputType.Text,
                    required = true
                )

                TextInput(
                    label = "Mật khẩu",
                    placeholder = "Nhập mật khẩu",
                    inputType = InputType.Password,
                    required = true
                )

                TextInput(
                    label = "Nhập lại mật khẩu",
                    placeholder = "Nhập lại mật khẩu",
                    inputType = InputType.Password,
                    required = true
                )

                TextInput(
                    label = "Mã giới thiệu từ bạn bè",
                    placeholder = "Nhập mã giới thiệu",
                    inputType = InputType.Text
                )
            }

            Spacer(modifier = Modifier.padding(2.dp))

            CheckboxComponent(
                value = stringResource(id = R.string.terms_and_condition),
                isChecked = isTermAccepted,
                onCheckedChange = { isTermAccepted = it },
                onTextSelected = { selectedText ->
                    when (selectedText) {
                        termOfUse -> { onClick(SignUpClicks.TermsOfUse)}
                        policy -> { onClick(SignUpClicks.PrivacyPolicy) }
                    }
                }
            )

            Spacer(modifier = Modifier.padding(4.dp))

            ButtonComponent(
                value = stringResource(id = R.string.sign_up),
                onClick = { onClick(SignUpClicks.SignUpSuccess) }
            )

            Spacer(modifier = Modifier.weight(1f))

            ClickableTextLoginComponent (
                tryingToLogin = true,
                onTextSelected = { onClick(SignUpClicks.SignIn) }
            )
        }
    }
}

sealed class SignUpClicks {
    data object SignIn: SignUpClicks()
    data object SignUpSuccess: SignUpClicks()
    data object TermsOfUse: SignUpClicks()
    data object PrivacyPolicy: SignUpClicks()
}

@Preview
@Composable
fun SignUpPreview() {
    SignUpScreen ({})
}