package com.example.bikerentalapp.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikerentalapp.R
import com.example.bikerentalapp.components.*
import com.example.bikerentalapp.navigation.PostOfficeAppRouter
import com.example.bikerentalapp.navigation.Screen
import com.example.bikerentalapp.ui.theme.PrimaryColor

@Composable
fun SignInScreen(onClick: () -> Unit) {
    Surface (
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_bg),
                contentDescription = "Login Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp),
                contentScale = ContentScale.Crop
            )

            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                HeadingTextComponent(value = stringResource(id = R.string.sign_in))

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
                    )

                    TextInput(
                        label = "Mật khẩu",
                        placeholder = "Nhập mật khẩu",
                        inputType = InputType.Password,
                    )

                    ButtonComponent(value = stringResource(id = R.string.sign_in))

                    Text(
                        text = stringResource(id = R.string.forgot_password),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium
                        ),
                        color = PrimaryColor
                    )
                }

                Spacer(modifier = Modifier.padding(32.dp))

                ClickableTextLoginComponent(
                    tryingToLogin = false,
                    onTextSelected = {
                        onClick()
                    },
                )
            }
        }


    }
}

@Preview
@Composable
fun SignInPreview() {
    SignInScreen({})
}