package com.example.myfulgora.ui.screens.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Autorenew
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfulgora.data.model.BikeState
import com.example.myfulgora.ui.components.FulgoraBackground
import com.example.myfulgora.ui.components.FulgoraTopBar
import com.example.myfulgora.ui.theme.AppIcons
import com.example.myfulgora.ui.theme.CardBackgroundColor
import com.example.myfulgora.ui.theme.Dimens
import com.example.myfulgora.ui.theme.GreenFresh
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.myfulgora.R

// 👇 E este para o 'delay' funcionar
import kotlinx.coroutines.delay

@Composable
fun BatteryScreen(
    state: BikeState,
    onMenuClick: () -> Unit = {}
) {
    FulgoraBackground {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

            val screenW = maxWidth
            val iconSize = screenW * Dimens.IconScaleRatio
            val paddingSide = screenW * Dimens.SideMarginRatio

            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = paddingSide)
                    .padding(top = Dimens.TopPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // 1. CABEÇALHO PARTILHADO
                FulgoraTopBar(
                    title = "Hi, Alex!",
                    subtitle = "Ready to ride?",
                    iconSize = iconSize,
                    onMenuClick = onMenuClick
                )

                Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge))

                // 2. TÍTULO
                Text(
                    text = stringResource(id = R.string.battery_title),
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    fontSize = Dimens.TextSizeHeader,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge))

                // 3. BATERIA GRANDE + INFO
                Row(
                    modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 👇 Passamos o STATE aqui
                    BigBatteryIndicator(
                        state = state,
                        modifier = Modifier.weight(0.35f).fillMaxHeight()
                    )

                    Spacer(modifier = Modifier.width(Dimens.PaddingMedium))

                    Box(modifier = Modifier.weight(0.65f)) {
                        // 👇 Passamos o STATE aqui também
                        BatteryInfoCard(state = state)
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                // 4. GRELHA DE ESTATÍSTICAS
                Column(verticalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)) {
                    // Linha 1
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
                    ) {
                        BatteryStatCard(
                            icon = AppIcons.Battery.BatteryHealth,
                            title = stringResource(id = R.string.battery_health),
                            value = state.batteryHealth,
                            modifier = Modifier.weight(1f)
                        )
                        BatteryStatCard(
                            icon = AppIcons.Battery.BatteryTemperature,
                            title = stringResource(id = R.string.battery_temperature),
                            value = "${state.batteryTemp}°C",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Linha 2
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
                    ) {
                        BatteryStatCard(
                            icon = AppIcons.Battery.BatteryConsumption,
                            title = stringResource(id = R.string.battery_consumption),
                            value = "45 Wh/km",
                            modifier = Modifier.weight(1f)
                        )
                        BatteryStatCard(
                            icon = AppIcons.Battery.BatteryChargingCycles,
                            title = stringResource(id = R.string.battery_charging_cycles),
                            value = "${state.batteryCycles} cycles",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // 5. MARGEM FINAL SCROLL
                Spacer(modifier = Modifier.height(Dimens.ScrollBottomPadding))
            }
        }
    }
}

@Composable
fun BigBatteryIndicator(
    state: BikeState,
    modifier: Modifier = Modifier
) {
    // 1. ESTADO DA ANIMAÇÃO
    // Controla qual imagem mostramos: True = Imagem 1, False = Imagem 2
    var showFrame1 by remember { mutableStateOf(true) }

    // 2. O MOTOR DA ANIMAÇÃO
    LaunchedEffect(state.isCharging) {
        if (state.isCharging) {
            // Se estiver a carregar, entra num loop infinito
            while (true) {
                delay(800) // Tempo de espera (800ms). Ajusta se quiseres mais rápido/lento.
                showFrame1 = !showFrame1 // Troca a imagem
            }
        } else {
            // Se parar de carregar, reseta para a imagem principal
            showFrame1 = true
        }
    }

    // 3. LÓGICA DE ESCOLHA DA IMAGEM E COR
    val currentIcon = if (state.isCharging) {
        // MODO A CARREGAR: Alterna entre as duas imagens
        if (showFrame1) {
            AppIcons.Battery.BigBatteryCharging // Imagem A (Ex: Cheia)
        } else {
            // ⚠️ ATENÇÃO: Coloca aqui a tua segunda imagem (Ex: Vazia ou Sem Raio)
            AppIcons.Battery.BigBattery
        }
    } else {
        // MODO NORMAL: Imagem estática
        AppIcons.Battery.BigBatteryCharging
    }

    val mainColor = if (state.isCharging) Color(0xFFFFD700) else GreenFresh

    // 4. O UI (Quase igual ao anterior, mas usa o currentIcon)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1.2f))

        // Ícone Dinâmico
        Icon(
            painter = painterResource(id = currentIcon), // 👈 Usa a variável dinâmica
            contentDescription = "Battery Status",
            tint = mainColor,
            modifier = Modifier
                .size(100.dp)
                .scale(1.5f)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "${state.batteryPercentage}%",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = (-1).sp
        )
    }
}

@Composable
fun BatteryInfoCard(
    state: BikeState
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackgroundColor
        )
    ) {
        Column(
            modifier = Modifier.padding(Dimens.SpacingSmall)
        ) {
            // Topo
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dimens.PaddingMedium)
            ) {
                Text(
                    text = if (state.isCharging) "Charging" else "Standby", // 👈 Texto Dinâmico
                    color = if (state.isCharging) GreenFresh else Color.Gray,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = stringResource(id = R.string.time_left),
                    color = Color.Gray,
                    fontSize = Dimens.TextSizeSmall
                )

                Text(
                    text = if (state.isCharging) state.timeLeft else "-- h -- m",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(Dimens.SpacingSmall))

            // Linha de Ícones
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Item 1
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(start = Dimens.PaddingMedium),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = AppIcons.Battery.Battery),
                        contentDescription = "Battery",
                        tint = GreenFresh,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("${state.batteryPercentage}%", color = Color.Gray, fontSize = Dimens.TextSizeSmall, fontWeight = FontWeight.Medium)
                }

                // Item 2 (Centro)
                Column(
                    modifier = Modifier.weight(1f).aspectRatio(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = AppIcons.Battery.Range),
                        contentDescription = "Range",
                        tint = GreenFresh,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("${state.range} km", color = Color.Gray, fontSize = Dimens.TextSizeSmall, fontWeight = FontWeight.Medium)
                }

                // Item 3 (Direita)
                Column(
                    modifier = Modifier.weight(1f).aspectRatio(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = AppIcons.Battery.Charging),
                        contentDescription = "Charging",
                        tint = GreenFresh,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(if(state.isCharging) "On" else "Off", color = Color.Gray, fontSize = Dimens.TextSizeSmall, fontWeight = FontWeight.Medium)                }
            }
        }
    }
}

@Composable
fun BatteryStatCard(
    icon: Int,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(CardBackgroundColor, RoundedCornerShape(16.dp))
            .padding(Dimens.PaddingMedium)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = GreenFresh,
            modifier = Modifier.size(34.dp)
        )
        Spacer(modifier = Modifier.height(Dimens.PaddingMedium))
        Text(text = value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = title, color = Color.Gray, fontSize = Dimens.TextSizeSmall)
    }
}