package com.example.bikerentalapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import com.example.bikerentalapp.api.data.OTPPurpose
import com.example.bikerentalapp.model.AccountViewModel
import com.example.bikerentalapp.components.UserAccount
import com.example.bikerentalapp.components.LocalNavigation
import com.example.bikerentalapp.model.SignUpViewModel
import com.example.bikerentalapp.screen.features.*
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
import com.example.bikerentalapp.screen.main.*
import com.example.bikerentalapp.screen.main.station.StationsScreen

import com.example.bikerentalapp.screen.policy.PrivacyPolicy
import com.example.bikerentalapp.screen.policy.TermsOfUse



@Composable
fun PostOfficeApp() {
    val navController = rememberNavController()
    val account = UserAccount.current
    val startDestination = account.startDestination.collectAsState().value

    CompositionLocalProvider(
        LocalNavigation provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
        ) {
            navigation<Screens.Auth>(
                startDestination = Screens.Auth.Login
            ) {
                val signUpViewModel = SignUpViewModel()

                composable<Screens.Auth.Login> {
                    SignInScreen()
                }

                horizontalNavigation<Screens.Auth.SignUp> { backStack ->
                    val isOTPVerified = backStack
                        .savedStateHandle
                        .getStateFlow("isOTPVerified", false)
                        .collectAsState()
                    SignUpScreen(
                        isOTPVerified.value,
                        signUpViewModel,
                    )
                }

                verticalNavigation<Screens.Auth.ForgotPassword> {
                    ForgotPassword()
                }

                horizontalNavigation<Screens.Auth.TermsOfUse> {
                    TermsOfUse(
                        onBackClick = { navController.navigateUp() }
                    )
                }

                horizontalNavigation<Screens.Auth.PrivacyPolicy>{
                    PrivacyPolicy(
                        onBackClick = { navController.navigateUp() }
                    )
                }

                horizontalNavigation<Screens.Auth.OTPConfirm> {
                    val args = it.toRoute<Screens.Auth.OTPConfirm>()
                    OTPScreen(
                        phoneNumber = args.phoneNumber,
                        purpose = args.purpose,
                    )
                }

                horizontalNavigation<Screens.Auth.ResetPassword> {
                    val args = it.toRoute<Screens.Auth.ResetPassword>()
                    ResetPasswordScreen(args.username)
                }
            }



            navigation<Screens.Main>(
                startDestination = Screens.Main.Home
            ) {
                composable<Screens.Main.Home> {
                    MainScreen {
                        HomeScreen(paddingValues = it)
                    }
                }

                composable<Screens.Main.Station> {
                    MainScreen {
                        StationsScreen()
                    }
                }

                composable<Screens.Main.Notification> {
                    MainScreen {
                        NotificationScreen()
                    }
                }

                composable<Screens.Main.Settings> {
                    MainScreen {
                        SettingsScreen()
                    }
                }

                composable<Screens.Main.QRCode> {
                    MainScreen {
                        QrScreen()
                    }
                }

                horizontalNavigation<Screens.Features.TopUp> {
                    CreditDepositScreen()
                }

                horizontalNavigation<Screens.Features.TransactionHistory> {
                    TransactionHistoryScreen()
                }

                horizontalNavigation<Screens.Features.PointSharing> {
                    PointSharingScreen()
                }

                horizontalNavigation<Screens.Main.Profile> {
                    ProfileScreen()
                }

                horizontalNavigation<Screens.Features.News> {
                    NewsScreen()
                }

                horizontalNavigation<Screens.Features.Promotion> {
                    NewsScreen()
                }

                horizontalNavigation<Screens.Features.Ranking> {
                    NewsScreen()
                }

                horizontalNavigation<Screens.Features.Promotion> {
                    NewsScreen()
                }

                horizontalNavigation<Screens.Features.InviteFriends> {
                    NewsScreen()
                }

                horizontalNavigation<Screens.Features.ManualUser> {
                    NewsScreen()
                }

                horizontalNavigation<Screens.Features.BuyingTickets> {
                    NewsScreen()
                }

                horizontalNavigation<Screens.Features.MyTrips> {
                    NewsScreen()
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


