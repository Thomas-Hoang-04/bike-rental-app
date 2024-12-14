package com.example.bikerentalapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.dataStore
import com.example.bikerentalapp.components.LocalDataStore
import com.example.bikerentalapp.components.UserAccount
import com.example.bikerentalapp.keystore.CryptoManager
import com.example.bikerentalapp.keystore.UserSettingsSerializer
import com.example.bikerentalapp.model.AccountViewModel
import com.example.bikerentalapp.navigation.PostOfficeApp


class MainActivity : ComponentActivity() {
    private val Context.dataStore by dataStore(
        fileName = "bike_rental_app_settings.json",
        serializer = UserSettingsSerializer(CryptoManager())
    )

    private val account by viewModels<AccountViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = 0xFFFFFFFF.toInt(),
                darkScrim = 0xFF000000.toInt()
            )
        )
        account.load(dataStore)
        installSplashScreen().setKeepOnScreenCondition {
            !account.isReady.value
        }
        setContent {
            CompositionLocalProvider(
                UserAccount provides account,
                LocalDataStore provides dataStore
            ) {
                PostOfficeApp()
            }
        }
    }
}

