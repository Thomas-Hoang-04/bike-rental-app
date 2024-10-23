package com.example.bikerentalapp.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.example.bikerentalapp.R
import com.example.bikerentalapp.navigation.PostOfficeAppRouter
import com.example.bikerentalapp.navigation.Screen
import com.example.bikerentalapp.ui.theme.*


@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center
    )
}

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
    required: Boolean = false
) {
    val textValue = remember { mutableStateOf("") }
    val isPasswordVisible = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(6.dp)
            )
            .background(
                color = bg_inputColor,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(10.dp)
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
                    value = textValue.value,
                    onValueChange = { newValue ->
                        when (inputType) {
                            is InputType.Phone -> {
                                if (newValue.all { it.isDigit() }) {
                                    textValue.value = newValue
                                }
                            }
                            else -> textValue.value = newValue
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
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
                            if (textValue.value.isEmpty()) {
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
                    modifier = Modifier
                        .size(18.dp)
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
    }
}

@Composable
fun CheckboxComponent(
    value: String,
    isChecked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    onTextSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )

        ClickableTextComponent(
            value = value,
            onTextSelected = onTextSelected
        )
    }
}

@Composable
fun ButtonComponent(value: String) {
    Button(onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp)
            .background(
                color = PrimaryColor,
                shape = RoundedCornerShape(6.dp)
            ),
        colors = ButtonColors(
            contentColor = Color.White,
            containerColor = PrimaryColor,
            disabledContentColor = Color.Gray,
            disabledContainerColor = disablePrimaryColor
        )
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
fun ClickableTextComponent(value: String,  onTextSelected: (String) -> Unit) {
    val initialText = "Bằng cách tiếp tục, bạn đã đọc và đồng ý với "
    val andText = " và "
    val endText = " của chúng tôi."
    val termOfUseText = stringResource(id = R.string.term_of_use)
    val policyText = stringResource(id = R.string.policy)

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = PrimaryColor)) {
            pushStringAnnotation(tag = "term_of_use", annotation = termOfUseText)
            append(termOfUseText)
        }

        append(andText)
        withStyle(style = SpanStyle(color = PrimaryColor)) {
            pushStringAnnotation(tag = "policy", annotation = policyText)
            append(policyText)
        }
        append(endText)
    }

    ClickableText(
        text = annotatedString,
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            fontSize = 12.sp
        ),
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "term_of_use", start = offset, end = offset)
                .firstOrNull()?.let {
                    onTextSelected(termOfUseText)
                }

            annotatedString.getStringAnnotations(tag = "policy", start = offset, end = offset)
                .firstOrNull()?.let {
                    onTextSelected(policyText)
                }
        }
    )
}

@Composable
fun ClickableTextLoginComponent(tryingToLogin: Boolean = false, onTextSelected: (String) -> Unit) {
    val initialText = if (tryingToLogin) "Bạn đã có tài khoản? " else "Bạn chưa có tài khoản? "
    val loginText = if (tryingToLogin) stringResource(id = R.string.sign_in) else stringResource(id = R.string.sign_up)

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = PrimaryColor, fontWeight = FontWeight.Bold)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }

    ClickableText(
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
        onClick =  { offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also { span ->
                if (span.item == loginText) {
                    onTextSelected(span.item)
                }
            }
        }
    )
}

