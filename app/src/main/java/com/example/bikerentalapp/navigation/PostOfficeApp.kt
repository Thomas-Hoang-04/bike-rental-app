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
import com.example.bikerentalapp.screen.main.MainNavigationScreen

enum class NavigationScreens{
    Login,
    SignUp,
    ForgotPassword,
    TermsOfUse,
    PrivacyPolicy,
    OTPConfirm,
    MainScreen,
}

@Composable
fun PostOfficeApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationScreens.Login.name,
    ) {
        composable(NavigationScreens.Login.name) {
            SignInScreen(
                onClick = { click ->
                    when(click) {
                        SignInClicks.SignUp -> { navController.navigate(NavigationScreens.SignUp.name) }
                        SignInClicks.ForgotPassword -> { navController.navigate(NavigationScreens.ForgotPassword.name) }
                        SignInClicks.SignInSuccess -> {
                            navController.navigate(NavigationScreens.MainScreen.name) {
                                popUpTo(NavigationScreens.Login.name) { inclusive = true }
                            }
                        }
                    }
                }
            )
        }

        composable(
            route = NavigationScreens.SignUp.name,
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
                            navController.navigate(NavigationScreens.MainScreen.name) {
                                popUpTo(NavigationScreens.SignUp.name) { inclusive = true }
                            }
                        }
                        SignUpClicks.SignIn -> {
                            navController.navigateUp()
                        }
                        SignUpClicks.TermsOfUse -> {
                            navController.navigate(NavigationScreens.TermsOfUse.name)
                        }
                        SignUpClicks.PrivacyPolicy -> {
                            navController.navigate(NavigationScreens.PrivacyPolicy.name)
                        }
                    }
                }
            )
        }

        composable(
            route = NavigationScreens.ForgotPassword.name,
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
                            navController.navigate(NavigationScreens.OTPConfirm.name)
                        }
                    }
                }
            )
        }

        composable(
            route = NavigationScreens.TermsOfUse.name,
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
            route = NavigationScreens.PrivacyPolicy.name,
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
            route = NavigationScreens.OTPConfirm.name,
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
                            navController.navigate(NavigationScreens.Login.name)
                        }

                        OTPClicks.OTPConfirm -> {
                            navController.navigate(NavigationScreens.MainScreen.name) {
                                popUpTo(NavigationScreens.OTPConfirm.name) { inclusive = true }
                            }
                        }
                    }
                }
            )
        }


        composable(NavigationScreens.MainScreen.name) {
            MainNavigationScreen()
        }
    }
}

