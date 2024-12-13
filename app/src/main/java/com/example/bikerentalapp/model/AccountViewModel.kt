package com.example.bikerentalapp.model

import androidx.lifecycle.ViewModel
import com.example.bikerentalapp.api.data.UserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AccountViewModel: ViewModel() {
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    private val _details = MutableStateFlow<UserDetails?>(null)
    val details = _details.asStateFlow()

    fun setUsername(username: String) {
        _username.value = username
    }

    fun setDetails(details: UserDetails) {
        _details.value = details
    }

    fun setBalance(balance: Int) {
        _details.update {
            it?.copy(balance = balance)
        }
    }

    fun setToken(token: String) {
        _token.value = token
    }
}