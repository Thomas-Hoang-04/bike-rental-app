package com.example.bikerentalapp.screen.features

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Transaction(
    val description: String,
    val status: String,
    val date: String,
    val amount: String,
    val amountColor: Color,
)

@Composable
fun TransactionHistoryScreen(transactions: List<Transaction>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { /* Handle Back Action */ },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Quay lại"
                )
            }

            Text(
                text = "Lịch sử giao dịch",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            IconButton(
                onClick = { /* Handle Reload Action */ },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Tải lại"
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(transactions.size) { index ->
                val transaction = transactions[index]
                TransactionItem(
                    description = transaction.description,
                    status = transaction.status,
                    date = transaction.date,
                    amount = transaction.amount,
                    amountColor = transaction.amountColor
                )
            }
        }
    }
}

@Composable
fun TransactionItem(
    description: String,
    status: String,
    date: String,
    amount: String,
    amountColor: Color
) {
    val statusColor = when (status) {
        "Thành công" -> Color(0xFF4CAF50)
        "Hủy giao dịch" -> Color(0xFFF44336)
        else -> Color.Gray
    }

    val isPositiveAmount = amount.replace(",", "").replace(".", "").toIntOrNull() ?: 0 > 0
   val icon = if (isPositiveAmount) {
        Icons.Default.CreditCard
    } else {
        Icons.Default.Money
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = if (isPositiveAmount) "Thẻ tín dụng" else "Tiền",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp),
                tint = if (isPositiveAmount) Color(0xFF4CAF50) else Color(0xFFF44336)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = description,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.body1.copy(lineHeight = 28.sp)
                )
                Text(
                    text = status,
                    fontSize = 14.sp,
                    color = statusColor,
                    style = MaterialTheme.typography.body2.copy(lineHeight = 20.sp)
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = amount,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = amountColor,
                    style = MaterialTheme.typography.body1.copy(lineHeight = 28.sp)
                )
                Text(
                    text = date,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    style = MaterialTheme.typography.body2.copy(lineHeight = 22.sp)
                )
            }
        }
    }
}

@Preview
@Composable
fun TransactionHistoryScreenPreview() {
    val transactions = listOf(
        Transaction(
            description = "Thanh toán chuyến đi",
            status = "Thành công",
            date = "08:51, 10/10/2024",
            amount = "-10,000",
            amountColor = Color.Red
        ),
        Transaction(
            description = "Thanh toán trực tuyến",
            status = "Thành công",
            date = "08:52, 10/10/2024",
            amount = "+60,000",
            amountColor = Color.Blue
        ),
        Transaction(
            description = "Thanh toán trực tuyến",
            status = "Hủy giao dịch",
            date = "08:52, 10/10/2024",
            amount = "+60,000",
            amountColor = Color.Blue
        )
    )

    TransactionHistoryScreen(transactions = transactions)
}

