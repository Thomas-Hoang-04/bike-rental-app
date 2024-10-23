package com.example.bikerentalapp.components

sealed class InputType {
    data object Text: InputType()
    data object Phone: InputType()
    data object Email: InputType()
    data object Password: InputType()
}