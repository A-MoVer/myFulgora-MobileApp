package com.example.myfulgora.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfulgora.data.remote.FulgoraMqttClient
import com.example.myfulgora.data.model.BikeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// Vamos simplificar o estado para refletir o que vem do MQTT (BikeState)
sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val bikeState: BikeState) : HomeUiState()
    // Removemos a lista de motas por agora para focar em receber dados da primeira
}

class MotaViewModel : ViewModel() {

    // UI State que a Home vai ler
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        // 1. Assim que o ViewModel nasce, conecta ao MQTT
        conectarMqtt()

        // 2. Começa a escutar os dados
        escutarDadosDaMota()
    }

    private fun conectarMqtt() {
        // Chamamos o objeto diretamente (sem parênteses)
        FulgoraMqttClient.connect()
    }

    private fun escutarDadosDaMota() {
        viewModelScope.launch {
            // Fica a "observar" o fluxo de dados que vem do cliente MQTT
            FulgoraMqttClient.bikeState.collectLatest { novoEstado ->

                // Sempre que chegar um dado novo (do Python), atualizamos a UI
                _uiState.value = HomeUiState.Success(novoEstado)
            }
        }
    }
}