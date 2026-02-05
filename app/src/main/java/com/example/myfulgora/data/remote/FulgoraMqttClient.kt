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

    //CSVU
    //private const val BROKER_HOST = "172.20.0.203"
    //private const val BROKER_PORT = 1884

    // O Cliente MQTT real
    private var client: Mqtt3AsyncClient? = null

    // O Estado da Mota (Observável) - É aqui que a UI vai "beber" os dados
    private val _bikeState = MutableStateFlow(BikeState())
    val bikeState: StateFlow<BikeState> = _bikeState.asStateFlow()

    fun connect() {
        // Se já estiver ligado ou a ligar, não faz nada
        if (client != null && client!!.state.isConnected) return

        Log.d(TAG, "A configurar cliente MQTT...")

        client = MqttClient.builder()
            .useMqttVersion3()
            .identifier("android-app-${UUID.randomUUID()}")
            .serverHost(BROKER_HOST)
            .serverPort(BROKER_PORT)

            // 👇 1. RECONEXÃO AUTOMÁTICA
            // Se a net falhar, ele tenta reconectar sozinho indefinidamente
            .automaticReconnectWithDefaultConfig()

            // 👇 2. LISTENER: QUANDO CONECTA
            .addConnectedListener {
                Log.i(TAG, "✅ CONECTADO AO BROKER!")
                // Atualiza o estado para ONLINE (Verde)
                _bikeState.update { it.copy(isOnline = true) }
                // Volta a subscrever os tópicos (por segurança)
                subscribeToTopics()
            }

            // 👇 3. LISTENER: QUANDO DESCONECTA
            .addDisconnectedListener {
                Log.w(TAG, "❌ DESCONECTADO DO BROKER!")
                // Atualiza o estado para OFFLINE (Vermelho)
                _bikeState.update { it.copy(isOnline = false) }
            }
            .buildAsync()

        // Inicia a conexão
        client?.connect()
    }

    private fun subscribeToTopics() {
        // Lista de tópicos para ouvir
        val topics = listOf(
            "moto/speed",
            "moto/battery",
            "moto/gear",
            "moto/range",
            "moto/status",
            "moto/charging",
            "moto/cycles",
            "moto/temperature",
            "moto/health",
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

        //Log.d(TAG, "Recebido [$topic]: $payload")

        // Atualiza o estado da mota com base na mensagem recebida
        _bikeState.update { currentState ->
            try {
                when (topic) {
                    "moto/speed" -> currentState.copy(speed = payload.toIntOrNull() ?: 0)
                    "moto/battery" -> currentState.copy(batteryPercentage = payload.toIntOrNull() ?: 0)
                    "moto/range" -> currentState.copy(range = payload.toIntOrNull() ?: 0)
                    "moto/status" -> currentState.copy(isOnline = payload.toBoolean())
                    //"moto/charging" -> currentState.copy(isCharging = payload == "true")
                    "moto/cycles" -> currentState.copy(batteryCycles = payload.toIntOrNull() ?: 0)
                    "moto/temperature" -> currentState.copy(batteryTemp = payload.toIntOrNull() ?: 0)
                    "moto/health" -> currentState.copy(batteryHealth = payload)
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