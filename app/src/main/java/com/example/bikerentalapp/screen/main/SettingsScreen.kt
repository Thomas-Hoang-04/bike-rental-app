package com.example.bikerentalapp.screen.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TabRowDefaults.Divider
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SettingsSection() {
    Column {
        Text(" ")
        Text(" ")
        Text(" ")
        Text(" ")
        Text("Cài đặt", fontSize = 20.sp , fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 20.dp))
        SettingsItem("Thông tin tài khoản")
        SettingsItem("Đổi mật khẩu")
        SettingsItem("Thông báo")
        SettingsItem("Vô hiệu hóa tài khoản")
        SettingsItem("Xóa tài khoản")
    }
}

@Composable
fun SettingsItem(title: String) {
    Row( modifier = Modifier
        .fillMaxWidth()
        .clickable { /* Handle click */ }
        .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically )
    {
        Text(title)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Arrow")
    }

    Divider(
        color = Color.Gray,
        thickness = 1.dp,
        modifier = Modifier.padding(top = 2.dp)
    )
}

@Composable
fun AboutUsSection() {
    Column {
        Text(" ")
        Text("Về chúng tôi", fontSize = 20.sp , fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 24.dp))
        SettingsItem("Bảng giá")
        SettingsItem("Vé Bike Rental App")
        SettingsItem("Hướng dẫn sử dụng")
        SettingsItem("Điều khoản chính sách")
        SettingsItem("Quy định chính sách")
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

@Composable
fun SettingsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Avatar",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.Gray)
                                    .padding(8.dp),
                                tint = Color.White
                            )
                            Column(
                                modifier = Modifier.padding(start = 16.dp)
                            ) {
                                Text(
                                    "Nguyễn Văn A",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    "0123456789",
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit Profile",
                            modifier = Modifier
                                .clickable {  }
                                .padding(8.dp)
                        )
                    }
                },
                backgroundColor = Color(0xFF00897B),
                contentColor = Color.White,
                modifier = Modifier.fillMaxWidth()
            )
        },
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            SettingsSection()
            AboutUsSection()
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}