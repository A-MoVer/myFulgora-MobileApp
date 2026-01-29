package com.example.myfulgora.ui.screens.performance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.myfulgora.ui.components.FulgoraBackground
import com.example.myfulgora.ui.components.FulgoraInfoCard
import com.example.myfulgora.ui.components.FulgoraTopBar
import com.example.myfulgora.ui.theme.Dimens
import com.example.myfulgora.ui.theme.GreenFresh

@Composable
fun PerformanceScreen() {
    FulgoraBackground {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

            val screenW = maxWidth
            val screenH = maxHeight

            // Cálculos dinâmicos
            val iconSize = screenW * Dimens.IconScaleRatio
            val bikeHeight = screenH * Dimens.BikeHeightRatio
            val paddingSide = screenW * Dimens.SideMarginRatio

            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = paddingSide)
                    .padding(top = Dimens.TopPadding) // 16.dp fixo no topo
            ) {

                // 1. HEADER (Agora é um item normal da lista)
                // Quando dás scroll para baixo, isto desaparece naturalmente.
                FulgoraTopBar(
                    iconSize = iconSize
                )

                Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge))

                // 2. TÍTULO E SUBTÍTULO
                Column {
                    Text(
                        text = "Maintenance",
                        fontSize = Dimens.TextSizeHeader,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "S10 Bike Name",
                        fontSize = Dimens.TextSizeNormal,
                        color = GreenFresh
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

                // 3. ZONA DA MOTO
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(bikeHeight)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Bike Image Here", color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))

                // 4. GRELHA DE CARTÕES
                Row(modifier = Modifier.fillMaxWidth()) {
                    FulgoraInfoCard(modifier = Modifier.weight(1f)) {
                        Text("Tyre Pressure", color = Color.Gray, fontSize = Dimens.TextSizeSmall)
                        Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
                        Text("Front 32 PSI", color = Color.White, fontWeight = FontWeight.Bold, fontSize = Dimens.TextSizeNormal)
                    }

                    Spacer(modifier = Modifier.width(Dimens.PaddingMedium))

                    FulgoraInfoCard(modifier = Modifier.weight(1f)) {
                        Text("Next Service", color = Color.Gray, fontSize = Dimens.TextSizeSmall)
                        Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
                        Text("320 km", color = Color.White, fontWeight = FontWeight.Bold, fontSize = Dimens.TextSizeNormal)
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

                // 5. CARTÃO DE PERFORMANCE
                FulgoraInfoCard {
                    Text("Performance", color = Color.Gray, fontSize = Dimens.TextSizeSmall)
                    Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Energy", color = Color.Gray, fontSize = Dimens.TextSizeSmall)
                            Text("0.9 kWh", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                        Column {
                            Text("Avg Speed", color = Color.Gray, fontSize = Dimens.TextSizeSmall)
                            Text("42 km/h", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Espaço final para não bater na barra de navegação
                Spacer(modifier = Modifier.height(Dimens.ScrollBottomPadding))
            }
        }
    }
}