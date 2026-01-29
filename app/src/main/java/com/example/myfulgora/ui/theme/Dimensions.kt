package com.example.myfulgora.ui.theme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object Dimens {
    // --- ESPAÇAMENTOS (Margins / Paddings) ---
    val PaddingSmall = 8.dp
    val PaddingMedium = 16.dp
    val PaddingLarge = 24.dp
    val PaddingExtraLarge = 32.dp

    // Padding específico para o topo (igual à Bateria/Performance)
    val TopPadding = 16.dp

    // Espaço final para o Scroll não bater na BottomBar
    val ScrollBottomPadding = 100.dp

    // --- TAMANHOS DE TEXTO (Opcional, se quiseres centralizar) ---
    val SpacingSmall = 12.dp
    val TextSizeSmall = 12.sp
    val TextSizeNormal = 14.sp
    val TextSizeTitle = 20.sp
    val TextSizeHeader = 28.sp

    // --- RÁCIOS (Percentagens do Ecrã) ---
    // Isto mantém a tua lógica dinâmica limpa!
    const val IconScaleRatio = 0.07f    // 7% da largura para ícones
    const val SideMarginRatio = 0.06f   // 6% da largura para margem lateral
    const val BikeHeightRatio = 0.25f   // 25% da altura para a mota
}