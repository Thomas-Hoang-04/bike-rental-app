package com.example.bikerentalapp.screen.test

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bikerentalapp.keystore.CryptoManager
import com.example.bikerentalapp.ui.theme.BikeRentalAppTheme
import java.io.File

@Composable
fun TestScreen(context: Context) {
    val cryptoManager = CryptoManager()

    BikeRentalAppTheme {
        var messageToEncrypt by remember { mutableStateOf("") }
        var encryptedMessage by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            TextField(
                value = messageToEncrypt,
                onValueChange = { messageToEncrypt = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Message to encrypt") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(onClick = {
                    val file = File(context.filesDir, "secret.txt")
                    if (!file.exists()) {
                        file.createNewFile()
                    }
                    val data = cryptoManager.encrypt(messageToEncrypt)
                    file.writeText(data)
                    encryptedMessage = data
                }) {
                    Text("Encrypt")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = {
                    val file = File(context.filesDir, "secret.txt")
                    val data = file.readText()
                    messageToEncrypt = cryptoManager.decrypt(data)
                }) {
                    Text("Decrypt")
                }
            }
            Text(text = encryptedMessage)
        }
    }
}