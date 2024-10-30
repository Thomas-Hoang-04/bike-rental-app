package com.example.bikerentalapp.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bikerentalapp.screen.login.SignInScreen
import com.example.bikerentalapp.screen.login.SignUpScreen
import com.example.bikerentalapp.screen.policy.PrivacyPolicy
import com.example.bikerentalapp.screen.policy.TermsOfUse
import kotlinx.serialization.Serializable



@Composable
fun PostOfficeApp() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        NavHost(
            navController = navController,
            startDestination = Login,
        ) {
            composable<Login> {
                Crossfade(
                    targetState = Login,
                    label = "login",
                    animationSpec = tween(300)
                ) {
                    SignInScreen {
                        navController.navigate(SignUp)
                    }
                }
            }

            composable<SignUp> {
                Crossfade(
                    targetState = SignUp,
                    label = "signUp",
                    animationSpec = tween(300)
                ) {
                    SignUpScreen {
                        navController.navigate(Login)
                    }
                }
            }
        }
    }
}

@Serializable
object Login

@Serializable
object SignUp