package com.example.bikerentalapp.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Face2
import androidx.compose.material.icons.filled.Search
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
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Quay lại"
                )
            }

            Text(
                text = "Nguyễn Văn A",
                style = MaterialTheme.typography.h6.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )

            IconButton(
                onClick = { },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Tra cứu"
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Icon(
                        imageVector = Icons.Default.Face2,
                        contentDescription = "Hình nền",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(alpha = 0.1f))
                    )
                    Text("Nguyễn Văn A", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("012-****-789", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Row {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("150", fontWeight = FontWeight.Bold)
                            Text("Người theo dõi", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("200", fontWeight = FontWeight.Bold)
                            Text("Đang theo dõi ", fontWeight = FontWeight.Bold, fontSize = 18.sp)

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
                    Divider(
                        modifier = Modifier
                            .height(25.dp)
                            .width(1.dp)
                            .background(Color.White)
                    )
                    StatCard("0,4", "giờ")
                    Divider(
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
                        backgroundColor = Color(color = 0xFFE0E0E0),
                        contentColor = Color.Black
                    )
                ) {
                    Text("Chỉnh sửa", fontWeight = FontWeight.Bold)
                }
                Button(onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(color = 0xFFE0E0E0),
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
                backgroundColor = Color.White,
                contentColor = Color.LightGray
            ) {
                Tab(
                    selected = true,
                    onClick = { /*TODO*/ },
                    selectedContentColor = Color(0xFF4ECBB5),
                    unselectedContentColor = Color.LightGray
                ) {
                    Text(
                        "Thư viện ảnh",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
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
            .background(Color(0xFF4ECBB5))
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
