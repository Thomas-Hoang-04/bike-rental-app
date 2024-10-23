package com.example.bikerentalapp.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.bikerentalapp.screen.login.SignInScreen
import com.example.bikerentalapp.screen.login.SignUpScreen
import com.example.bikerentalapp.screen.policy.PrivacyPolicy
import com.example.bikerentalapp.screen.policy.TermsOfUse

@Composable
fun PostOfficeApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Crossfade(targetState = PostOfficeAppRouter.currentScreen) { currentState ->
            when (currentState.value) {
                is Screen.SignUpScreen -> {
                    SignUpScreen()
                }
                is Screen.TermOfUse -> {
                    TermsOfUse()
                }
                is Screen.PrivacyPolicy -> {
                    PrivacyPolicy()
                }
                is Screen.SignInScreen -> {
                    SignInScreen()
                }
            }
        }
    }
}