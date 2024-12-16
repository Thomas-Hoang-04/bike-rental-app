package com.example.bikerentalapp.screen.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikerentalapp.api.data.CRUDResponse
import com.example.bikerentalapp.api.data.ErrorResponse
import com.example.bikerentalapp.api.data.TopUpRequest
import com.example.bikerentalapp.api.data.TransactionStatus
import com.example.bikerentalapp.api.network.RetrofitInstances
import com.example.bikerentalapp.components.*
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointSharingScreen() {
    var selectedAmount by remember { mutableIntStateOf(10000) }
    var phoneNumber by remember { mutableStateOf("") }
    var phoneNumberError by remember { mutableStateOf<String?>(null) }
    val isPhoneNumberValid = {
        val error = when {
            phoneNumber.isEmpty() -> "Số điện thoại không được để trống"
            !phoneNumber.matches(Regex("^0[1-9][0-9]{8}\$")) -> "Số điện thoại không hợp lệ"
            else -> null
        }
        phoneNumberError = error
        error == null
    }

    var infoDialogTrigger by remember { mutableStateOf(false) }
    var confirmDialogTrigger by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val navController = LocalNavigation.current
    val focusManager = LocalFocusManager.current

    val account = UserAccount.current
    val token = account.token.collectAsState()
    val username = account.username.collectAsState()
    val balance = account.details.collectAsState().value?.balance ?: 0
    val retrofit = RetrofitInstances.Query(token.value)
    val scope = rememberCoroutineScope()

    if (isLoading) {
        LoadingScreen()
    }

    if (infoDialogTrigger) {
        CustomDialog(
            title = "Xác nhận chia sẻ điểm",
            message = "Bạn có chắc chắn muốn chia sẻ ${NumberFormat.getInstance().format(selectedAmount)} điểm cho tài khoản $phoneNumber?",
            onDismiss = { infoDialogTrigger = false },
            onAccept = {
                scope.launch {
                    infoDialogTrigger = false
                    isLoading = true
                    val req = retrofit.queryAPI.pointSharing(
                        TopUpRequest(
                            from = username.value,
                            to = phoneNumber,
                            amount = selectedAmount,
                        )
                    )
                    if (req.isSuccessful) {
                        val body = req.body() as CRUDResponse<*>
                        val status = body.target as TransactionStatus
                        if (status == TransactionStatus.SUCCESS) {
                            delay(2000)
                            isLoading = false
                            confirmDialogTrigger = true
                        } else {
                            isLoading = false
                            phoneNumberError = "Chia sẻ điểm thất bại"
                        }
                    } else {
                        val e = req.errorBody()?.string()
                        val errBody = Gson().fromJson(e, ErrorResponse::class.java)
                        isLoading = false
                        phoneNumberError = errBody.message
                    }
                }
            }
        )
    }

    if (confirmDialogTrigger) {
        CustomDialog(
            title = "Chia sẻ điểm thành công",
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
                title = { Text(
                    "Chia sẻ điểm",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp) },
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
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp)
        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//
//            Text(
//                text = "Chia sẻ điểm",
//                style = MaterialTheme.typography.h6,
//                modifier = Modifier.align(Alignment.CenterVertically)
//            )
//
//            IconButton(
//                onClick = { },
//                modifier = Modifier.size(40.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                    contentDescription = "Xem lịch sử chia sẻ điểm"
//                )
//            }

            Card(
                colors = CardDefaults.cardColors(containerColor = PrimaryColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Số điểm muốn chia sẻ (VND)",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
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

                    Spacer(modifier = Modifier.height(16.dp))

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
                text = "Chọn nguồn muốn chia sẻ",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "M",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White,
                        modifier = Modifier
                            .background(PrimaryColor, shape = RoundedCornerShape(6.dp))
                            .padding(4.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Tài khoản chính: ${NumberFormat.getInstance().format(balance)} điểm",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                    isPhoneNumberValid()
                },
                label = { Text("Số điện thoại người nhận") },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryColor,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = PrimaryColor,
                    focusedLabelColor = PrimaryColor,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        isPhoneNumberValid()
                        focusManager.clearFocus()
                    }
                ),
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = phoneNumberError ?: "",
                color = Color.Red,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (isPhoneNumberValid()) {
                        infoDialogTrigger = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
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
