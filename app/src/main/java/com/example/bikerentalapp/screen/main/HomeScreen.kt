package com.example.bikerentalapp.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ControlPointDuplicate
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikerentalapp.api.data.UserDetails
import com.example.bikerentalapp.components.GridLayout
import com.example.bikerentalapp.components.UserAccount
import com.example.bikerentalapp.components.LocalNavigation
import com.example.bikerentalapp.components.features
import com.example.bikerentalapp.model.AccountViewModel
import com.example.bikerentalapp.navigation.Screens
import com.example.bikerentalapp.ui.theme.PrimaryColor
import java.text.NumberFormat

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
) {
    val scrollState = rememberScrollState()
    val isBalanceVisible = remember { mutableStateOf(false) }
    val navController = LocalNavigation.current
    val accModel = UserAccount.current
    val details = accModel.details.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                0.dp,
                paddingValues.calculateTopPadding(),
                0.dp, 0.dp
            )
            .verticalScroll(scrollState),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    paddingValues.calculateStartPadding(
                        LayoutDirection.Ltr
                    ),
                    0.dp,
                    paddingValues.calculateEndPadding(
                        LayoutDirection.Ltr
                    ),
                    paddingValues.calculateBottomPadding()
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryColor)
                    .wrapContentHeight()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Xin chào, ${details.value?.name}",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .padding(4.dp)
                    )

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = PrimaryColor,
                            modifier = Modifier.size(24.dp).clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                navController.navigate(Screens.Main.Profile) {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color.White.copy(alpha = 0.1f))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ) {}
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize().clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ) {
                                navController.navigate(Screens.Main.Station) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Tìm kiếm trạm xe",
                                color = Color.White.copy(alpha = 0.7f),
                                style = TextStyle(
                                    fontSize = 13.sp
                                )
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = 72.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Tài khoản chính",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Black
                                    )
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text =
                                        (if (isBalanceVisible.value) NumberFormat.getInstance().format(details.value?.balance)
                                        else "**********") + " điểm",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = PrimaryColor
                                    )
                                )
                            }

                            IconButton(
                                onClick = {
                                    isBalanceVisible.value = !isBalanceVisible.value
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = if (isBalanceVisible.value)
                                        Icons.Filled.VisibilityOff
                                    else
                                        Icons.Filled.Visibility,
                                    contentDescription = "Balance Visibility",
                                    tint = PrimaryColor,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))
                        }

                        HorizontalDivider(
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp),
                            thickness = 1.dp,
                            color = Color.Gray
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier,
                                    contentAlignment = Alignment.Center) {
                                    IconButton(
                                        onClick = {
                                            navController.navigate(Screens.Features.TopUp) {
                                                launchSingleTop = true
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Wallet,
                                            contentDescription = "Deposit",
                                            tint = PrimaryColor,
                                            modifier = Modifier
                                                .size(36.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = "Nạp điểm",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Box(
                                    modifier = Modifier,
                                    contentAlignment = Alignment.Center
                                ) {
                                    IconButton(
                                        onClick = {
                                            navController.navigate(Screens.Features.TransactionHistory) {
                                                launchSingleTop = true
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.History,
                                            contentDescription = "Transaction History",
                                            tint = PrimaryColor,
                                            modifier = Modifier
                                                .size(36.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = "Lịch sử\ngiao dịch",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier,
                                    contentAlignment = Alignment.Center) {
                                    IconButton(
                                        onClick = {
                                            navController.navigate(Screens.Features.PointSharing) {
                                                launchSingleTop = true
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.ControlPointDuplicate,
                                            contentDescription = "Share points",
                                            tint = PrimaryColor,
                                            modifier = Modifier
                                                .size(36.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = "Chia sẻ điểm",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Tiện ích",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Normal
                    ),
                    modifier = Modifier.padding(start = 10.dp)
                )

                GridLayout(
                    items = features,
                    column = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 10.dp),
                    navController = navController
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val accModel = AccountViewModel()
    accModel.setUsername("Nguyễn Văn A")
    accModel.setDetails(UserDetails(
        name = "Nguyễn Văn A",
        phoneNum = "0123456789",
        dob = "01/01/2000",
        email = "",
        balance = 100000
    ))
    HomeScreen(
        paddingValues = PaddingValues(0.dp),
    )
}