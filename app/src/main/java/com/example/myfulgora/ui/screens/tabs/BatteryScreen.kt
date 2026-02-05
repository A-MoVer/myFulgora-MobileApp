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
                    text = "Battery Info",
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
                            title = "Battery health",
                            value = state.batteryHealth,
                            modifier = Modifier.weight(1f)
                        )
                        BatteryStatCard(
                            icon = AppIcons.Battery.BatteryTemperature,
                            title = "Temperature",
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
                            title = "Avg. consumption",
                            value = "45 Wh/km",
                            modifier = Modifier.weight(1f)
                        )
                        BatteryStatCard(
                            icon = AppIcons.Battery.BatteryChargingCycles,
                            title = "Charging cycles",
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
    state: BikeState = BikeState(),
    modifier: Modifier = Modifier) {

    val mainColor = if (state.isCharging) Color(0xFFFFD700) else GreenFresh

    Column( // 👈 Mudámos de Box para Column para ficar um em baixo do outro
        modifier = modifier, // Recebe o weight(0.35f) e fillMaxHeight() do pai
        horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
        //verticalArrangement = Arrangement.Center // Centra verticalmente

    ) {
        Spacer(modifier = Modifier.weight(1.2f))
        // 1. ÍCONE DA BATERIA
        Icon(
            painter = painterResource(
                id = if (state.isCharging) AppIcons.Battery.BigBatteryCharging else AppIcons.Battery.BigBatteryCharging // Se tiveres um icone "Full" ou normal, troca aqui o segundo
            ),
            contentDescription = "Battery Status",
            tint = mainColor, // 👈 COR DINÂMICA
            modifier = Modifier
                .size(100.dp)
                .scale(1.5f)
        )

        // Espaço entre o ícone e o texto
        Spacer(modifier = Modifier.height(20.dp))

        // 2. TEXTO DA PERCENTAGEM
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
                    text = "Time left",
                    color = Color.Gray,
                    fontSize = Dimens.TextSizeSmall
                )

                Text(
                    text = if (state.isCharging) "Calculating..." else "-- h -- m",
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