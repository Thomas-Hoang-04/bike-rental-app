package com.example.bikerentalapp.navigation


import com.example.bikerentalapp.api.data.OTPPurpose
import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {
    @Serializable
    object Auth {
        @Serializable
        object Login

        @Serializable
        object SignUp

        @Serializable
        object ForgotPassword

        @Serializable
        object TermsOfUse

        @Serializable
        object PrivacyPolicy

        @Serializable
        data class OTPConfirm(
            val phoneNumber: String,
            val purpose: OTPPurpose
        )

        @Serializable
        data class ResetPassword(
            val username: String
        )
    }

    @Serializable
    object Main {
        @Serializable
        object Home

        @Serializable
        object Station

        @Serializable
        object Notification

        @Serializable
        object Profile

        @Serializable
        object QRCode

        @Serializable
        data class QrResult(val qrContent : String)

        @Serializable
        data class TrackingMap(val bikeId : String,val battery : String)

        @Serializable
        data class Feedback(val totalMinutes : Int, val bikeId: String,val fee : Int,val tripId : String)

        @Serializable
        object Settings
    }

    @Serializable
    object Features {
        @Serializable
        object BuyingTickets

        @Serializable
        object MyTrips

        @Serializable
        data class MyTripsDetails(
            val polyPoints: String,
            val time: String,
            val id: String,
            val distance: String,
            val duration: String,
        )

        @Serializable
        object News

        @Serializable
        object Ranking

        @Serializable
        object ManualUser

        @Serializable
        object InviteFriends

        @Serializable
        object Promotion

        @Serializable
        object PointSharing

        @Serializable
        object TopUp

        @Serializable
        object TransactionHistory

        @Serializable
        object ProfileSettingScreen
    }
}
