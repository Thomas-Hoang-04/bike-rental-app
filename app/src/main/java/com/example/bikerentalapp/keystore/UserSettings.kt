package com.example.bikerentalapp.keystore

import kotlinx.serialization.Serializable
import androidx.datastore.core.Serializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class UserSettings(
    val username: String? = null,
    val password: String? = null,
)

class UserSettingsSerializer(
    private val cryptoManager: CryptoManager,
): Serializer<UserSettings> {
    override val defaultValue: UserSettings = UserSettings()

    override suspend fun readFrom(input: InputStream): UserSettings {
        return try {
            val decryptedData = cryptoManager.decrypt(input.readBytes().decodeToString())
            Json.decodeFromString(
                deserializer = UserSettings.serializer(),
                string = decryptedData
            )
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserSettings, output: OutputStream) {
        cryptoManager.encrypt(Json.encodeToString(UserSettings.serializer(), t))
            .also { output.write(it.toByteArray()) }
    }
}