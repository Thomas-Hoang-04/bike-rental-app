package com.example.bikerentalapp.screen.features

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikerentalapp.ui.theme.PrimaryColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettingScreen() {
    var name by remember { mutableStateOf("Nguyễn Văn A") }
    var username by remember { mutableStateOf("Nguyễn Văn A") }
    var bio by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Nam") }
    var birthDate by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Chỉnh sửa",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {  },
                        modifier = Modifier
                            .size(40.dp)
                            .padding(start = 0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        bottomBar = {
            BottomAppBar(
                content = {
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryColor,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Lưu thay đổi",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                containerColor = Color.White
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 80.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .padding(8.dp),
                    tint = Color.White
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Chỉnh sửa avatar",
                    color = PrimaryColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.clickable { }
                )
            }

            InputRowWithDivider(label = "Tên", value = name, onValueChange = { name = it })
            InputRowWithDivider(label = "Tên người dùng", value = username, onValueChange = { username = it })
            InputRowWithDivider(label = "Ngày sinh", value = birthDate, onValueChange = { birthDate = it })
            InputRowWithDivider(label = "Tiểu sử", value = bio, onValueChange = { bio = it })
            InputRowWithDivider(label = "Email", value = email, onValueChange = { email = it })
            InputRowWithDivider(label = "Giới tính", value = gender, onValueChange = { gender = it })
        }
    }
}

@Composable
fun InputRowWithDivider(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                fontSize = 20.sp
            )
            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .clickable { }
            ) {
                Text(
                    text = if (value.isEmpty()) "Thêm ${label.replaceFirstChar { it.lowercaseChar() }}" else value,
                    fontSize = 18.sp,
                    color = if (value.isEmpty()) Color.Gray else Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                )
            }
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}

@Preview
@Composable
fun ProfileSettingScreenPreview() {
    ProfileSettingScreen()
}
