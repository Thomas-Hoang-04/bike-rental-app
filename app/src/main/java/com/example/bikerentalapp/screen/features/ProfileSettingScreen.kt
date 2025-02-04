package com.example.bikerentalapp.screen.features

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikerentalapp.components.LocalNavigation
import com.example.bikerentalapp.components.UserAccount
import com.example.bikerentalapp.ui.theme.PrimaryColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettingScreen() {
    val navController = LocalNavigation.current
    val account = UserAccount.current
    val username = account.username.collectAsState()
    val details = account.details.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Chỉnh sửa",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .padding(start = 0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
    ) { _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 40.dp)
                .padding(top = 40.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                            .padding(8.dp),
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Chỉnh sửa avatar",
                        color = PrimaryColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable { }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InputRowWithDivider(label = "Tên", value = "${details.value?.name}")
                    InputRowWithDivider(label = "Số điện thoại", value = username.value)
                    InputRowWithDivider(label = "Ngày sinh", value = LocalDate.parse("${details.value?.dob}")
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    InputRowWithDivider(label = "Email", value = "${details.value?.email}")
                }
            }

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp)
                    .background(
                        color = PrimaryColor,
                        shape = RoundedCornerShape(6.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Lưu thay đổi",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }


    }
}

@Composable
fun InputRowWithDivider(
    label: String,
    value: String,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp
            )
            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .clickable { }
            ) {
                Text(
                    text = if (value.isEmpty()) "Thêm ${label.replaceFirstChar { it.lowercaseChar() }}" else value,
                    fontSize = 15.sp,
                    color = if (value.isEmpty()) Color.Gray else Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                )
            }
        }
        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
    }
}
