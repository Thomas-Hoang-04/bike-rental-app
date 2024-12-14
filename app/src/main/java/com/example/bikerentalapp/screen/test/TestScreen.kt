package com.example.bikerentalapp.screen.test

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import com.example.bikerentalapp.keystore.CryptoManager
import com.example.bikerentalapp.keystore.UserSettings
import com.example.bikerentalapp.ui.theme.BikeRentalAppTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun TestScreen(
    dataStore: DataStore<UserSettings>,
    ref: UserSettings
) {
    val cryptoManager = CryptoManager()

    BikeRentalAppTheme {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var details by remember { mutableStateOf(ref) }
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            TextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Username") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Password") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(onClick = {
                    Log.d("TestScreen", "Saving data")
                    scope.launch {
                        dataStore.updateData {
                            UserSettings(username, password)
                        }
                    }
                },
                    enabled = username.isNotEmpty() && password.isNotEmpty()
                ) {
                    Text("Save")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = {
                    scope.launch {
                        details = dataStore.data.first()
                    }
                }) {
                    Text("Load")
                }
            }
            Text(text = details.toString())
        }
    }
}