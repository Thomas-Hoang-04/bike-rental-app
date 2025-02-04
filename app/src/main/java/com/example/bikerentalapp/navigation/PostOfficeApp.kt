package com.example.bikerentalapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bikerentalapp.components.LocalNavigation
import com.example.bikerentalapp.components.UserAccount
import com.example.bikerentalapp.model.SignUpViewModel
import com.example.bikerentalapp.screen.features.CreditDepositScreen
import com.example.bikerentalapp.screen.features.InviteFriendsScreen
import com.example.bikerentalapp.screen.features.ManualUserScreen
import com.example.bikerentalapp.screen.features.NewsScreen
import com.example.bikerentalapp.screen.features.PointSharingScreen
import com.example.bikerentalapp.screen.features.ProfileSettingScreen
import com.example.bikerentalapp.screen.features.PromotionScreen
import com.example.bikerentalapp.screen.features.RankingScreen
import com.example.bikerentalapp.screen.features.TicketScreen
import com.example.bikerentalapp.screen.features.TransactionHistoryScreen
import com.example.bikerentalapp.screen.features.my_trips.MyTripsScreen
import com.example.bikerentalapp.screen.features.my_trips.SerializableLatLng
import com.example.bikerentalapp.screen.features.my_trips.TripsMap
import com.example.bikerentalapp.screen.features.my_trips.toLatLng
import com.example.bikerentalapp.screen.login.ForgotPassword
import com.example.bikerentalapp.screen.login.OTPScreen
import com.example.bikerentalapp.screen.login.ResetPasswordScreen
import com.example.bikerentalapp.screen.login.SignInScreen
import com.example.bikerentalapp.screen.login.SignUpScreen
import com.example.bikerentalapp.screen.main.HomeScreen
import com.example.bikerentalapp.screen.main.MainScreen
import com.example.bikerentalapp.screen.main.NotificationScreen
import com.example.bikerentalapp.screen.main.ProfileScreen
import com.example.bikerentalapp.screen.main.SettingsScreen
import com.example.bikerentalapp.screen.main.qrcode.QrCodeResultScreen
import com.example.bikerentalapp.screen.main.qrcode.QrScreen
import com.example.bikerentalapp.screen.main.qrcode.ReturnBikeScreen
import com.example.bikerentalapp.screen.main.qrcode.TrackingMapScreen
import com.example.bikerentalapp.screen.main.station.StationsScreen
import com.example.bikerentalapp.screen.policy.PrivacyPolicy
import com.example.bikerentalapp.screen.policy.TermsOfUse
import kotlinx.serialization.json.Json

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

                verticalNavigation<Screens.Main.QRCode> {
                    QrScreen()
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

                horizontalNavigation<Screens.Features.Ranking> {
                    RankingScreen()
                }

                horizontalNavigation<Screens.Features.Promotion> {
                    PromotionScreen()
                }

                horizontalNavigation<Screens.Features.InviteFriends> {
                    InviteFriendsScreen()
                }

                horizontalNavigation<Screens.Features.ManualUser> {
                    ManualUserScreen()
                }

                horizontalNavigation<Screens.Features.BuyingTickets> {
                    TicketScreen()
                }

                horizontalNavigation<Screens.Features.ProfileSettingScreen> {
                    ProfileSettingScreen()
                }
            }

            horizontalNavigation<Screens.Main.QrResult> {
                val args = it.toRoute<Screens.Main.QrResult>()
                QrCodeResultScreen(qrCodeContent = args.qrContent,navController = navController)
            }

            composable<Screens.Main.TrackingMap>{
                val args = it.toRoute<Screens.Main.TrackingMap>()
                TrackingMapScreen(qrCodeContent = args.bikeId, battery = args.battery,navController)
            }

            composable<Screens.Main.Feedback> {
                val args = it.toRoute<Screens.Main.Feedback>()
                ReturnBikeScreen(totalMinutes = args.totalMinutes, bikeId = args.bikeId, navController, args.fee, args.tripId)
            }

            composable<Screens.Features.MyTrips> {
                MyTripsScreen(navController)
            }

            horizontalNavigation<Screens.Features.MyTripsDetails> {
                val args = it.toRoute<Screens.Features.MyTripsDetails>()

                val polylinePointsJson = args.polyPoints

                // Decode polylinePoints
                val polylinePoints = polylinePointsJson.let { json ->
                    Json.decodeFromString<List<SerializableLatLng>>(json)
                }

                TripsMap(polylinePoints.map { it.toLatLng() }, args.time, args.id, args.distance, args.duration)
            }
        }
    }
}