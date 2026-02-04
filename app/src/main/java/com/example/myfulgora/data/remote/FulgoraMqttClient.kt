package com.example.myfulgora.data.remote

import android.util.Log
import com.example.myfulgora.data.model.BikeState
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.nio.charset.StandardCharsets
import java.util.UUID

object FulgoraMqttClient {

    private const val TAG = "FulgoraMqtt"

    // Configurações do Broker (Para testar em casa usamos o público da HiveMQ)
    private const val BROKER_HOST = "broker.hivemq.com"
    private const val BROKER_PORT = 1883

    // O Cliente MQTT real
    private var client: Mqtt3AsyncClient? = null

    // O Estado da Mota (Observável) - É aqui que a UI vai "beber" os dados
    private val _bikeState = MutableStateFlow(BikeState())
    val bikeState: StateFlow<BikeState> = _bikeState.asStateFlow()

    fun connect() {
        if (client != null && client!!.state.isConnected) return

        Log.d(TAG, "A tentar conectar ao broker $BROKER_HOST...")

        client = MqttClient.builder()
            .useMqttVersion3()
            .identifier("android-app-${UUID.randomUUID()}")
            .serverHost(BROKER_HOST)
            .serverPort(BROKER_PORT)
            .buildAsync()

        client?.connectWith()
            ?.send()
            ?.whenComplete { _, throwable ->
                if (throwable != null) {
                    Log.e(TAG, "Erro ao conectar: ${throwable.message}")
                } else {
                    Log.i(TAG, "Conectado com sucesso!")
                    subscribeToTopics()
                }
            }
    }

    private fun subscribeToTopics() {
        // Lista de tópicos para ouvir
        val topics = listOf(
            "moto/speed",
            "moto/battery",
            "moto/gear",
            "moto/range"
        )

        topics.forEach { topic ->
            client?.subscribeWith()
                ?.topicFilter(topic)
                ?.callback { publish -> handleMessage(publish) }
                ?.send()
                ?.whenComplete { _, error ->
                    if (error == null) Log.d(TAG, "Subscrito: $topic")
                }
        }
    }

    private fun handleMessage(publish: Mqtt3Publish) {
        val topic = publish.topic.toString()
        val payload = String(publish.payloadAsBytes, StandardCharsets.UTF_8)

        Log.d(TAG, "Recebido [$topic]: $payload")

        // Atualiza o estado da mota com base na mensagem recebida
        _bikeState.update { currentState ->
            try {
                when (topic) {
                    "moto/speed" -> currentState.copy(speed = payload.toIntOrNull() ?: 0)
                    "moto/battery" -> currentState.copy(batteryPercentage = payload.toIntOrNull() ?: 0)
                    "moto/range" -> currentState.copy(range = payload.toIntOrNull() ?: 0)
                    "moto/gear" -> currentState.copy(gear = payload)
                    else -> currentState
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao processar dados: ${e.message}")
                currentState
            }
        }
    }

    fun disconnect() {
        client?.disconnect()
    }
}