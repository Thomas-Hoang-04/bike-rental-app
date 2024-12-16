package com.example.bikerentalapp.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikerentalapp.components.LocalNavigation
import com.example.bikerentalapp.components.UserAccount
import com.example.bikerentalapp.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val navController = LocalNavigation.current
    val account = UserAccount.current
    val username = account.username.collectAsState()
    val details = account.details.collectAsState()
    val phoneNumber = derivedStateOf {
        username.value.substring(0, 3) + "****" + username.value.substring(7, 10)
    }
    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    Text(
                        text = "${details.value?.name}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Tra cứu"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 10.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Hình nền",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.Gray.copy(alpha = 0.1f))
                        )
                        Text("${details.value?.name}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(phoneNumber.value, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("150", fontWeight = FontWeight.Bold)
                                Text("Người theo dõi", fontWeight = FontWeight.Bold, fontSize = 12.sp)

                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("200", fontWeight = FontWeight.Bold)
                                Text("Đang theo dõi ", fontWeight = FontWeight.Bold, fontSize = 12.sp)

                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(16.dp)
                        .clip(MaterialTheme.shapes.medium)
                ) {
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StatCard("3", "km")
                        HorizontalDivider(
                            modifier = Modifier
                                .height(25.dp)
                                .width(1.dp)
                                .background(Color.White)
                        )
                        StatCard("0,4", "giờ")
                        HorizontalDivider(
                            modifier = Modifier
                                .height(25.dp)
                                .width(1.dp)
                                .background(Color.White)
                        )
                        StatCard("1", "chặng")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(color = 0xFFE0E0E0),
                            contentColor = Color.Black
                        )
                    ) {
                        Text("Chỉnh sửa", fontWeight = FontWeight.Bold)
                    }
                    Button(onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(color = 0xFFE0E0E0),
                            contentColor = Color.Black
                        )
                    )  {
                        Text("Chia sẻ trang cá nhân", fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                TabRow(
                    selectedTabIndex = 0,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color.LightGray),
                    containerColor = Color.White,
                    contentColor = Color.LightGray,
                ) {
                    Tab(
                        selected = true,
                        onClick = { /*TODO*/ },
                        selectedContentColor = PrimaryColor,
                        unselectedContentColor = Color.Gray
                    ) {
                        Text(
                            "Thư viện ảnh",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Tab(
                        selected = false,
                        onClick = { /*TODO*/ },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.LightGray
                    ) {
                        Text(
                            "Hoạt động",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(value: String, unit: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .background(PrimaryColor)
            .padding(horizontal = 25.dp, vertical = 16.dp)
    ) {
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White
        )
        Text(
            text = unit,
            color = Color.White
        )
    }
}


@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
