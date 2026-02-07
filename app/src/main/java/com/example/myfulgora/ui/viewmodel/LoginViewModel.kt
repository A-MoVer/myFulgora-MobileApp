package com.example.myfulgora.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfulgora.data.auth.AuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Estados possíveis do ecrã de login
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

// 👇 Mudámos para AndroidViewModel para poder usar o 'getApplication()' para o AuthManager
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow()

    // 👇 AQUI ESTÁ A CORREÇÃO: Adicionámos (user, pass) aos argumentos
    fun fazerLogin(user: String, pass: String) {

        // Validação básica antes de tentar a internet
        if (user.isBlank() || pass.isBlank()) {
            _loginState.value = LoginState.Error("Por favor preenche todos os campos.")
            return
        }

        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            // Instanciar o AuthManager usando o contexto da aplicação
            val authManager = AuthManager(getApplication())

            // Tentar login real
            val sucesso = authManager.loginDireto(user, pass)

            if (sucesso) {
                _loginState.value = LoginState.Success
            } else {
                _loginState.value = LoginState.Error("Login falhou. Verifica os dados.")
            }
        }
    }
}