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

    var phoneNumberError by mutableStateOf<String?>(null)

    var passwordError by mutableStateOf<String?>(null)

    fun updatePhoneNumber(value: String) {
        phoneNumber = value
        validatePhoneNumber()
    }

    fun updatePassword(value: String) {
        password = value
        validatePassword()
    }

    private fun validatePhoneNumber() {
        val error = when {
            phoneNumber.isEmpty() -> "Tài khoản không được để trống"
            !phoneNumber.matches(Regex("^0[1-9][0-9]{8}\$")) -> "Tài khoản không hợp lệ"
            else -> null
        }

        phoneNumberError = error
    }

    private fun validatePassword() {
        val error = when {
            password.isEmpty() -> "Mật khẩu không được để trống"
            password.length < 6 -> "Mật khẩu phải có ít nhất 6 ký tự"
            else -> null
        }

        passwordError = error
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