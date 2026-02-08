package com.example.myfulgora.ui.screens.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem

@Composable
fun SettingsScreen(
    onMenuClick: () -> Unit = {}
) {
    var isMetric by remember { mutableStateOf(true) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var lowBatteryAlertEnabled by remember { mutableStateOf(true) }

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
                // 1. TOP BAR
                FulgoraTopBar(
                    iconSize = iconSize,
                    onMenuClick = onMenuClick
                )

                Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge))

                // 2. HEADER TEXT
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Settings",
                        fontSize = Dimens.TextSizeHeader,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))

                // 6. PREFERENCES
                FulgoraInfoCard {
                    Text(
                        text = "Preferences",
                        color = GreenFresh,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

                    // Units Toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Units", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                        
                        // Custom Unit Switcher
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFF2D2D2D))
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(if (isMetric) GreenFresh else Color.Transparent)
                                    .clickable { isMetric = true }
                                    .padding(horizontal = 12.dp, vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "km",
                                    color = if (isMetric) Color.Black else Color.Gray,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .background(if (!isMetric) GreenFresh else Color.Transparent)
                                    .clickable { isMetric = false }
                                    .padding(horizontal = 12.dp, vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "miles",
                                    color = if (!isMetric) Color.Black else Color.Gray,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.1f))

                    // Notifications Switch
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Notifications", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                        Switch(
                            modifier = Modifier.scale(0.85f),
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.Black,
                                checkedTrackColor = GreenFresh,
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color(0xFF2D2D2D),
                                uncheckedBorderColor = Color.Transparent
                            )
                        )
                    }

                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.1f))

                    // Low battery Alert Switch
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Low battery Alert", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                        Switch(
                            modifier = Modifier.scale(0.85f),
                            checked = lowBatteryAlertEnabled,
                            onCheckedChange = { lowBatteryAlertEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.Black,
                                checkedTrackColor = GreenFresh,
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color(0xFF2D2D2D),
                                uncheckedBorderColor = Color.Transparent
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

                // 7. STYLES
                FulgoraInfoCard {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Styles",
                            color = GreenFresh,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

                        // --- ITEM 1: LANGUAGE (COM DROPDOWN) ---
                        LanguageSelectorRow()

                        HorizontalDivider(color = Color.Gray.copy(alpha = 0.1f))

                        // --- ITEM 2: THEME (Ainda estático por enquanto) ---
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp)
                                .clickable { /* Lógica para mudar tema futuro */ },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Theme (White/Black)", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                            Icon(
                                painter = painterResource(id = AppIcons.Dashboard.ArrowRight0),
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.ScrollBottomPadding))
            }
        }
    }
}

@Composable
fun LanguageSelectorRow() {
    // 1. Estado do Menu (Aberto ou Fechado)
    var expanded by remember { mutableStateOf(false) }

    // 2. Lista de Idiomas Disponíveis
    // O par é: "Nome que aparece" to "Código do Android"
    val languages = mapOf(
        "English" to "en",
        "Português" to "pt",
        "Chinese" to "zh"
    )

    // Detectar a língua atual (apenas para mostrar o texto correto)
    // Nota: Isto é simplificado. Num cenário real, podes guardar isto em DataStore.
    val currentLocale = AppCompatDelegate.getApplicationLocales().toLanguageTags()
    val displayLanguage = when {
        currentLocale.contains("pt") -> "Português"
        currentLocale.contains("zh") -> "Chinese"
        else -> "English"
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        // A Linha Clicável
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true } // 👇 Abre o menu ao clicar
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Language", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                // Mostra a língua selecionada em pequeno
                Text(text = displayLanguage, color = GreenFresh, fontSize = 12.sp)
            }

            Icon(
                painter = painterResource(id = AppIcons.Dashboard.ArrowRight0),
                contentDescription = null,
                tint = if (expanded) GreenFresh else Color.Gray, // Muda cor se aberto
                modifier = Modifier.size(20.dp)
            )
        }

        // O Menu Dropdown (Aparece por cima quando expanded = true)
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF2A2A2A)) // Fundo escuro do menu
        ) {
            languages.forEach { (name, code) ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = name,
                            color = if (name == displayLanguage) GreenFresh else Color.White
                        )
                    },
                    onClick = {
                        expanded = false
                        // 👇 A MAGIA QUE MUDA O IDIOMA DA APP
                        changeAppLanguage(code)
                    }
                )
            }
        }
    }
}

// Função Auxiliar para mudar o idioma nativamente (Android 13+)
fun changeAppLanguage(languageCode: String) {
    val appLocale = LocaleListCompat.forLanguageTags(languageCode)
    AppCompatDelegate.setApplicationLocales(appLocale)
}