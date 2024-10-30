package com.example.bikerentalapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bikerentalapp.screen.login.*
import com.example.bikerentalapp.screen.policy.*
import com.example.bikerentalapp.screen.login.SignInClicks
import com.example.bikerentalapp.screen.login.SignUpClicks
import com.example.bikerentalapp.screen.main.MainScreen

const val Login = "login"
const val SignUp = "signup"
const val ForgotPassword = "forgotPassword"
const val MainScreen = "mainScreen"
const val TermsOfUse = "termsOfUse"
const val PrivacyPolicy = "privacyPolicy"
const val OTPConfirm = "OTPConfirm"

@Composable
fun PostOfficeApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Login,
    ) {
        composable(Login) {
            SignInScreen(
                onClick = { click ->
                    when(click) {
                        SignInClicks.SignUp -> { navController.navigate(SignUp) }
                        SignInClicks.ForgotPassword -> { navController.navigate(ForgotPassword) }
                        SignInClicks.SignInSuccess -> {
                            navController.navigate(MainScreen) {
                                popUpTo(Login) { inclusive = true }
                            }
                        }
                    }
                }
            )
        }

        composable(
            route = SignUp,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(400)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(400)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(400)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(400)
                )
            }
        ) {
            SignUpScreen(
                onClick = { click ->
                    when (click) {
                        SignUpClicks.SignUpSuccess -> {
                            navController.navigate(MainScreen) {
                                popUpTo(SignUp) { inclusive = true }
                            }
                        }
                        SignUpClicks.SignIn -> {
                            navController.navigateUp()
                        }
                        SignUpClicks.TermsOfUse -> {
                            navController.navigate(TermsOfUse)
                        }
                        SignUpClicks.PrivacyPolicy -> {
                            navController.navigate(PrivacyPolicy)
                        }
                    }
                }
            )
        }

        composable(
            route = ForgotPassword,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(400)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(400)
                )
            },
            popEnterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(400)
                )
            },
            popExitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(400)
                )
            }
        ) {
            ForgotPassword(
                onClick = { click ->
                    when (click) {
                        ForgotPasswordClicks.BackToSignIn -> {
                            navController.navigateUp()
                        }
                        ForgotPasswordClicks.OTPConfirm -> {
                            navController.navigate(OTPConfirm)
                        }
                    }
                }
            )
        }

        composable(
            route = TermsOfUse,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(400)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(400)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(400)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(400)
                )
            }
        ) {
            TermsOfUse(
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(
            route = PrivacyPolicy,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(400)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(400)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(400)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(400)
                )
            }
        ) {
            PrivacyPolicy(
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(
            route = OTPConfirm,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(400)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(400)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(400)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(400)
                )
            }
        ) {
            OTPScreen(
                onClick = { click ->
                    when (click) {
                        OTPClicks.BackToSignIn -> {
                            navController.navigate(Login)
                        }

                        OTPClicks.OTPConfirm -> {
                            navController.navigate(MainScreen)
                        }
                    }
                }
            )
        }


        composable(MainScreen) {
            MainScreen()
        }
    }
}

