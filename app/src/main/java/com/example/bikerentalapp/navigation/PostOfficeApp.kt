package com.example.bikerentalapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bikerentalapp.screen.login.ForgotPassword
import com.example.bikerentalapp.screen.login.ForgotPasswordClicks
import com.example.bikerentalapp.screen.login.OTPClicks
import com.example.bikerentalapp.screen.login.OTPScreen
import com.example.bikerentalapp.model.AccountViewModel
import com.example.bikerentalapp.model.SignUpViewModel
import com.example.bikerentalapp.screen.login.*
import com.example.bikerentalapp.screen.policy.*
import com.example.bikerentalapp.screen.login.SignInClicks
import com.example.bikerentalapp.screen.login.SignInScreen
import com.example.bikerentalapp.screen.login.SignUpClicks
import com.example.bikerentalapp.screen.login.SignUpScreen
import com.example.bikerentalapp.screen.main.HomeScreen
import com.example.bikerentalapp.screen.main.MainScreen
import com.example.bikerentalapp.screen.main.NotificationScreen
import com.example.bikerentalapp.screen.main.ProfileScreen
import com.example.bikerentalapp.screen.main.qrcode.QrCodeResultScreen
import com.example.bikerentalapp.screen.main.qrcode.QrScreen
import com.example.bikerentalapp.screen.main.qrcode.ReturnBikeScreen
import com.example.bikerentalapp.screen.main.qrcode.TrackingMapScreen
import com.example.bikerentalapp.screen.main.station.StationsScreen
import com.example.bikerentalapp.screen.policy.PrivacyPolicy
import com.example.bikerentalapp.screen.policy.TermsOfUse



@Composable
fun PostOfficeApp() {
    val navController = rememberNavController()
    val account = AccountViewModel()
    val signUpViewModel = SignUpViewModel()

    val startDestination = Screens.Auth

    LaunchedEffect(key1 = account) {


    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
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
                                    popUpTo(Screens.Auth) { inclusive = true }
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
            ) { backstackEntry ->
                val isOTPVerified = backstackEntry
                    .savedStateHandle
                    .getStateFlow("isOTPVerified", false)
                    .collectAsState()
                SignUpScreen(
                    isOTPVerified.value,
                    signUpViewModel,
                    onClick = { click, phoneNum ->
                        when (click) {
                            SignUpClicks.SignUpSuccess -> {
                                navController.navigate(Screens.Main.Home) {
                                    popUpTo(Screens.Auth) { inclusive = true }
                                }
                            }
                            SignUpClicks.SignIn -> {
                                navController.navigateUp()
                            }
                            SignUpClicks.OTPVerify -> {
                                navController.navigate(Screens.Auth.OTPConfirm(phoneNum, OTPPurpose.SIGNUP))
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
                    onClick = { click, phoneNum ->
                        when (click) {
                            ForgotPasswordClicks.BackToSignIn -> {
                                navController.navigateUp()
                            }
                            ForgotPasswordClicks.OTPConfirm -> {
                                navController.navigate(
                                    Screens.Auth.OTPConfirm(phoneNum, OTPPurpose.RESET_PASSWORD)
                                )
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
                val args = it.toRoute<Screens.Auth.OTPConfirm>()
                OTPScreen(
                    phoneNumber = args.phoneNumber,
                    purpose = args.purpose,
                    onClick = { click ->
                        when (click) {
                            OTPClicks.BackToSignIn -> {
                                navController.navigate(Screens.Auth.Login)
                            }

                            OTPClicks.OTPConfirm -> {
                                navController
                                    .previousBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("isOTPVerified", true)
                                navController.popBackStack()
                            }

                            OTPClicks.PasswordResetConfirm -> {
                                navController.navigate(Screens.Auth.Login) {
                                    popUpTo(Screens.Auth.ForgotPassword) { inclusive = true }
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

            composable<Screens.Main.QrResult>(
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
                val args = it.toRoute<Screens.Main.QrResult>()
                QrCodeResultScreen(qrCodeContent = args.qrContent,navController = navController)
            }

            composable<Screens.Main.TrackingMap>{
                val args = it.toRoute<Screens.Main.TrackingMap>()
                TrackingMapScreen(bikeId = args.bikeId,navController)
            }

            composable<Screens.Main.Feedback> {
                val args = it.toRoute<Screens.Main.Feedback>()
                ReturnBikeScreen(totalMinutes =args.totalMinutes, bikeId = args.bikeId,navController)
            }
        }
    }
}


