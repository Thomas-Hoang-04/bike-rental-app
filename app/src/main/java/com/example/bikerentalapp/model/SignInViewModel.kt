package com.example.bikerentalapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    var phoneNumber by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var phoneNumberError by mutableStateOf<String?>(null)
        private set
    var passwordError by mutableStateOf<String?>(null)
        private set

    fun updatePhoneNumber(value: String) {
        phoneNumber = value
        validatePhoneNumber()
    }

    fun updatePassword(value: String) {
        password = value
        validatePassword()
    }

    fun showError(message: String) {
        error = message
    }

    private fun validatePhoneNumber() {
        // TODO: Check whether the phone number is valid
    }

    private fun validatePassword() {
        // TODO: Check whether the password is valid
    }

    private fun validateAll(): Boolean {
        validatePhoneNumber()
        validatePassword()

        return phoneNumberError == null && passwordError == null
    }

    @Suppress("unused")
    fun signIn(onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (validateAll()) {
                onSuccess()
            }
        }
    }
}