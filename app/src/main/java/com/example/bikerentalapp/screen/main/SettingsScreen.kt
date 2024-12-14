package com.example.bikerentalapp.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikerentalapp.components.LocalDataStore
import com.example.bikerentalapp.components.LocalNavigation
import com.example.bikerentalapp.components.UserAccount
import com.example.bikerentalapp.navigation.Screens
import kotlinx.coroutines.launch

@Composable
fun SettingsSection() {
    val navController = LocalNavigation.current
    val scope = rememberCoroutineScope()
    val datastore = LocalDataStore.current

    Column {
        Text("Cài đặt", fontSize = 20.sp , fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 24.dp, bottom = 12.dp))
        SettingsItem("Thông tin tài khoản")
        SettingsItem("Đổi mật khẩu")
        SettingsItem("Vô hiệu hóa tài khoản")
        SettingsItem("Xóa tài khoản")
        SettingsItem("Đăng xuất") {
            scope.launch {
                datastore.updateData { settings ->
                    settings.copy(
                        username = null,
                        password = null,
                    )
                }
            }
            navController.navigate(Screens.Auth) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}

@Composable
fun SettingsItem(
    title: String,
    onClick: () -> Unit = {}
) {
    Row( modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically )
    {
        Text(title)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Arrow",
            modifier = Modifier.clickable { onClick() }
        )
    }

    HorizontalDivider(
        color = Color.Gray,
        thickness = 1.dp,
        modifier = Modifier.padding(top = 2.dp)
    )
}

@Composable
fun AboutUsSection() {
    Column {
        Text("Về chúng tôi", fontSize = 20.sp , fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 24.dp, bottom = 12.dp))
        SettingsItem("Bảng giá")
        SettingsItem("Vé Bike Rental App")
        SettingsItem("Hướng dẫn sử dụng")
        SettingsItem("Điều khoản chính sách")
        SettingsItem("Quy định chính sách")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val account = UserAccount.current
    val name = account.details.collectAsState()
    val phoneNumber = account.username.collectAsState()
    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxSize(),
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
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .padding(8.dp),
                                tint = Color.Gray
                            )
                            Column(
                                modifier = Modifier.padding(start = 16.dp)
                            ) {
                                Text(
                                    text = "${name.value?.name}",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    lineHeight = 8.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = phoneNumber.value,
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    lineHeight = 8.sp
                                )
                            }
                        }

                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit Profile",
                            modifier = Modifier
                                .clickable {  }
                                .padding(end = 16.dp)
                                .size(24.dp),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00897B),
                    titleContentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth().height(105.dp)
            )
        },
    ) { pad ->
        Column(
            modifier = Modifier.padding(
            pad.calculateStartPadding(LayoutDirection.Ltr) + 24.dp,
            pad.calculateTopPadding(),
            pad.calculateEndPadding(LayoutDirection.Ltr) + 24.dp,
            pad.calculateBottomPadding()
        )) {
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