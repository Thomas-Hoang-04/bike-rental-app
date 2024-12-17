package com.example.bikerentalapp.screen.features

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikerentalapp.api.data.QueryResponse
import com.example.bikerentalapp.api.data.TransactionStatus
import com.example.bikerentalapp.api.data.TransactionsDetails
import com.example.bikerentalapp.api.network.RetrofitInstances
import com.example.bikerentalapp.components.LoadingScreen
import com.example.bikerentalapp.components.LocalNavigation
import com.example.bikerentalapp.components.UserAccount
import com.example.bikerentalapp.components.makeToast
import com.example.bikerentalapp.ui.theme.ListColor
import com.example.bikerentalapp.ui.theme.PrimaryColor
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen() {
    val context = LocalContext.current
    val navController = LocalNavigation.current
    var transactionsList by remember { mutableStateOf<List<TransactionsDetails>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val account = UserAccount.current
    val token = account.token.collectAsState()
    val username = account.username.collectAsState()
    val scope = rememberCoroutineScope()
    val retrofit = RetrofitInstances.Query(token.value).queryAPI

    val fetchTransaction = suspend {
        val req = retrofit.getTransactions(username.value)
        if (req.isSuccessful) {
            val body = req.body() as QueryResponse
            transactionsList = body.data
        } else {
            makeToast(context, "Lỗi khi tải dữ liệu. Vui lòng thử lại")
        }
        isLoading = false
    }

    LaunchedEffect(true) {
        fetchTransaction()
    }

    if (isLoading) {
        LoadingScreen()
    }

    Scaffold (
        containerColor = ListColor,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lịch sử giao dịch",
                        fontWeight = FontWeight.Bold,
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
                        onClick = {
                            scope.launch {
                                isLoading = true
                                fetchTransaction()
                            }
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Tải lại"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { pad ->
        LazyColumn(
            contentPadding = PaddingValues(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxSize().padding(pad).padding(horizontal = 20.dp)
        ) {
            items(transactionsList) { transaction ->
                TransactionItem(
                    description = transaction.descriptions,
                    status = transaction.status,
                    date = OffsetDateTime.parse(transaction.createdAt),
                    amount = transaction.amount,
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionItem(
    description: String,
    status: TransactionStatus,
    date: OffsetDateTime,
    amount: Int,
) {
    val statusColor = when (status) {
        TransactionStatus.SUCCESS -> Color(0xFF4CAF50)
        TransactionStatus.FAILED -> Color(0xFFF44336)
        else -> Color.Gray
    }

    val statusString = when (status) {
        TransactionStatus.SUCCESS -> "Thành công"
        TransactionStatus.FAILED -> "Thất bại"
        else -> "Đang xử lý"
    }

    val timestamp = {
        val hour = "%02d".format(date.toLocalTime().hour + 7)
        val minute = "%02d".format(date.toLocalTime().minute)
        val datestamp = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        "$hour:$minute, $datestamp"
    }

    val isPositiveAmount = amount > 0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.CreditCard,
                contentDescription = if (isPositiveAmount) "Thẻ tín dụng" else "Tiền",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 12.dp),
                tint = if (isPositiveAmount) PrimaryColor else Color.Red
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = description,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = statusString,
                    fontSize = 12.sp,
                    color = statusColor,
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = (if (isPositiveAmount) "+" else "") + NumberFormat.getInstance().format(amount),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isPositiveAmount) PrimaryColor else Color.Red,
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = timestamp(),
                    fontSize = 12.sp,
                    color = Color.Gray,
                )
            }
        }
    }
}


