package com.example.bikerentalapp.screen.features

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
fun CreditDepositScreen() {
    var selectedAmount by remember { mutableIntStateOf(10000) }
    val paymentMethods = listOf("ATM, Visa/Master/JCB, Thanh toán QRCode, ShopeePay, Tại cửa hàng", "Ví Momo")
    val selectedPaymentMethod = remember { mutableStateOf(paymentMethods[0]) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Nạp điểm",
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
                        text = "Số điểm muốn nạp (VND)",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )

                    TextField(
                        value = NumberFormat.getInstance().format(selectedAmount),
                        onValueChange = {
                                amount -> selectedAmount = if (amount.isEmpty()) 0 else amount.replace(",", "").toInt()
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

            Text(
                text = "Phương thức thanh toán",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            paymentMethods.forEach { method ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedPaymentMethod.value = method }
                        .padding(vertical = 20.dp)
                ) {

                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = method,
                        color = if (selectedPaymentMethod.value == method) Color(0xFF00C853) else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* Handle top-up action */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp)
            ) {
                Text(text = "Nạp điểm")
            }
        }
    }
}


@Preview
@Composable
fun CreditDepositScreenPreview() {
    CreditDepositScreen()
}