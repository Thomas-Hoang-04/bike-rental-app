package com.example.bikerentalapp.screen.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointSharingScreen() {
    var selectedAmount by remember { mutableIntStateOf(10000) }
    val phoneNumber = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Chia sẻ điểm",
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { /* Xử lý sự kiện quay lại */ },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { paddingVal ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingVal)
                .padding(horizontal = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

            Text(
                text = "Chia sẻ điểm",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            IconButton(
                onClick = { },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Xem lịch sử chia sẻ điểm"
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF00C853)
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Số điểm muốn chia sẻ",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )
        Card(
            backgroundColor = Color(0xFF00C853),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(20.dp)
            ) {
                androidx.compose.material3.Text(
                    text = "Số điểm muốn chia sẻ (VND)",
                    style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
                    color = Color.White
                )

                    TextField(
                        value = NumberFormat.getInstance().format(selectedAmount),
                        onValueChange = { amount ->
                            selectedAmount = if (amount.isEmpty()) 0 else amount.replace(",", "").toInt()
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = {
                            Text("0")
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                        ),
                        textStyle = LocalTextStyle.current.merge(
                            color = Color.White,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf(50000, 100000, 200000, 500000).forEach { amount ->
                            Button(
                                onClick = { selectedAmount = amount },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedAmount == amount) Color.White else Color.Gray,
                                    contentColor = if (selectedAmount == amount) Color.Gray else Color.White
                                ),
                                contentPadding = PaddingValues(
                                    horizontal = 16.dp
                                )
                            ) {
                                Text(text = NumberFormat.getInstance().format(amount))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Chọn nguồn muốn chia sẻ",
            style = MaterialTheme.typography.labelMedium,
        )
            Spacer(modifier = Modifier.height(4.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE0F7FA)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "A",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black,
                    modifier = Modifier
                        .background(Color(0xFF00C853), shape = RoundedCornerShape(6.dp))
                        .padding(4.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Tài khoản chính: 50.000",
                    style = MaterialTheme.typography.labelMedium, color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            label = { Text("Số điện thoại người nhận") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* Handle top-up action */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp)
            ) {
                Text(text = "Chia sẻ điểm")
            }
        }
    }
}

@Preview
@Composable
fun PointSharingScreenPreview() {
    PointSharingScreen()
}