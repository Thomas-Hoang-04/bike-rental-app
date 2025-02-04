package com.example.bikerentalapp.screen.features

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
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
    val selectedAmount = remember { mutableIntStateOf(10000) }
    val screenAmount by remember {
        derivedStateOf {
            NumberFormat.getInstance().format(selectedAmount.intValue)
        }
    }
    val expanded = remember { mutableStateOf(false) }
    val paymentMethods = listOf("ATM, Visa/Master/JCB", "Thanh toán QRCode, ShopeePay, Tại cửa hàng", "Ví Momo")
    var selectedPaymentMethod by remember { mutableStateOf(paymentMethods[0]) }
    val paymentMethodIcon by remember {
        derivedStateOf {
            when (selectedPaymentMethod) {
                paymentMethods[0] -> Icons.Default.CreditCard
                paymentMethods[1] -> Icons.Default.QrCode
                paymentMethods[2] -> Icons.Default.Wallet
                else -> Icons.Default.Money
            }
        }
    }
    var isLoading by remember { mutableStateOf(false) }
    val navController = LocalNavigation.current
    var infoDialogTrigger by remember { mutableStateOf(false) }
    var confirmDialogTrigger by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var card by remember { mutableStateOf("") }
    val cardDisplay by remember {
        derivedStateOf {
            card.chunked(4).joinToString(" ")
        }
    }
    var securityCode by remember { mutableStateOf("") }
    var cardHolder by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    val expiryDateDisplay by remember {
        derivedStateOf {
            expiryDate.chunked(2).joinToString("/")
        }
    }

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
            message = "Bạn có chắc chắn muốn nạp $screenAmount điểm vào tài khoản?",
            onDismiss = { infoDialogTrigger = false },
            onAccept = {
                scope.launch {
                    infoDialogTrigger = false
                    isLoading = true
                    val req = retrofit.queryAPI.topUp(
                        TopUpRequest(
                            from = username.value,
                            amount = selectedAmount.intValue,
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
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                ),
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

            MoneyDepositCard(
                screenAmount = screenAmount,
                selectedAmount = selectedAmount,
                focusManager = focusManager,
                title = "Số điểm muốn nạp (VND)",
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Phương thức thanh toán",
                fontSize = 22.sp,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                    ) {
                        expanded.value = true
                    }
            ) {
                Icon(
                    imageVector = paymentMethodIcon,
                    contentDescription = "Phương thức thanh toán",
                    tint = PrimaryColor,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    selectedPaymentMethod,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryColor,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded.value) Icons.Default.ArrowDropUp
                    else Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = PrimaryColor
                )

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    containerColor = Color.White,
                    modifier = Modifier.fillMaxWidth(0.85f),
                    offset = DpOffset(0.dp, 10.dp)
                ) {
                    paymentMethods.forEach { item ->
                        DropdownMenuItem(
                            leadingIcon = {
                                val icon = when (item) {
                                    paymentMethods[0] -> Icons.Default.CreditCard
                                    paymentMethods[1] -> Icons.Default.QrCode
                                    paymentMethods[2] -> Icons.Default.Wallet
                                    else -> Icons.Default.Money
                                }
                                Icon(
                                    imageVector = icon,
                                    contentDescription = "Phương thức thanh toán",
                                    tint = PrimaryColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            text = { Text(text = item) },
                            onClick = {
                                scope.launch {
                                    expanded.value = false
                                    delay(50)
                                    selectedPaymentMethod = item
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (selectedPaymentMethod == paymentMethods[0]) {
                OutlinedTextField(
                    value = TextFieldValue(
                        text = cardDisplay,
                        selection = TextRange(cardDisplay.length)
                    ),
                    onValueChange = {
                        if (it.text.length < 20)
                            card = it.text.filter { c -> c.isDigit() }
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
                        .padding(vertical = 6.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.CreditCard,
                            contentDescription = "Số thẻ",
                            tint = PrimaryColor
                        )
                    }
                )
                Spacer(modifier = Modifier.height(3.dp))
                OutlinedTextField(
                    value = cardHolder,
                    onValueChange = {
                        cardHolder = it.uppercase()
                    },
                    label = { Text("Tên chủ thẻ") },
                    placeholder = { Text("Nhập tên chủ thẻ") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
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
                        .padding(vertical = 6.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Chủ thẻ",
                            tint = PrimaryColor
                        )
                    },
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(3.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
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
                            imeAction = ImeAction.Next
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
                            .weight(0.5f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    OutlinedTextField(
                        value = TextFieldValue(
                            text = expiryDateDisplay,
                            selection = TextRange(expiryDateDisplay.length)
                        ),
                        onValueChange = {
                            if (it.text.length <= 5) {
                                expiryDate = it.text.filter { c -> c.isDigit() }
                            }
                        },
                        label = { Text("Ngày hết hạn") },
                        placeholder = { Text("Ngày hết hạn") },
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
                            .weight(0.5f)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (selectedPaymentMethod == paymentMethods[0]) {
                        if (card.length != 16 || securityCode.length != 3 || cardHolder.isEmpty() || expiryDate.length != 4) {
                            makeToast(context, "Vui lòng nhập đầy đủ thông tin thẻ")
                        } else {
                            infoDialogTrigger = true
                        }
                    } else {
                        infoDialogTrigger = true
                    }
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