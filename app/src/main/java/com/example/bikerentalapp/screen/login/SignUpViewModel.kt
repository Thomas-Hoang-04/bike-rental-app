package com.example.bikerentalapp.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    var phoneNumber by mutableStateOf("")
        private set
    var name by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var dateOfBirth by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var confirmPassword by mutableStateOf("")
        private set
    var referralCode by mutableStateOf("")
        private set
    var isTermAccepted by mutableStateOf(false)
        private set


    var phoneNumberError by mutableStateOf<String?>(null)
        private set
    var nameError by mutableStateOf<String?>(null)
        private set
    var dateOfBirthError by mutableStateOf<String?>(null)
        private set
    var passwordError by mutableStateOf<String?>(null)
        private set
    var confirmPasswordError by mutableStateOf<String?>(null)
        private set

    fun updatePhoneNumber(value: String) {
        phoneNumber = value
        validatePhoneNumber()
    }

    fun updateName(value: String) {
        name = value
        validateName()
    }

    fun updateEmail(value: String) {
        email = value
    }

    fun updateDateOfBirth(value: String) {
        dateOfBirth = value
        validateDateOfBirth()
    }

    fun updatePassword(value: String) {
        password = value
        validatePassword()
    }

    fun updateConfirmPassword(value: String) {
        confirmPassword = value
        validateConfirmPassword()
    }

    fun updateReferralCode(value: String) {
        referralCode = value
    }

    fun updateTermAccepted(value: Boolean) {
        isTermAccepted = value
    }

    private fun validatePhoneNumber() {
        phoneNumberError = when {
            phoneNumber.isEmpty() -> "Số điện thoại không được để trống"
            !phoneNumber.matches(Regex("^[0-9]{10}$")) -> "Số điện thoại không hợp lệ"
            else -> null
        }
    }

    private fun validateName() {
        nameError = when {
            name.isEmpty() -> "Tên không được để trống"
            name.length < 2 -> "Tên phải có ít nhất 2 ký tự"
            else -> null
        }
    }

    private fun validateDateOfBirth() {
        dateOfBirthError = when {
            dateOfBirth.isEmpty() -> "Ngày sinh không được để trống"
            !dateOfBirth.matches(Regex("^\\d{2}/\\d{2}/\\d{4}$")) -> "Ngày sinh không hợp lệ"
            else -> null
        }
    }

    private fun validatePassword() {
        passwordError = when {
            password.isEmpty() -> "Mật khẩu không được để trống"
            password.length < 6 -> "Mật khẩu phải có ít nhất 6 ký tự"
            else -> null
        }
        validateConfirmPassword()
    }

    private fun validateConfirmPassword() {
        confirmPasswordError = when {
            confirmPassword.isEmpty() -> "Vui lòng nhập lại mật khẩu"
            confirmPassword != password -> "Mật khẩu không khớp"
            else -> null
        }
    }

    private fun validateAll(): Boolean {
        validatePhoneNumber()
        validateName()
        validateDateOfBirth()
        validatePassword()
        validateConfirmPassword()

        return phoneNumberError == null &&
                nameError == null &&
                dateOfBirthError == null &&
                passwordError == null &&
                confirmPasswordError == null &&
                isTermAccepted
    }

    fun signUp(onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (validateAll()) {
                onSuccess()
            }
        }
    }
}