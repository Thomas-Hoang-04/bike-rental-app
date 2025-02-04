package com.example.bikerentalapp.screen.main.qrcode

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bikerentalapp.components.ButtonComponent
import com.example.bikerentalapp.components.RatingBar
import com.example.bikerentalapp.components.TextColumn
import com.example.bikerentalapp.navigation.Screens
import com.example.bikerentalapp.ui.theme.PrimaryColor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ReturnBikeScreen(totalMinutes : Int,bikeId : String,navController: NavController,fee : Int,tripId : String) {
    var feedback by remember { mutableStateOf("") }
    var rating by remember { mutableFloatStateOf(0f) }
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("hh:mm, dd/MM/yyyy")
    val navigateToMainScreen : () -> Unit = {
        navController.navigate(Screens.Main.Home)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(0.2f)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryColor)
                .padding(top = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Trả xe thành công",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                modifier = Modifier.padding(start = 100.dp),
            )
            IconButton(
                onClick = navigateToMainScreen,
            ) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryColor)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            RatingBar(
                rating = rating,
                onRatingChanged = {
                    rating = it
                },
                starSize = 50.dp,
                enableDragging = true,
                enableTapping = true
            )
            Box(
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp, top = 20.dp, bottom = 10.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White)
                    .fillMaxWidth()
                    .height(80.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ){
                    IconWithTextVertical(Icons.Default.Wallet, "$fee VND")
                    IconWithTextVertical(Icons.Default.PedalBike, "1 xe")
                    IconWithTextVertical(Icons.Default.AccessTime, "$totalMinutes phút")
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
                .height(200.dp)
        ){
            Column(
                Modifier.fillMaxSize(),
            ){
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ){
                    Text(bikeId, style = MaterialTheme.typography.titleMedium, color = PrimaryColor, modifier = Modifier.padding(start = 16.dp))
                    Text(currentDateTime.format(formatter), style = MaterialTheme.typography.bodyMedium, color = Color.Black.copy(0.5f), modifier = Modifier.padding(16.dp))
                }

                HorizontalDivider(
                    color = Color.LightGray.copy(0.5f),
                    thickness = 3.dp,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ){
                    Text("Ma chuyen di", style = MaterialTheme.typography.titleMedium, color = PrimaryColor, modifier = Modifier.padding(start = 16.dp))
                    Text(tripId, style = MaterialTheme.typography.bodyMedium, color = Color.Black.copy(0.5f), modifier = Modifier.padding(16.dp))
                }

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ){
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.LightGray.copy(0.5f))
                            .width(120.dp)
                            .height(80.dp)
                    ){
                        TextColumn(title = "$fee", subTitle = "Giá cước", modifier = Modifier.align(Alignment.Center))
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.LightGray.copy(0.5f))
                            .width(120.dp)
                            .height(80.dp)
                    ){
                        TextColumn(title = totalMinutes.toString(), subTitle = "phút", modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        TextField(
            value = feedback,
            onValueChange = { feedback = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Chia sẻ cảm nhận của bạn về chuyến đi") },
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.White,
                cursorColor = PrimaryColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = PrimaryColor,
                unfocusedIndicatorColor = PrimaryColor
            ),
            keyboardActions = KeyboardActions(
                onDone = {navigateToMainScreen()}

            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )

        ButtonComponent(
            value = "Gửi đánh giá",
            onClick = { navigateToMainScreen() },
            color = ButtonColors(
                contentColor = Color.White,
                containerColor = PrimaryColor,
                disabledContentColor = Color.Gray,
                disabledContainerColor = Color.Gray.copy(0.5f)
            ),
            Modifier.padding(horizontal = 30.dp, vertical = 10.dp)
        )
    }
}

@Composable
fun IconWithTextVertical(icon: ImageVector, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = icon, contentDescription = "null", modifier = Modifier.size(45.dp), tint = PrimaryColor)
        Text(text, style = MaterialTheme.typography.bodyMedium, color = PrimaryColor)
    }
}