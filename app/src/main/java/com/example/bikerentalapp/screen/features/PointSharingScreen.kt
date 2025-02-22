package com.example.bikerentalapp.screen.features

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikerentalapp.api.data.CRUDResponse
import com.example.bikerentalapp.api.data.ErrorResponse
import com.example.bikerentalapp.api.data.TopUpRequest
import com.example.bikerentalapp.api.data.TransactionStatus
import com.example.bikerentalapp.api.network.RetrofitInstances
import com.example.bikerentalapp.components.CustomDialog
import com.example.bikerentalapp.components.LoadingScreen
import com.example.bikerentalapp.components.LocalNavigation
import com.example.bikerentalapp.components.MoneyDepositCard
import com.example.bikerentalapp.components.UserAccount
import com.example.bikerentalapp.components.makeToast
import com.example.bikerentalapp.ui.theme.DialogColor
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointSharingScreen() {
    val selectedAmount = remember { mutableIntStateOf(10000) }
    val screenAmount by remember {
        derivedStateOf {
            NumberFormat.getInstance().format(selectedAmount.intValue)
        }
    }
    var phoneNumber by remember { mutableStateOf("") }
    var phoneNumberError by remember { mutableStateOf<String?>(null) }

    var infoDialogTrigger by remember { mutableStateOf(false) }
    var confirmDialogTrigger by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val navController = LocalNavigation.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val account = UserAccount.current
    val token = account.token.collectAsState()
    val username = account.username.collectAsState()
    val details = account.details.collectAsState()
    val balance = remember {
        derivedStateOf {
            details.value?.balance ?: 0
        }
    }
    val retrofit = RetrofitInstances.Query(token.value)
    val scope = rememberCoroutineScope()

    val isPhoneNumberValid by remember {
        derivedStateOf {
            val error = when {
                phoneNumber.isEmpty() -> "Số điện thoại không được để trống"
                !phoneNumber.matches(Regex("^0[1-9][0-9]{8}$")) -> "Số điện thoại không hợp lệ"
                phoneNumber == username.value -> "Không thể chia sẻ điểm cho chính mình"
                else -> null
            }
            phoneNumberError = error
            error == null
        }
    }

    if (isLoading) {
        LoadingScreen()
    }

    if (infoDialogTrigger) {
        CustomDialog(
            title = "Xác nhận chia sẻ điểm",
            message = "Bạn có chắc chắn muốn chia sẻ $screenAmount điểm cho tài khoản $phoneNumber?",
            onDismiss = { infoDialogTrigger = false },
            onAccept = {
                scope.launch {
                    infoDialogTrigger = false
                    isLoading = true
                    val req = retrofit.queryAPI.pointSharing(
                        TopUpRequest(
                            from = username.value,
                            to = phoneNumber,
                            amount = selectedAmount.intValue,
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
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                ),
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

            MoneyDepositCard(
                screenAmount = screenAmount,
                selectedAmount = selectedAmount,
                focusManager = focusManager,
                title = "Số điểm muốn chia sẻ",
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Chọn nguồn muốn chia sẻ",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = DialogColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBalanceWallet,
                        contentDescription = "Tài khoản chính",
                        tint = PrimaryColor,
                        modifier = Modifier.size(32.dp).padding(4.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Tài khoản chính: ${NumberFormat.getInstance().format(balance.value)} điểm",
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
                    if (isPhoneNumberValid) {
                        if (selectedAmount.intValue > balance.value) makeToast(context, "Số điểm không đủ để chia sẻ")
                        else infoDialogTrigger = true
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