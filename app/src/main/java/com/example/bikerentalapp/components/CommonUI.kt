package com.example.bikerentalapp.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBike
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.outlined.Navigation
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikerentalapp.R
import com.example.bikerentalapp.model.Station
import com.example.bikerentalapp.ui.theme.PrimaryColor
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SearchBarWithDebounce (
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    active: Boolean = false,
    onActiveChange: (Boolean) -> Unit,
    placeholder: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    kbController: SoftwareKeyboardController?,
    items: List<T>,
    rows: @Composable (T) -> Unit
) {
    Column {
        SearchBar(
            query = query,
            onQueryChange = {
                onQueryChange(it)
                if (it.length > 2) {
                    onSearch(it.lowercase(Locale.getDefault()))
                }
            },
            onSearch = {
                onSearch(it.lowercase(Locale.getDefault()))
                kbController?.hide()
                onQueryChange("")
            },
            modifier = modifier
                .height(55.dp)
                .padding(horizontal = 5.dp),
            active = active,
            onActiveChange = onActiveChange,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            content = {},
        )
        if (query != "") Card(
            modifier = Modifier
                .padding(12.dp)
                .background(color = Color.White)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                items(items) { item ->
                    rows(item)
                }
            }
        }
    }
}

@Composable
fun StationInfoCard(
    station: Station,
    modifier: Modifier
) {
    val context = LocalContext.current
    Card(
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.Blue
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
        ,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.gps),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = station.id,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedButton(
                        onClick = {
                            val uri = Uri.parse("google.navigation:q=${station.lat},${station.lng}&mode=d")
                            val mapIntent = Intent(Intent.ACTION_VIEW, uri).apply {
                                setPackage("com.google.android.apps.maps")
                            }

                            if (mapIntent.resolveActivity(context.packageManager) != null) {
                                context.startActivity(mapIntent)
                            }
                        },
                        shape = RoundedCornerShape(12),
                        border = BorderStroke(width = 1.dp, color = PrimaryColor),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Navigation,
                            contentDescription = "Navigate",
                            tint = PrimaryColor,
                            modifier = Modifier
                                .size(30.dp)
                                .rotate(50f)
                        )
                    }
                    Text(
                        text = "${station.farAway} m",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = station.address,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                InfoBox(
                    imageVector = Icons.Filled.PedalBike , // Replace with actual bicycle icon
                    label = "Xe đạp",
                    count = station.listBicycle.filter { it.type == "Standard" }.size,
                )
                InfoBox(
                    imageVector = Icons.Filled.ElectricBike,
                    label = "Xe điện",
                    count = station.listBicycle.filter { it.type == "Electric" }.size,
                    backgroundColor = Color(0xFFE8F5E9)
                )
                InfoBox(
                    imageVector = Icons.Filled.LocalParking,
                    label = "Số chỗ đậu",
                    count = station.listBicycle.size,
                    backgroundColor = Color(0xFFFFF9C4)
                )
            }
        }
    }
}

@Composable
fun InfoBox(
    imageVector: ImageVector,
    label: String,
    count: Int,
    backgroundColor: Color = Color(0xFFF5F5F5)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(8.dp)
            .width(80.dp)
    ) {
        Icon(imageVector = imageVector, contentDescription = null)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "$label: $count",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}
