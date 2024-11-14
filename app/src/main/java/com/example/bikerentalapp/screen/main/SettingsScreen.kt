package com.example.bikerentalapp.screen.main

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SettingsSection() {
    Column {
        Text(" ")
        Text(" ")
        Text(" ")
        Text(" ")
        Text("Cài đặt", fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 20.dp))
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
    { Text(title)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Arrow") }
}

@Composable
fun AboutUsSection() {
    Column {
        Text(" ")
        Text("Về chúng tôi",  fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 24.dp))
        SettingsItem("Bảng giá")
        SettingsItem("Vé Bike Rental App")
        SettingsItem("Hướng dẫn sử dụng")
        SettingsItem("Điều khoản chính sách")
        SettingsItem("Quy định chính sách")
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Arrow")
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

@Composable
fun SettingsScreen() {
    Scaffold(
        topBar = { TopAppBar( title = {
            Icon(Icons.Filled.Edit, contentDescription = " Edit Background")
            Column {
                Text(" Nguyễn Văn A ", fontWeight = FontWeight.Bold)
                Text(" 0123456789 ")

            }
            Icon(Icons.Default.Edit, contentDescription = " Edit Profile ")},
            backgroundColor = Color(0xFF00897B),
            contentColor = Color.White ) },
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
