package com.example.myfulgora.data.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfulgora.data.remote.FulgoraMqttClient
import com.example.myfulgora.data.model.BikeState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // A UI vai ouvir esta variável
    val bikeState: StateFlow<BikeState> = FulgoraMqttClient.bikeState

    init {
        // Assim que a App abre, liga ao MQTT
        connectToMqtt()
    }

    private fun connectToMqtt() {
        viewModelScope.launch {
            FulgoraMqttClient.connect()
        }
    }
}