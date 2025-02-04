package com.example.bikerentalapp.screen.features

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableSupportingPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikerentalapp.api.data.*
import com.example.bikerentalapp.api.network.RetrofitInstances
import com.example.bikerentalapp.components.*
import com.example.bikerentalapp.ui.theme.LightPrimaryColor
import com.example.bikerentalapp.ui.theme.ListColor
import com.example.bikerentalapp.ui.theme.PrimaryColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.time.Duration
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTicketScreen(
    initial: Boolean,
    isLoading: MutableState<Boolean>,
    navigation: () -> Unit = {},
) {
    val context = LocalContext.current
    val navController = LocalNavigation.current
    var ticketList by remember { mutableStateOf<List<TicketDetails>>(emptyList()) }

    val account = UserAccount.current
    val token = account.token.collectAsState()
    val username = account.username.collectAsState()
    val scope = rememberCoroutineScope()
    val retrofit = RetrofitInstances.Query(token.value).queryAPI

    val fetchTickets = suspend {
        val req = retrofit.getTickets(username.value)
        if (req.isSuccessful) {
            val body = req.body() as QueryResponse
            ticketList = body.data
        } else {
            makeToast(context, "Lỗi khi tải dữ liệu. Vui lòng thử lại")
        }
        isLoading.value = false
    }

    LaunchedEffect(initial) {
        fetchTickets()
    }

    Scaffold(
        containerColor = ListColor,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                ),
                title = {
                    Text(
                        text = "Lịch sử mua vé",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
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
                                isLoading.value = true
                                fetchTickets()
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
                modifier = Modifier.fillMaxWidth()
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                contentColor = Color.Black,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(0.05f))
                Button(
                    onClick = navigation,
                    modifier = Modifier
                        .weight(0.9f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryColor
                    )
                ) {
                    Text("Mua vé")
                }
                Spacer(modifier = Modifier.weight(0.05f))
            }
        }
    ) { pad ->
        LazyColumn(
            contentPadding = PaddingValues(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(horizontal = 20.dp)
        ) {
            items(ticketList) { ticket ->
                TicketItem(ticket)
            }
        }
    }
}

@Composable
fun TicketItem(
    ticket: TicketDetails,
) {
    val period = remember {
        derivedStateOf {
            val now = OffsetDateTime.now()
            Duration.between(now, OffsetDateTime.parse(ticket.validTill)).toMinutes()
        }
    }

    val status = when (ticket.status) {
        TicketStatus.ACTIVE -> {
            "Còn ${period.value.minutes}"
        }
        TicketStatus.EXPIRED -> "Đã hết hạn"
    }

    val statusColor = when (ticket.status) {
        TicketStatus.ACTIVE -> {
            if (period.value.minutes > 12.hours) PrimaryColor
            else Color(0xFFD97706)
        }
        TicketStatus.EXPIRED -> Color.Red
    }

    val type = when (ticket.ticket) {
        TicketTypes.DAILY -> "Vé ngày"
        TicketTypes.MONTHLY -> "Vé tháng"
        else -> "Vé vãng lai"
    }

    val timestamp: (String) -> String = { time ->
        val convertedTime = OffsetDateTime.parse(time)
            .atZoneSameInstant(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(7)))
        val hour = "%02d".format(convertedTime.toOffsetDateTime().hour)
        val minute = "%02d".format(convertedTime.toOffsetDateTime().minute)
        val datestamp = convertedTime.toOffsetDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        "$hour:$minute, $datestamp"
    }

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
                imageVector = Icons.Filled.ConfirmationNumber,
                contentDescription = type,
                modifier = Modifier
                    .size(40.dp)
                    .padding(start = 10.dp),
                tint = if (ticket.status == TicketStatus.ACTIVE) PrimaryColor else Color.Red
            )

            VerticalDivider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier
                    .height(40.dp)
                    .padding(horizontal = 16.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = type,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Thời điểm mua: ${timestamp(ticket.issuedAt)}",
                    fontSize = 14.sp,
                    color = Color.Gray,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = status,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusColor
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyingTicketScreen(
    navigation: () -> Unit = {}
) {
    val ticketType = remember { mutableStateOf<TicketTypes?>(null) }
    val ticketAmount = remember { mutableIntStateOf(1) }
    val currentTime = remember { mutableStateOf(OffsetDateTime.now()) }
    val validTime = remember {
        derivedStateOf {
            when (ticketType.value) {
                TicketTypes.DAILY -> currentTime.value.plusDays(1)
                TicketTypes.MONTHLY -> currentTime.value.plusMonths(1)
                else -> currentTime.value
            }
        }
    }
    val displayValid = remember {
        derivedStateOf {
            val formattedTime: (OffsetDateTime) -> String = {
                it.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            }
            "Từ ${formattedTime(currentTime.value)} đến ${formattedTime(validTime.value)}"
        }
    }
    val disclaimer = remember {
        derivedStateOf {
            when (ticketType.value) {
                TicketTypes.DAILY -> "Vé ngày, áp dụng tối đa 450 phút (7.5 giờ) di chuyển trong ngày, phụ thu thêm 3.000 điểm cho mỗi 15 phút tiếp theo"
                TicketTypes.MONTHLY -> "Vé tháng, chuyến đi không quá 45 phút, không áp dụng thuê nhiều xe cùng lúc, không giới hạn số chuyến trong ngày"
                else -> ""
            }
        }
    }
    val price = remember {
        derivedStateOf {
            when (ticketType.value) {
                TicketTypes.DAILY -> 49000 * ticketAmount.intValue
                TicketTypes.MONTHLY -> 89000
                else -> 0
            }
        }
    }

    var infoDialogTrigger by remember { mutableStateOf(false) }
    var confirmDialogTrigger by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val account = UserAccount.current
    val username = account.username.collectAsState()
    val details = account.details.collectAsState()
    val balance = remember {
        derivedStateOf {
            details.value?.balance ?: 0
        }
    }
    val token = account.token.collectAsState()
    val retrofit = RetrofitInstances.Query(token.value).queryAPI
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    if (isLoading) {
        LoadingScreen()
    }

    if (infoDialogTrigger) {
        CustomDialog(
            title = "Xác nhận nạp điểm",
            message = "Xác nhận mua ${if (ticketType.value == TicketTypes.DAILY) ticketAmount.intValue else 1} vé ${if (ticketType.value == TicketTypes.DAILY) "ngày" else "tháng"} với ${NumberFormat.getInstance().format(price.value)} điểm?",
            onDismiss = { infoDialogTrigger = false },
            onAccept = {
                scope.launch {
                    infoDialogTrigger = false
                    isLoading = true
                    val req = retrofit.createTicket(
                        TicketRequest(
                            username = username.value,
                            ticket = ticketType.value!!,
                            price = price.value
                        )
                    )
                    if (req.isSuccessful) {
                        delay(2000)
                        isLoading = false
                        confirmDialogTrigger = true
                    } else {
                        isLoading = false
                        makeToast(context, "Mua vé thất bại. Vui lòng thử lại sau")
                    }
                }
            }
        )
    }

    if (confirmDialogTrigger) {
        CustomDialog(
            title = "Mua vé thành công",
            icon = Icons.Default.CheckCircle,
            onAccept = {
                confirmDialogTrigger = false
                scope.launch {
                    isLoading = true
                    account.update()
                    delay(500)
                    navigation()
                }
            },
            canDismiss = false
        )
    }

    Scaffold(
        containerColor = ListColor,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                ),
                title = {
                    Text(
                        text = "Mua vé",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigation,
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
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                contentColor = Color.Black,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(112.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountBalanceWallet,
                            tint = PrimaryColor,
                            contentDescription = "Tài khoản chính",
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Tài khoản chính",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "${NumberFormat.getInstance().format(balance.value)} điểm",
                            color = PrimaryColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(
                            onClick = {
                                if (ticketType.value == null) {
                                    makeToast(context, "Vui lòng chọn loại vé")
                                } else if (price.value > balance.value) {
                                    makeToast(context, "Số điểm không đủ để mua vé")
                                } else {
                                    infoDialogTrigger = true
                                }
                            },
                            modifier = Modifier
                                .weight(0.9f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryColor
                            )
                        ) {
                            Text("Mua vé")
                        }
                    }
                }
            }
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(6.dp)
        ) {
            Text(
                text = "Chọn loại vé",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = ticketType.value == TicketTypes.DAILY,
                    onClick = { ticketType.value = TicketTypes.DAILY },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = PrimaryColor,
                        unselectedColor = Color.Gray
                    )
                )
                Text(
                    text = "Vé ngày",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            ticketType.value = TicketTypes.DAILY
                        },
                    color = if (ticketType.value == TicketTypes.DAILY) PrimaryColor else Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = ticketType.value == TicketTypes.MONTHLY,
                    onClick = { ticketType.value = TicketTypes.MONTHLY },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = PrimaryColor,
                        unselectedColor = Color.Gray
                    )
                )
                Text(
                    text = "Vé tháng",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            ticketType.value = TicketTypes.MONTHLY
                        },
                    color = if (ticketType.value == TicketTypes.MONTHLY) PrimaryColor else Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
            if (ticketType.value == TicketTypes.DAILY) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Số lượng",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(16.dp)
                            .background(LightPrimaryColor, RoundedCornerShape(16.dp))
                    ) {
                        IconButton(
                            onClick = { if (ticketAmount.intValue > 1) ticketAmount.intValue-- },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Giảm số lượng"
                            )
                        }
                        Text(
                            text = ticketAmount.intValue.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryColor,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        IconButton(
                            onClick = { if (ticketAmount.intValue <= 10) ticketAmount.intValue++ },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Tăng số lượng"
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            if (ticketType.value != null) {
                Text(
                    "Hiệu lực",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
                OutlinedTextField(
                    enabled = false,
                    value = displayValid.value,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledBorderColor = Color.Gray,
                        disabledTextColor = Color.Black
                    ),
                    textStyle = TextStyle(
                        fontSize = 15.sp
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    disclaimer.value,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(22.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(LightPrimaryColor, RoundedCornerShape(8.dp))
                ) {
                    Text(
                        "Giá vé",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        NumberFormat.getInstance().format(-price.value),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryColor,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun TicketScreen() {
    val navigator = rememberSupportingPaneScaffoldNavigator<Any>()
    val isLoading = remember { mutableStateOf(true) }
    val initial = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    if (isLoading.value) {
        LoadingScreen()
    }

    NavigableSupportingPaneScaffold(
        navigator = navigator,
        mainPane = {
            ListTicketScreen(initial.value, isLoading) {
                scope.launch {
                    delay(300)
                    navigator.navigateTo(SupportingPaneScaffoldRole.Supporting)
                }
            }
        },
        supportingPane = {
            AnimatedPane {
                BuyingTicketScreen {
                    scope.launch {
                        initial.value = !initial.value
                        delay(250)
                        navigator.navigateBack()
                    }
                }
            }
        }
    )
}