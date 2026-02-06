package com.example.myfulgora.ui.screens.tabs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.DirectionsBike
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.LockOpen
import androidx.compose.material.icons.rounded.PowerSettingsNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfulgora.ui.components.FulgoraBackground
import com.example.myfulgora.ui.components.FulgoraInfoCard
import com.example.myfulgora.ui.components.FulgoraTopBar
import com.example.myfulgora.ui.theme.AppIcons
import com.example.myfulgora.ui.theme.Dimens
import com.example.myfulgora.ui.theme.GreenFresh
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import com.example.myfulgora.data.model.BikeState

@Composable
fun HomeScreen(
    state: BikeState,
    onMenuClick: () -> Unit = {}
) {
    // Estado para simular o bloqueio/desbloqueio (como no mockup)
    var isLocked by remember { mutableStateOf(true) }

    FulgoraBackground {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

            val screenW = maxWidth
            val iconSize = screenW * Dimens.IconScaleRatio
            val paddingSide = screenW * Dimens.SideMarginRatio
            val circleSize = screenW * Dimens.HomeCircleRatio
            val strokeWidth = 12.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = Dimens.TopPadding)
                    .padding(horizontal = paddingSide),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // 1. TOP BAR
                FulgoraTopBar(
                    title = "Hi, Alex!",
                    subtitle = "Ready to ride?",
                    iconSize = iconSize,
                    onMenuClick = onMenuClick
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 2. BIKE NAME & MINI STATUS
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "1FA6P8CF3E5100000",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = AppIcons.Dashboard.BatteryTop),
                            contentDescription = null,
                            tint = GreenFresh,
                            modifier = Modifier.size(34.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isLocked) "${state.batteryPercentage}%" else "1hr 10m 4s remaining",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // 3. CÍRCULO CENTRAL COM SETAS
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    // Setas de navegação (Mockup)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.Dashboard.ArrowLeft0),
                            contentDescription = null,
                            tint = Color.Gray.copy(alpha = 0.5f),
                            modifier = Modifier.size(24.dp)
                        )
                        Icon(
                            painter = painterResource(id = AppIcons.Dashboard.ArrowRight0),
                            contentDescription = null,
                            tint = Color.Gray.copy(alpha = 0.5f),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    // Círculo e Mota
                    Box(
                        modifier = Modifier.size(circleSize),
                        contentAlignment = Alignment.Center
                    ) {
                        // A. Arcos
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            // Fundo
                            drawArc(
                                color = Color.Gray.copy(alpha = 0.2f),
                                startAngle = 0f,
                                sweepAngle = 360f,
                                useCenter = false,
                                style = Stroke(width = strokeWidth.toPx())
                            )
                            val sweep = (state.batteryPercentage * 360f) / 100f
                            // Progresso (Verde)
                            drawArc(
                                color = GreenFresh,
                                startAngle = -90f,
                                sweepAngle = sweep, // 78% simulado
                                useCenter = false,
                                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                            )
                        }

                        // B. A Mota (Sozinha no meio)
                        Image(
                            painter = painterResource(id = AppIcons.Dashboard.MainBike),
                            contentDescription = "My Bike",
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .aspectRatio(1.4f)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // 4. BOTÕES DE ACÇÃO (Power & Lock Slider)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp), // Altura fixa dos botões
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // --- BOTÃO POWER (40%) ---
                    Surface(
                        modifier = Modifier
                            .weight(0.4f)      // 👈 Ocupa 40% da largura disponível
                            .fillMaxHeight(),  // Ocupa toda a altura da linha (56dp)
                        shape = RoundedCornerShape(28.dp), // Forma de "Pílula" para acompanhar o esticão
                        color = if (isLocked) GreenFresh else Color(0xFF1E1E1E),
                        // Adicionei onClick vazio ou com lógica se quiseres que ele faça algo
                        onClick = { /* Lógica do Power */ }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Rounded.PowerSettingsNew,
                                contentDescription = "Power",
                                tint = if (isLocked) Color.Black else Color.Gray,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    // --- SLIDER LOCK/UNLOCK (60%) ---
                    BoxWithConstraints(
                        modifier = Modifier
                            .weight(0.6f)
                            .fillMaxHeight()
                            .background(
                                color = if (isLocked) Color(0xFF1E1E1E) else GreenFresh,
                                shape = RoundedCornerShape(28.dp)
                            )
                            .clip(RoundedCornerShape(28.dp)) // Garante que nada sai das bordas
                    ) {
                        val maxWidthPx = constraints.maxWidth.toFloat()
                        val thumbSize = 48.dp // Tamanho da bolinha (um pouco menor que a altura 56dp para ter margem)
                        val padding = 4.dp    // Margem interna

                        // Converter dp para pixéis
                        val density = LocalDensity.current
                        val thumbSizePx = with(density) { thumbSize.toPx() }
                        val paddingPx = with(density) { padding.toPx() }

                        // A distância máxima que a bola pode andar
                        val maxSwipeDistance = maxWidthPx - thumbSizePx - (paddingPx * 2)

                        // Estado da animação da posição da bola
                        // Se isLocked = 0 (Esquerda), Se !isLocked = maxSwipeDistance (Direita)
                        val swipeableState = remember { Animatable(if (isLocked) 0f else maxSwipeDistance) }
                        val scope = rememberCoroutineScope()

                        // Sincronizar estado externo (isLocked) com a posição visual
                        LaunchedEffect(isLocked) {
                            swipeableState.animateTo(
                                targetValue = if (isLocked) 0f else maxSwipeDistance,
                                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                            )
                        }

                        // 1. O TEXTO DE FUNDO (Fica estático atrás)
                        Box(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isLocked) {
                                // Texto alinhado à direita quando bloqueado
                                Text(
                                    "Swipe to unlock >>",
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            } else {
                                // Texto alinhado à esquerda quando desbloqueado
                                Text(
                                    "<< Swipe to lock",
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                        }

                        // 2. A BOLINHA QUE DESLIZA (Thumb)
                        Box(
                            modifier = Modifier
                                .padding(padding) // Margem de segurança
                                .offset { IntOffset(swipeableState.value.roundToInt(), 0) } // 👈 O segredo: move a bola aqui
                                .size(thumbSize)
                                .background(
                                    color = if (isLocked) Color(0xFF2D2D2D) else Color.White,
                                    shape = CircleShape
                                )
                                .draggable(
                                    orientation = Orientation.Horizontal,
                                    state = rememberDraggableState { delta ->
                                        // Atualiza a posição enquanto arrastas (respeitando os limites)
                                        scope.launch {
                                            swipeableState.snapTo(
                                                (swipeableState.value + delta).coerceIn(0f, maxSwipeDistance)
                                            )
                                        }
                                    },
                                    onDragStopped = {
                                        // LÓGICA FINAL: Quando largas o dedo
                                        val targetVal = swipeableState.value
                                        val threshold = maxSwipeDistance * 0.5f // Se passar de 50% do caminho

                                        if (isLocked) {
                                            // Estava bloqueado, se arrastou muito para a direita -> Desbloqueia
                                            if (targetVal > threshold) {
                                                isLocked = false
                                            } else {
                                                // Não chegou lá? Volta para trás (Snap Back)
                                                scope.launch { swipeableState.animateTo(0f) }
                                            }
                                        } else {
                                            // Estava desbloqueado, se arrastou muito para a esquerda -> Bloqueia
                                            if (targetVal < threshold) {
                                                isLocked = true
                                            } else {
                                                // Não chegou lá? Volta para a direita
                                                scope.launch { swipeableState.animateTo(maxSwipeDistance) }
                                            }
                                        }
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            // Ícone dentro da bolinha
                            Icon(
                                imageVector = if (isLocked) Icons.Rounded.Lock else Icons.Rounded.LockOpen,
                                contentDescription = null,
                                tint = if (isLocked) Color.White else Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // 5. INFOCARD DE STATUS (4 colunas conforme o mockup)
                FulgoraInfoCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 👇 ALTERAR ESTAS LINHAS:

                        // 1. Bateria
                        HomeStatItem(
                            painterResource(id = AppIcons.Dashboard.Battery),
                            "${state.batteryPercentage}%", // Valor dinâmico
                            "",
                        )

                        // 2. Consumo (Podes manter estático ou simular depois)
                        HomeStatItem(
                            painterResource(id = AppIcons.Dashboard.Power),
                            "3,72",
                            "kW/100"
                        )

                        // 3. Autonomia (Range)
                        HomeStatItem(
                            painterResource(id = AppIcons.Dashboard.Bike),
                            "${state.range}", // Valor dinâmico
                            "km"
                        )

                        // 4. Status (Se o telemovel está conectado com a mota ou não...)
                        val isOnline = state.isOnline // ⚠️ Certifica-te que tens isto no BikeState

                        HomeStatItem(
                            icon = painterResource(id = AppIcons.Dashboard.Status),
                            value = if (isOnline) "Online" else "Offline",
                            label = "",
                            statusColor = if (isOnline) GreenFresh else Color.Red // 👈 Define a cor da bolinha
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))
            }
        }
    }
}

@Composable
fun HomeStatItem(
    icon: Any,
    value: String,
    label: String,
    statusColor: Color? = null // 👈 Novo parâmetro opcional
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Desenha o Ícone (aceita Vector ou Painter)
        when (icon) {
            is androidx.compose.ui.graphics.vector.ImageVector -> Icon(icon, null, tint = GreenFresh, modifier = Modifier.size(24.dp))
            is Painter -> Icon(icon, null, tint = GreenFresh, modifier = Modifier.size(24.dp))
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Linha do Texto + Bolinha
        Row(verticalAlignment = Alignment.CenterVertically) {

            // 👇 AQUI ESTÁ A BOLINHA
            if (statusColor != null) {
                Box(
                    modifier = Modifier
                        .size(8.dp) // Tamanho da bolinha
                        .clip(CircleShape)
                        .background(statusColor)
                )
                Spacer(modifier = Modifier.width(6.dp)) // Espaço entre a bolinha e o texto
            }

            Text(
                text = value,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            if (label.isNotEmpty()) {
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = label, color = Color.Gray, fontSize = 10.sp)
            }
        }
    }
}