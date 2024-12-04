package com.example.bikerentalapp.screen.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
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

@Composable
fun PointSharingScreen() {
    var selectedAmount by remember { mutableIntStateOf(10000) }
    val phoneNumber = remember { mutableStateOf("") }

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
                text = "Chia se điểm",
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
        }
        Spacer(modifier = Modifier.height(16.dp))

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
                    text = "Số điểm muốn nạp (VND)",
                    style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
                    color = Color.White
                )

                androidx.compose.material3.TextField(
                    value = NumberFormat.getInstance().format(selectedAmount),
                    onValueChange = { amount ->
                        selectedAmount =
                            if (amount.isEmpty()) 0 else amount.replace(",", "").toInt()
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = {
                        androidx.compose.material3.Text("0")
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                    ),
                    textStyle = androidx.compose.material3.LocalTextStyle.current.merge(
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
                        androidx.compose.material3.Button(
                            onClick = { selectedAmount = amount },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedAmount == amount) Color.White else Color.Gray,
                                contentColor = if (selectedAmount == amount) Color.Gray else Color.White
                            ),
                            contentPadding = PaddingValues(
                                horizontal = 16.dp
                            )
                        ) {
                            androidx.compose.material3.Text(
                                text = NumberFormat.getInstance().format(amount)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Chọn nguồn muốn chia sẻ",
            style = MaterialTheme.typography.subtitle1
        )
        Card(
            backgroundColor = Color(0xFFE0F7FA),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "A",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black,
                    modifier = Modifier
                        .background(Color(0xFF00C853))
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Tài khoản chính: 50.000",
                    style = MaterialTheme.typography.subtitle1, color = Color.Black
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
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp)
        ) {
            Text(text = "Chia sẻ điểm")
        }
    }
}

@Preview
@Composable
fun PointSharingScreenPreview() {
    PointSharingScreen()
}