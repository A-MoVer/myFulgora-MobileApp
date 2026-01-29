package com.example.myfulgora.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfulgora.ui.theme.GreenFresh
import com.example.myfulgora.ui.theme.CardBackgroundColor



// --- 1. O CABEÇALHO REUTILIZÁVEL (TOP BAR) ---
// ... imports ...

@Composable
fun FulgoraTopBar(
    title: String = "Hi, Alex!",
    subtitle: String = "Ready to ride?",
    iconSize: Dp = 24.dp, // 👈 Novo parâmetro com valor por defeito
    onNotificationClick: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Textos
        Column {
            Text(text = title, color = Color.Gray, fontSize = 14.sp)
            Text(text = subtitle, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        // Ícones Dinâmicos
        Row {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = Color.White,
                modifier = Modifier
                    .size(iconSize) // 👈 Usa o tamanho calculado
                    .clickable { onNotificationClick() }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Color.White,
                modifier = Modifier
                    .size(iconSize) // 👈 Usa o tamanho calculado
                    .clickable { onMenuClick() }
            )
        }
    }
}

// --- 2. O CARTÃO CINZENTO BASE (INFO CARD) ---
// Este é o "contentor" que vamos usar para Pneus, Performance, etc.
@Composable
fun FulgoraInfoCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = CardBackgroundColor,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp), // Cantos arredondados consistentes
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // Padding interno padrão
        ) {
            content()
        }
    }
}