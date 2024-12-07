package com.example.bikerentalapp.components

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.example.bikerentalapp.R
import com.example.bikerentalapp.ui.theme.*
import java.util.Calendar

@Composable
fun HeadingTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center
    )
}

@Composable
fun TextInput(
    label: String,
    placeholder: String,
    inputType: InputType = InputType.Text,
    required: Boolean = false,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    error: String? = null,
    onFocusChange: (Boolean) -> Unit = {},
    readOnly: Boolean = false
) {
    val isPasswordVisible = remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (error != null) Color.Red else Color.Gray,
                shape = RoundedCornerShape(6.dp)
            )
            .background(
                color = bg_inputColor,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(10.dp)
            .then(if (readOnly) {
                Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onFocusChange(true) }
            } else Modifier)
    ) {
        Row {
            Text(
                text = label,
                color = LabelColor,
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 4.dp),
                fontWeight = FontWeight.Bold
            )

            if (required) {
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = "*",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }

        Spacer(Modifier.padding(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Transparent)
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                            onFocusChange(isFocused)
                        },
                    enabled = !readOnly,
                    keyboardOptions = when (inputType) {
                        is InputType.Phone -> KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next
                        )
                        is InputType.Email -> KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        )
                        is InputType.Password -> KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        )
                        else -> KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    },
                    visualTransformation = when {
                        inputType is InputType.Password && !isPasswordVisible.value ->
                            PasswordVisualTransformation()
                        else -> VisualTransformation.None
                    },
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp
                    ),
                    decorationBox = { innerText ->
                        Box(
                            modifier = Modifier.background(Color.Transparent)
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                )
                            }
                            innerText()
                        }
                    }
                )
            }

            if (inputType is InputType.Password) {
                IconButton(
                    onClick = { isPasswordVisible.value = !isPasswordVisible.value },
                    modifier = Modifier.size(18.dp)
                ) {
                    Icon(
                        imageVector = if (isPasswordVisible.value)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff,
                        contentDescription = if (isPasswordVisible.value)
                            "Hide password"
                        else
                            "Show password",
                        tint = Color.Gray
                    )
                }
            }
        }

        if (error != null) {
            Text(
                text = error,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun ButtonComponent(
    value: String,
    onClick: () -> Unit,
    color: ButtonColors,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        colors = color,
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = value,
            style = TextStyle(
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun ClickableTextComponent(
    texts: List<AnnotatedText>,
    style: TextStyle = TextStyle (
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
        fontSize = 12.sp
    )
) {
    val annotatedString = buildAnnotatedString {
        texts.forEach { text ->
            when (text) {
                is AnnotatedText.Plain -> append(text = text.text)
                is AnnotatedText.Clickable -> withLink(
                    LinkAnnotation.Clickable(
                        tag = text.text,
                        styles = TextLinkStyles(text.style)
                    ) {
                        text.onClick(text.text)
                    }
                ) {
                    append(text.text)
                }
            }
        }
    }

    Text(
        text = annotatedString,
        style = style
    )
}

@Composable
fun ClickableTextLoginComponent(
    tryingToLogin: Boolean = false,
    onTextSelected: () -> Unit
) {
    val initialText = if (tryingToLogin) "Bạn đã có tài khoản? " else "Bạn chưa có tài khoản? "
    val loginText = if (tryingToLogin) stringResource(id = R.string.sign_in) else stringResource(id = R.string.sign_up)

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withLink(
            LinkAnnotation.Clickable(
                tag = loginText,
                styles = TextLinkStyles(SpanStyle(color = PrimaryColor, fontWeight = FontWeight.Bold))
            ) {
                onTextSelected()
            }
        ) {
            append(loginText)
        }
    }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(28.dp),
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        text = annotatedString,
    )
}

@SuppressLint("DefaultLocale")
@Composable
fun DatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val calendar = Calendar.getInstance()
    val context = LocalContext.current

    val maxCalendar = Calendar.getInstance()
    maxCalendar.add(Calendar.YEAR, -18)

    val minCalendar = Calendar.getInstance()
    minCalendar.add(Calendar.YEAR, -100)

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSelected(
                String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            )
        },
        maxCalendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    datePickerDialog.datePicker.apply {
        maxDate = maxCalendar.timeInMillis
        minDate = minCalendar.timeInMillis
    }

    datePickerDialog.setOnDismissListener {
        onDismiss()
    }

    datePickerDialog.show()
}

@Composable
fun FeatureCard(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card (
            modifier = Modifier
                .size(72.dp)
                .clickable(onClick = onClick),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp),

        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = PrimaryColor,
                    modifier = Modifier
                        .size(56.dp)
                )
            }

        }


        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SearchBar(
    hint: String,
    searchText: String,
    onSearchChange: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        placeholder = {
            Text(
                text = hint,
                color = Color.White.copy(alpha = 0.7f),
                style = TextStyle(
                    fontSize = 12.sp
                )
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.White
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        textStyle = TextStyle(
            fontSize = 12.sp
        ),
        shape = RoundedCornerShape(14.dp),
        singleLine = true,
        visualTransformation = VisualTransformation.None,
        interactionSource = interactionSource,

    )
}

@Composable
fun GridLayout(
    items: List<FeatureItem>,
    column: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        val rows = (items.size + column - 1) / column

        for (rowIndex in 0 until rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (columnIndex in 0 until column) {
                    val itemIndex = rowIndex * column + columnIndex
                    if (itemIndex < items.size) {
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            FeatureCard(
                                icon = items[itemIndex].icon,
                                title = items[itemIndex].title,
                                onClick = items[itemIndex].onClick
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

fun makeToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
            .background(Color.Gray.copy(alpha = 0.5f))
            .pointerInput(Unit) { },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = PrimaryColor)
    }

}


