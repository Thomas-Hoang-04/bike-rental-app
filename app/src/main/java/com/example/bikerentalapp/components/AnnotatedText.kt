package com.example.bikerentalapp.components

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.bikerentalapp.ui.theme.PrimaryColor

sealed class AnnotatedText {
    data class Plain (val text: String): AnnotatedText()
    data class Clickable (
        val text: String,
        val style: SpanStyle = SpanStyle(
            color = PrimaryColor,
            fontWeight = FontWeight.SemiBold
        ),
        val onClick: (String) -> Unit
    ): AnnotatedText()
}