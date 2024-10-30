package com.example.bikerentalapp.components

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.example.bikerentalapp.R
import com.example.bikerentalapp.ui.theme.*

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
                color = Color.Black,
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 4.dp)
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
                modifier = Modifier.weight(1f)
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
                        Box {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
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
    color: ButtonColors
) {
    Button(
        onClick = onClick,
        modifier = Modifier
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
fun ClickableTextLoginComponent(tryingToLogin: Boolean = false, onTextSelected: () -> Unit) {
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

