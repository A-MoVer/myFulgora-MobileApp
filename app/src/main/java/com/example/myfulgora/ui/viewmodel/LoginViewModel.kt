package com.example.myfulgora.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfulgora.data.auth.AuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val authManager = AuthManager(application)

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow()

    // Variáveis para guardar o texto dos campos
    var username = MutableStateFlow("")
    var password = MutableStateFlow("")

    fun fazerLogin() {
        println("DEBUG: Username digitado: ${username.value}")
        println("DEBUG: Password digitada: ${password.value}")
        val user = username.value
        val pass = password.value

        if (user.isBlank() || pass.isBlank()) {
            _loginState.value = LoginState.Error("Preenche todos os campos")
            return
        }

        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            val sucesso = authManager.loginDireto(user, pass)

            if (sucesso) {
                _loginState.value = LoginState.Success
            } else {
                _loginState.value = LoginState.Error("Login falhou. Verifica as credenciais.")
            }
        }
    }
}