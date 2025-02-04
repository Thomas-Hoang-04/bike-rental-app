package com.example.bikerentalapp.components

import android.content.Intent
import androidx.annotation.FloatRange
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBike
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.outlined.Navigation
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikerentalapp.R
import com.example.bikerentalapp.api.data.BikeType
import com.example.bikerentalapp.api.data.Station
import com.example.bikerentalapp.ui.theme.PrimaryColor
import java.util.Locale
import kotlin.math.round
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SearchBarWithDebounce (
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    active: Boolean = false,
    onActiveChange: (Boolean) -> Unit,
    onFocusChange: () -> Unit,
    placeholder: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    kbController: SoftwareKeyboardController?,
    items: List<T>,
    rows: @Composable (T) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = {
                        onQueryChange(it)
                        onSearch(it.lowercase(Locale.getDefault()))
                    },
                    onSearch = {
                        onSearch(it.lowercase(Locale.getDefault()))
                        kbController?.hide()
                        onQueryChange("")
                    },
                    expanded = active,
                    onExpandedChange = onActiveChange,
                    placeholder = placeholder,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                )
            },
            expanded = active,
            onExpandedChange = onActiveChange,
            modifier = modifier
                .height(55.dp)
                .align(Alignment.CenterHorizontally)
                .onFocusChanged { focusState ->
                    if(focusState.isFocused){
                        onFocusChange()
                    }
                },
            colors = SearchBarDefaults.colors(),
            content = {},
        )
        Card(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, bottom = 70.dp)
                .background(color = Color.Transparent)
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
                    text = station.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedButton(
                        onClick = {
                            val uri =
                                "https://www.google.com/maps/dir/?api=1&destination=${station.coordinates.lat},${station.coordinates.lng}&travelmode=two-wheeler".toUri()
                            val mapIntent = Intent(Intent.ACTION_VIEW, uri)

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
                    count = station.bikeList.filter { it.type == BikeType.MANUAL }.size,
                )
                InfoBox(
                    imageVector = Icons.Filled.ElectricBike,
                    label = "Xe điện",
                    count = station.bikeList.filter { it.type == BikeType.ELECTRIC }.size,
                    backgroundColor = Color(0xFFE8F5E9)
                )
                InfoBox(
                    imageVector = Icons.Filled.LocalParking,
                    label = "Số chỗ đậu",
                    count = station.capacity - station.bikeList.size,
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

@Composable
fun CircularButtonWithText(icon:ImageVector,text:String,onClick : ()->Unit,color: Color = Color.White,iconColor : Color = Color.Black,size : Dp = 80.dp) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .border(1.dp,Color.Gray, CircleShape)
                .size(size),
            colors = IconButtonColors(
                containerColor = color,
                contentColor = Color.Black,
                disabledContentColor = Color.Gray,
                disabledContainerColor = Color.Gray,
            )
        ) {
            Icon(icon, modifier = Modifier.size(30.dp), contentDescription = null, tint = iconColor)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text,
            style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun IconWithTextHorizontal(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    iconTint: Color = PrimaryColor,
    textColor: Color = PrimaryColor
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(iconSize),
            tint = iconTint
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}

object RatingBarDefaults {

    @Composable
    fun UnratedContent(color: Color = Color.LightGray) {
        Icon(
            tint = color,
            imageVector = Icons.Rounded.Star,
            modifier = Modifier.fillMaxSize(),
            contentDescription = "Unrated Star"
        )
    }

    @Composable
    fun RatedContent(color: Color = Color(0xFFFFC107)) {
        Icon(
            tint = color,
            imageVector = Icons.Rounded.Star,
            modifier = Modifier.fillMaxSize(),
            contentDescription = "Rated Star"
        )
    }
}

@Composable
fun RatingBar(
    rating: Float,
    onRatingChanged: (newRating: Float) -> Unit,
    modifier: Modifier = Modifier,
    @FloatRange(0.0, 1.0)
    ratingStep: Float = 0.5f,
    starsCount: Int = 5,
    starSize: Dp = 32.dp,
    starSpacing: Dp = 0.dp,
    unratedContent: @Composable BoxScope.(starIndex: Int) -> Unit = {
        RatingBarDefaults.UnratedContent()
    },
    ratedContent: @Composable BoxScope.(starIndex: Int) -> Unit = {
        RatingBarDefaults.RatedContent()
    },
    enableDragging: Boolean = true,
    enableTapping: Boolean = true
) {
    val bounds = remember { mutableMapOf<Int, Rect>() }

    Row(
        horizontalArrangement = Arrangement.spacedBy(starSpacing),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.then(
            if (enableDragging) {
                Modifier.pointerInput(Unit) {
                    detectHorizontalDragGestures { change, _ ->
                        val (index, rect) = bounds.entries.find { (_, rect) ->
                            rect.contains(Offset(change.position.x, 0f))
                        } ?: return@detectHorizontalDragGestures

                        val baseRating = (index - 1)
                        val normalizedX = (change.position.x - rect.left)
                        val fractionalRating = (normalizedX / rect.width).coerceIn(0f, 1f)
                        val roundedRating = when (ratingStep) {
                            1f -> round(fractionalRating)
                            0f -> fractionalRating
                            else -> roundToStep(fractionalRating, ratingStep)
                        }
                        onRatingChanged(baseRating + roundedRating)
                    }
                }
            } else {
                Modifier
            }
        )
    ) {
        for (index in 1..starsCount) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(starSize)
                    .onGloballyPositioned { layoutCoordinates ->
                        bounds[index] = layoutCoordinates.boundsInParent()
                    }
                    .then(
                        if (enableTapping) {
                            Modifier.pointerInput(Unit) {
                                detectTapGestures {
                                    onRatingChanged(index.toFloat())
                                }
                            }
                        } else {
                            Modifier
                        }
                    )
            ) {
                unratedContent(index)

                val fillWidthFraction = when {
                    (rating >= index) -> 1f
                    (rating > index - 1) && (rating <= index) -> rating - (index - 1)
                    else -> 0f
                }
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(ClippingRectShape(fillWidthFraction)),
                    contentAlignment = Alignment.Center,
                    content = {
                        ratedContent(index)
                    }
                )
            }
        }
    }
}

private class ClippingRectShape(private val fillWidthFraction: Float) : Shape {

    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val clippingRect = Rect(Offset.Zero, Size(size.width * fillWidthFraction, size.height))
        return Outline.Rectangle(clippingRect)
    }
}

private fun roundToStep(value: Float, step: Float): Float {
    return round(value / step) * step
}

@Composable
fun TextColumn(title:String,subTitle : String,modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
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

