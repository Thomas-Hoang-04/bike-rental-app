package com.example.bikerentalapp.navigation

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bikerentalapp.screen.login.*
import com.example.bikerentalapp.screen.policy.*
import com.example.bikerentalapp.screen.login.SignInClicks
import com.example.bikerentalapp.screen.login.SignUpClicks
import com.example.bikerentalapp.screen.main.*
import com.example.bikerentalapp.screen.main.qrcode.QrCodeResultScreen
import com.example.bikerentalapp.screen.main.qrcode.QrScreen
import com.example.bikerentalapp.screen.main.qrcode.TrackingMapScreen
import com.example.bikerentalapp.screen.main.station.StationsScreen

//@Serializable
//enum class AuthScreens{
//    Login,
//    SignUp,
//    ForgotPassword,
//    TermsOfUse,
//    PrivacyPolicy,
//    OTPConfirm,
//}
//
//@Serializable
//enum class Screens.Main{
//    Home,
//}



@Composable
fun PostOfficeApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.Auth,
    ) {
        navigation<Screens.Auth>(
            startDestination = Screens.Auth.Login
        ) {
            composable<Screens.Auth.Login> {
                SignInScreen(
                    onClick = { click ->
                        when(click) {
                            SignInClicks.SignUp -> { navController.navigate(Screens.Auth.SignUp) }
                            SignInClicks.ForgotPassword -> { navController.navigate(Screens.Auth.ForgotPassword) }
                            SignInClicks.SignInSuccess -> {
                                navController.navigate(Screens.Main.Home) {
                                    popUpTo(Screens.Auth.Login) { inclusive = true }
                                }
                            }
                        }
                    }
                )
            }

            composable<Screens.Auth.SignUp>(
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
                                navController.navigate(Screens.Main.Home) {
                                    popUpTo(Screens.Auth.SignUp) { inclusive = true }
                                }
                            }
                            SignUpClicks.SignIn -> {
                                navController.navigateUp()
                            }
                            SignUpClicks.TermsOfUse -> {
                                navController.navigate(Screens.Auth.TermsOfUse)
                            }
                            SignUpClicks.PrivacyPolicy -> {
                                navController.navigate(Screens.Auth.PrivacyPolicy)
                            }
                        }
                    }
                )
            }

            composable<Screens.Auth.ForgotPassword>(
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
                                navController.navigate(Screens.Auth.OTPConfirm)
                            }
                        }
                    }
                )
            }

            composable<Screens.Auth.TermsOfUse>(
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

            composable<Screens.Auth.PrivacyPolicy>(
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

            composable<Screens.Auth.OTPConfirm>(
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
                                navController.navigate(Screens.Auth.Login)
                            }

                            OTPClicks.OTPConfirm -> {
                                navController.navigate(Screens.Main.Home) {
                                    popUpTo(Screens.Auth.OTPConfirm) { inclusive = true }
                                }
                            }
                        }
                    }
                )
            }
        }



        navigation<Screens.Main>(
            startDestination = Screens.Main.Home
        ) {
            composable<Screens.Main.Home> {
                MainScreen(navController) {
                    HomeScreen(onFeatureClick = {}, paddingValues = it)
                }
            }

            composable<Screens.Main.Station> {
                MainScreen(navController) {
                    StationsScreen()
                }
            }

            composable<Screens.Main.Notification> {
                MainScreen(navController) {
                    NotificationScreen()
                }
            }

            composable<Screens.Main.Profile> {
                MainScreen(navController) {
                    ProfileScreen()
                }
            }

            composable<Screens.Main.QRCode> {
                MainScreen(navController) {
                    QrScreen()
                }
            }

            composable(
                route = "${Screens.Main.QRResult}/{qrCodeContent}",
                arguments = listOf(navArgument("qrCodeContent") {
                    type = NavType.StringType
                }),
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
            ){
                backStackEntry ->
                val qrCodeContent = backStackEntry.arguments?.getString("qrCodeContent")
                QrCodeResultScreen(qrCodeContent = qrCodeContent.orEmpty(),navController = navController)
            }

            composable(
                "${Screens.Main.TrackingMap}/{bikeId}",
                arguments = listOf(navArgument("bikeId") {
                    try{
                        type = NavType.StringType
                    }catch (e : Exception){
                        Log.e("BikeId",e.toString())
                    }
                }),
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(500)
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
            ) { backStackEntry ->
                val bikeId = backStackEntry.arguments?.getString("bikeId")
                TrackingMapScreen(bikeId = bikeId.orEmpty())
            }
        }
    }
}


