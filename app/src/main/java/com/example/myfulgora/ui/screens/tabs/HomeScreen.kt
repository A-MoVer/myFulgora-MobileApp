package com.example.myfulgora.ui.screens.tabs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfulgora.R
import com.example.myfulgora.ui.components.FulgoraBackground
import com.example.myfulgora.ui.components.FulgoraTopBar
import com.example.myfulgora.ui.theme.GreenFresh

@Composable
fun HomeScreen() {
    FulgoraBackground {
        // 1. A MAGIA: BoxWithConstraints dá-nos o tamanho exato do ecrã atual
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            // Cálculos de Proporção (Ajusta estes valores conforme o teu gosto)
            val screenW = maxWidth
            val screenH = maxHeight

            // Margens dinâmicas (ex: 6% da largura)
            val horizontalPadding = screenW * 0.06f
            val verticalPadding = screenH * 0.03f

            // Tamanhos dinâmicos
            val iconSizeStandard = screenW * 0.07f // Ícones normais
            val iconSizeSmall = screenW * 0.05f    // Ícones pequenos
            val powerButtonSize = screenW * 0.15f  // Botão Power grande

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = horizontalPadding)
                    .padding(bottom = verticalPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // 1. HEADER (Passamos o tamanho do ícone)
                FulgoraTopBar(
                    title = "Hi, Alex!",
                    subtitle = "Ready to ride?"
                )
                Spacer(modifier = Modifier.height(verticalPadding))

                // 2. NOME DA MOTA E BATERIA
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "VIM: 4Y1SL65848Z411439",
                        color = Color.White,
                        // Fonte escala com a largura do ecrã
                        fontSize = getResponsiveTextSize(screenW, 28f),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(screenH * 0.01f))

                    // Pílula da Bateria
                    Surface(
                        color = Color(0xFF1E1E1E),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.height(screenH * 0.045f) // Altura relativa
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Bolt,
                                contentDescription = null,
                                tint = GreenFresh,
                                modifier = Modifier.size(iconSizeSmall)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "77%",
                                color = Color.White,
                                fontSize = getResponsiveTextSize(screenW, 14f)
                            )
                        }
                    }
                }

                // Espaço Elástico
                Spacer(modifier = Modifier.weight(1f))

                // 3. A MOTA E O CÍRCULO
                // Usamos fillMaxWidth com percentagem para garantir que cabe em qualquer largura
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .aspectRatio(1f)
                ) {
                    val strokeWidth = screenW * 0.04f // Espessura do arco relativa à largura

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawArc(
                            color = Color(0xFF333333),
                            startAngle = 140f,
                            sweepAngle = 260f,
                            useCenter = false,
                            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                        )
                        drawArc(
                            color = GreenFresh,
                            startAngle = 140f,
                            sweepAngle = 180f,
                            useCenter = false,
                            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.mota),
                        contentDescription = "Mota",
                        modifier = Modifier.fillMaxSize(0.85f)
                    )

                    // Setas laterais também escalam
                    Icon(Icons.Rounded.ChevronLeft, contentDescription = null, tint = Color.Gray, modifier = Modifier.align(Alignment.CenterStart).size(iconSizeStandard))
                    Icon(Icons.Rounded.ChevronRight, contentDescription = null, tint = GreenFresh, modifier = Modifier.align(Alignment.CenterEnd).size(iconSizeStandard))
                }

                // Espaço Elástico
                Spacer(modifier = Modifier.weight(1f))

                // 4. CONTROLOS (SWIPE + POWER)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Botão Power Dinâmico
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .size(powerButtonSize)
                            .background(GreenFresh, CircleShape)
                    ) {
                        Icon(
                            Icons.Rounded.PowerSettingsNew,
                            contentDescription = "Power",
                            tint = Color.White,
                            modifier = Modifier.size(powerButtonSize * 0.5f) // Icone é metade do botão
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Swipe Area
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(powerButtonSize) // Mesma altura do botão power
                            .background(Color(0xFF1E1E1E), RoundedCornerShape(50)),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Swipe to unlock >>",
                            color = Color.Gray,
                            fontSize = getResponsiveTextSize(screenW, 14f),
                            modifier = Modifier.align(Alignment.Center)
                        )
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(powerButtonSize - 8.dp) // Ajuste fino para caber dentro
                                .background(GreenFresh, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Rounded.Lock,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(iconSizeSmall)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(verticalPadding))

                // 5. RODAPÉ
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DashboardStat(Icons.Rounded.BatteryStd, "75%", "Charge", iconSizeStandard, screenW, Modifier.weight(1f))
                    DashboardStat(Icons.Rounded.Bolt, "3,72", "kWh/100", iconSizeStandard, screenW, Modifier.weight(1f))
                    DashboardStat(Icons.Rounded.ModeOfTravel, "245", "km", iconSizeStandard, screenW, Modifier.weight(1f))
                    DashboardStat(Icons.Rounded.Map, "Loc", "Location", iconSizeStandard, screenW, Modifier.weight(1f))
                }
            }
        }
    }
}

// --- HELPERS E SUB-COMPONENTES ATUALIZADOS ---

// Função mágica para converter tamanho base em tamanho responsivo
@Composable
fun getResponsiveTextSize(screenWidth: Dp, baseSize: Float): TextUnit {
    // Lógica simples: Considera 360dp como largura base de um telemovel normal
    val scaleFactor = screenWidth.value / 360f
    return (baseSize * scaleFactor).sp
}

@Composable
fun HomeHeader(iconSize: Dp) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = "Hi, Alex!", color = Color.Gray, fontSize = 14.sp)
            Text(text = "Ready to ride?", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Row {
            Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.White, modifier = Modifier.size(iconSize))
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Default.Menu, contentDescription = null, tint = Color.White, modifier = Modifier.size(iconSize))
        }
    }
}

@Composable
fun DashboardStat(
    icon: ImageVector,
    value: String,
    label: String,
    iconSize: Dp,
    screenW: Dp,
    modifier: Modifier = Modifier // Adiciona isto
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier // Usa o modifier aqui
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = GreenFresh, modifier = Modifier.size(iconSize))
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = getResponsiveTextSize(screenW, 14f)
        )
        Text(
            text = label,
            color = Color.Gray,
            fontSize = getResponsiveTextSize(screenW, 8f), // Reduzi ligeiramente
            maxLines = 1

        )
    }
}