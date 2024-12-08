package com.example.bikerentalapp.navigation

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
        data class OTPConfirm(val phoneNumber: String)
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
        data class TrackingMap(val bikeId : String)
        @Serializable
        data class Feedback(val totalMinutes : Int, val bikeId: String)
    }
}