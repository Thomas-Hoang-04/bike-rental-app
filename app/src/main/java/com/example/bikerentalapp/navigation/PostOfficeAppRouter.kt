package com.example.bikerentalapp.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen() {
    data object SignUpScreen: Screen()
    data object TermOfUse: Screen()
    data object PrivacyPolicy: Screen()
    data object SignInScreen: Screen()
}

object PostOfficeAppRouter {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.SignInScreen)

    fun navigateTo(destination: Screen) {
        currentScreen.value = destination
    }
}