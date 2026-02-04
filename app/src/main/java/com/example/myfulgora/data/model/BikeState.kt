package com.example.myfulgora.data.model

data class BikeState(
    val speed: Int = 0,
    val batteryPercentage: Int = 0,
    val range: Int = 0,
    val gear: String = "N", // P, N, D, R
    val isLocked: Boolean = true,
    val isCharging: Boolean = false,

    // Luzes e Sinais
    val leftTurnSignal: Boolean = false,
    val rightTurnSignal: Boolean = false,
    val highBeam: Boolean = false,

    // Mensagens de erro/aviso
    val warningMessage: String? = null
)