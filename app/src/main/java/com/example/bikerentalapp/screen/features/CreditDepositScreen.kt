package com.example.bikerentalapp.screen.features

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Money
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikerentalapp.api.data.TopUpRequest
import com.example.bikerentalapp.api.network.RetrofitInstances
import com.example.bikerentalapp.components.*
import com.example.bikerentalapp.ui.theme.PrimaryColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditDepositScreen() {
    var selectedAmount by remember { mutableIntStateOf(10000) }
    val paymentMethods = listOf("ATM, Visa/Master/JCB", "Thanh toán QRCode, ShopeePay, Tại cửa hàng", "Ví Momo")
    val selectedPaymentMethod = remember { mutableStateOf(paymentMethods[0]) }
    var isLoading by remember { mutableStateOf(false) }
    val navController = LocalNavigation.current
    var infoDialogTrigger by remember { mutableStateOf(false) }
    var confirmDialogTrigger by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var card by remember { mutableStateOf("") }
    var securityCode by remember { mutableStateOf("") }

    val account = UserAccount.current
    val token = account.token.collectAsState()
    val username = account.username.collectAsState()

    val retrofit = RetrofitInstances.Query(token.value)
    val scope = rememberCoroutineScope()

    if (isLoading) {
        LoadingScreen()
    }

    if (infoDialogTrigger) {
        CustomDialog(
            title = "Xác nhận nạp điểm",
            message = "Bạn có chắc chắn muốn nạp ${NumberFormat.getInstance().format(selectedAmount)} điểm vào tài khoản?",
            onDismiss = { infoDialogTrigger = false },
            onAccept = {
                scope.launch {
                    infoDialogTrigger = false
                    isLoading = true
                    val req = retrofit.queryAPI.topUp(
                        TopUpRequest(
                            from = username.value,
                            amount = selectedAmount,
                        )
                    )
                    if (req.isSuccessful) {
                        delay(2000)
                        isLoading = false
                        confirmDialogTrigger = true
                    } else {
                        isLoading = false
                        makeToast(context, "Nạp điểm thất bại. Vui lòng thử lại sau")
                    }
                }
            }
        )
    }

    if (confirmDialogTrigger) {
        CustomDialog(
            title = "Nạp điểm thành công",
            icon = Icons.Default.CheckCircle,
            onAccept = {
                confirmDialogTrigger = false
                scope.launch {
                    isLoading = true
                    account.update()
                    delay(500)
                    navController.popBackStack()
                }
            },
            canDismiss = false
        )
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Nạp điểm",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
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
                .padding(horizontal = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

            }

            Spacer(modifier = Modifier.height(16.dp))


            Card(
                colors = CardDefaults.cardColors(containerColor = PrimaryColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 28.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Số điểm muốn nạp (VND)",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    TextField(
                        value = NumberFormat.getInstance().format(selectedAmount),
                        onValueChange = { amount ->
                            selectedAmount =
                                if (amount.isEmpty()) 0 else amount.replace(",", "").toInt()
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        ),
                        placeholder = { Text("0") },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.White,
                        ),
                        textStyle = LocalTextStyle.current.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf(50000, 100000, 200000, 300000).forEach { amount ->
                            Spacer(modifier = Modifier.width(2.dp))
                            Button(
                                onClick = { selectedAmount = amount },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedAmount == amount) Color.White else Color.LightGray,
                                    contentColor = if (selectedAmount == amount) PrimaryColor else Color.Black
                                ),
                                contentPadding = PaddingValues(
                                    horizontal = 8.dp
                                ),
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(
                                    text = NumberFormat.getInstance().format(amount),
                                    fontSize = 12.sp,
                                )
                            }
                            Spacer(modifier = Modifier.width(2.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Phương thức thanh toán",
                fontSize = 22.sp,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            paymentMethods.forEach { method ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) { selectedPaymentMethod.value = method }
                        .padding(vertical = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Money,
                        contentDescription = "Phương thức thanh toán",
                        tint = if (selectedPaymentMethod.value == method) PrimaryColor else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = method,
                        fontSize = 18.sp,
                        color = if (selectedPaymentMethod.value == method) PrimaryColor else Color.Black
                    )
                }
                if (method == paymentMethods.first() && selectedPaymentMethod.value == method) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = card.chunked(4).joinToString(" "),
                            onValueChange = {
                                 if (it.length <= 16)
                                    card = it.filter { c -> c.isDigit() }
                            },
                            label = { Text("Số thẻ") },
                            placeholder = { Text("Nhập số thẻ") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Gray,
                                focusedBorderColor = PrimaryColor,
                                cursorColor = PrimaryColor,
                                focusedLabelColor = PrimaryColor,
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.Black,
                                fontSize = 16.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .height(50.dp)
                                .weight(0.75f),
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = securityCode,
                            onValueChange = {
                                if (it.length <= 3) {
                                    securityCode = it
                                }
                            },
                            label = { Text("CVV") },
                            placeholder = { Text("CVV") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Gray,
                                focusedBorderColor = PrimaryColor,
                                cursorColor = PrimaryColor,
                                focusedLabelColor = PrimaryColor,
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.Black,
                                fontSize = 16.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                                .weight(0.25f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    infoDialogTrigger = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp)
            ) {
                Text(
                    text = "Nạp điểm",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun CreditDepositScreenPreview() {
    CreditDepositScreen()
}