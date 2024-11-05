package com.example.bikerentalapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikerentalapp.components.FocusField
import kotlinx.coroutines.launch
import java.util.Calendar

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

    private var focusedField by mutableStateOf<FocusField?>(null)

    fun updateFocusField(field: FocusField) {
        focusedField = field
        when (field) {
            FocusField.PHONE_NUMBER -> validatePhoneNumber(true)
            FocusField.NAME -> validateName(true)
            FocusField.DATE_OF_BIRTH -> validateDateOfBirth(true)
            FocusField.PASSWORD -> validatePassword(true)
            FocusField.CONFIRM_PASSWORD -> validateConfirmPassword(true)
            else -> {}
        }
    }

    fun updatePhoneNumber(value: String) {
        phoneNumber = value
        if (focusedField == FocusField.PHONE_NUMBER) {
            validatePhoneNumber(true)
        }
    }

    fun updateName(value: String) {
        name = value
        if (focusedField == FocusField.NAME) {
            validateName(true)
        }
    }

    fun updateEmail(value: String) {
        email = value
    }

    fun updateDateOfBirth(value: String) {
        dateOfBirth = value
        if (focusedField == FocusField.DATE_OF_BIRTH) {
            validateDateOfBirth(true)
        }
    }

    fun updatePassword(value: String) {
        password = value
        if (focusedField == FocusField.PASSWORD) {
            validatePassword(true)
        }
        if (focusedField == FocusField.CONFIRM_PASSWORD && confirmPassword.isNotEmpty()) {
            validateConfirmPassword(true)
        }
    }

    fun updateConfirmPassword(value: String) {
        confirmPassword = value
        if (focusedField == FocusField.CONFIRM_PASSWORD) {
            validateConfirmPassword(true)
        }
    }

    fun updateReferralCode(value: String) {
        referralCode = value
    }

    fun updateTermAccepted(value: Boolean) {
        isTermAccepted = value
    }

    private fun validatePhoneNumber(showError: Boolean = false): Boolean {
        val error = when {
            phoneNumber.isEmpty() -> "Số điện thoại không được để trống"
            !phoneNumber.matches(Regex("^[0-9]{10}$")) -> "Số điện thoại không hợp lệ"
            else -> null
        }

        if (showError) {
            phoneNumberError = error
        }
        return error == null
    }


    private fun validateName(showError: Boolean = false): Boolean {
        val error = when {
            name.isEmpty() -> "Tên không được để trống"
            name.length < 2 -> "Tên phải có ít nhất 2 ký tự"
            else -> null
        }

        if (showError) {
            nameError = error
        }
        return error == null
    }


    private fun validateDateOfBirth(showError: Boolean = false): Boolean {
        val error = when {
            dateOfBirth.isEmpty() -> "Ngày sinh không được để trống"
            !isValidDate(dateOfBirth) -> "Ngày sinh không hợp lệ"
            else -> null
        }

        if (showError) {
            dateOfBirthError = error
        }
        return error == null
    }

    private fun isValidDate(date: String): Boolean {
        return try {
            val parts = date.split("/")
            if (parts.size != 3) return false
            val day = parts[0].toInt()
            val month = parts[1].toInt()
            val year = parts[2].toInt()

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month - 1)
            calendar.set(Calendar.DAY_OF_MONTH, day)

            true
        } catch (e: Exception) {
            false
        }
    }

    private fun validatePassword(showError: Boolean = false): Boolean {
        val error = when {
            password.isEmpty() -> "Mật khẩu không được để trống"
            password.length < 6 -> "Mật khẩu phải có ít nhất 6 ký tự"
            else -> null
        }

        if (showError) {
            passwordError = error
        }
        return error == null
    }

    private fun validateConfirmPassword(showError: Boolean = false): Boolean {
        val error = when {
            confirmPassword.isEmpty() -> "Vui lòng nhập lại mật khẩu"
            confirmPassword != password -> "Mật khẩu không khớp"
            else -> null
        }

        if (showError) {
            confirmPasswordError = error
        }
        return error == null
    }

    fun validateAll(): Boolean {
        val isPhoneValid = validatePhoneNumber(false)
        val isNameValid = validateName(false)
        val isDateValid = validateDateOfBirth(false)
        val isPasswordValid = validatePassword(false)
        val isConfirmPasswordValid = validateConfirmPassword(false)

        return isPhoneValid &&
                isNameValid &&
                isDateValid &&
                isPasswordValid &&
                isConfirmPasswordValid &&
                isTermAccepted
    }

    fun signUp(onSuccess: () -> Unit) {
        viewModelScope.launch {
            validatePhoneNumber(true)
            validateName(true)
            validateDateOfBirth(true)
            validatePassword(true)
            validateConfirmPassword(true)

            if (validateAll()) {
                onSuccess()
            }
        }
    }
}