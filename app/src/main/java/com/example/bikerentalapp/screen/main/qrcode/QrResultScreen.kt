package com.example.bikerentalapp.screen.main.qrcode

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bikerentalapp.R
import com.example.bikerentalapp.components.ButtonComponent
import com.example.bikerentalapp.navigation.Screens
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.example.bikerentalapp.ui.theme.disablePrimaryColor

@Composable
fun QrCodeResultScreen(qrCodeContent: String,navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 5.dp),
        ){
            Image(
                painter = painterResource(id = R.drawable.track_bicycle),
                contentDescription = "QR Code",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
            )
            IconButton(onClick = { navController.popBackStack()}) {
                Icon(
                    Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(35.dp)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ){
            TextColumn("San sang:",qrCodeContent.substring(0, 10))
            Spacer(modifier = Modifier.width(16.dp))
            TextColumn("78%","Pin")
        }
        RowWithTextAndDropdown(
            title = "Hình thức trả phí:",
            dropdownItems = listOf("Vé lượt", "Vé tháng", "Vé năm")
        )
        HorizontalDivider(
            color = Color.Gray.copy(alpha = 0.2f),
            thickness = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
        )
        RowWithTextAndDropdown(
            title = "Mã khuyến mãi",
            dropdownItems = listOf("Khuyến mãi 1", "Khuyến mãi 2", "Khuyến mãi 3")
        )
        Text(
            "Bảng giá áp dụng:\n",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 10.dp),
        )
        Text("Về lượt, 10.000 điểm cho 60 phút đầu tiên, sau 60 phút đầu sẽ tỉnh cước phí 3.000 điểm cho môi 15 phút tiếp theo.\n",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray.copy(alpha = 0.6f),
            modifier = Modifier.padding(start = 10.dp)
        )
        HorizontalDivider(
            color = Color.Gray.copy(alpha = 0.2f),
            thickness = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        ButtonComponent(
            value = "Bat dau",
            onClick = {
                navController.navigate("${Screens.Main.TrackingMap}/${qrCodeContent.substring(0, 10)}"){
                    popUpTo(navController.graph.startDestinationId){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            color = ButtonColors(
                contentColor = Color.White,
                containerColor = PrimaryColor,
                disabledContentColor = Color.Gray,
                disabledContainerColor = disablePrimaryColor
            ),
            modifier = Modifier.padding(horizontal = 40.dp)
        )
    }
}

@Composable
fun TextColumn(title:String,subTitle : String){
    Column(
        modifier = Modifier
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = PrimaryColor,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Text(
            text = subTitle,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun RowWithTextAndDropdown(title: String, dropdownItems: List<String>) {
    val expanded = remember { mutableStateOf(false) }
    val text = remember { mutableStateOf(dropdownItems[0]) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.width(30.dp))
        Box {
            TextButton(
                onClick = { expanded.value = true },
            ) {
                Text(text = text.value, style = MaterialTheme.typography.bodyMedium, color = PrimaryColor)
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown", tint = PrimaryColor)
            }

            // Dropdown Menu
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                dropdownItems.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item)},
                        onClick = {
                            text.value = item
                            expanded.value = false
                        }
                    )
                }
            }
        }
    }
}
