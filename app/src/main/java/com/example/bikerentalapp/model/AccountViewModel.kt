package com.example.bikerentalapp.model

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikerentalapp.api.data.LoginRequest
import com.example.bikerentalapp.api.data.LoginResponse
import com.example.bikerentalapp.api.data.QueryResponse
import com.example.bikerentalapp.api.data.User
import com.example.bikerentalapp.api.data.UserDetails
import com.example.bikerentalapp.api.network.RetrofitInstances
import com.example.bikerentalapp.keystore.UserSettings
import com.example.bikerentalapp.navigation.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Suppress("unused")
class AccountViewModel: ViewModel() {
    private val _startDestination = MutableStateFlow<Any>(Screens.Auth)
    val startDestination = _startDestination.asStateFlow()

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

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
        _details.update { details }
    }

    fun setBalance(balance: Int) {
        _details.update {
            it?.copy(balance = balance)
        }
    }

    fun setToken(token: String) {
        _token.value = token
    }

    fun update() {
        val retrofit = RetrofitInstances.Query(_token.value).queryAPI
        viewModelScope.launch {
            val req = retrofit.getUser()
            if (req.isSuccessful) {
                val body = req.body() as QueryResponse<*, *>?
                val user = body?.data?.get(0) as User
                _username.value = user.username
                _details.value = user.details
            }
        }
    }

    fun store(dataStore: DataStore<UserSettings>, data: LoginResponse, pwd: String) {
        viewModelScope.launch {
            store(data)
            dataStore.updateData { settings ->
                settings.copy(
                    username = data.targetUser.username,
                    password = pwd
                )
            }
        }
    }

    fun store(data: LoginResponse) {
        _username.value = data.targetUser.username
        _token.value = data.accessToken
        _details.value = data.targetUser.details
    }

    fun load(dataStore: DataStore<UserSettings>) {
        viewModelScope.launch {
            val account = dataStore.data.catch { e ->
                e.printStackTrace()
                emit(UserSettings())
            }.first()
            if (account.username == null || account.password == null) {
                delay(100)
                _isReady.value = true
            } else {
                val req = RetrofitInstances.Auth.authAPI.login(
                    LoginRequest(
                        account.username,
                        account.password
                    )
                )
                if (req.isSuccessful) {
                    val body = req.body() as LoginResponse
                    _token.value = body.accessToken
                    _username.value = body.targetUser.username
                    _details.value = body.targetUser.details
                    _startDestination.value = Screens.Main
                    delay(100)
                    _isReady.value = true
                } else {
                    delay(100)
                    _isReady.value = true
                }
            }
        }
    }
}