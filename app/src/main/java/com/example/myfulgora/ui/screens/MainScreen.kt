package com.example.myfulgora.ui.screens.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myfulgora.ui.screens.auth.ForgotPasswordScreen
import com.example.myfulgora.ui.screens.performance.PerformanceScreen
import com.example.myfulgora.ui.screens.BatteryScreen // Confirma se este import está certo
import com.example.myfulgora.ui.theme.AppIcons
import com.example.myfulgora.ui.theme.BlackBrand
import com.example.myfulgora.ui.theme.GreenFresh
import androidx.compose.ui.res.painterResource // 👈 IMPORTANTE
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = BlackBrand,
                contentColor = Color.Gray
            ) {
                // 1. TEXTOS
                val items = listOf("Map", "Battery", "Home", "Social", "Performance")

                // 2. ÍCONES (Agora vêm do teu AppIcons)
                // Confirma se os nomes batem certo com o teu ficheiro AppIcons!
                val icons = listOf(
                    AppIcons.Navbar.Map,         // Exemplo
                    AppIcons.Navbar.Battery,
                    AppIcons.Navbar.Home,
                    AppIcons.Navbar.Social,
                    AppIcons.Navbar.Home
                )

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEachIndexed { index, item ->
                    val isSelected = currentDestination?.hierarchy?.any { it.route == item.lowercase() } == true

                    NavigationBarItem(
                        // 👇 MUDANÇA AQUI: Usamos painterResource em vez de imageVector
                        icon = {
                            Icon(
                                painter = painterResource(id = icons[index]),
                                contentDescription = item,
                                // Força o tamanho 24dp para ficarem todos iguais,
                                // caso os PNGs/SVGs tenham tamanhos originais diferentes
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        label = { Text(item) },
                        alwaysShowLabel = false,
                        selected = isSelected,
                        onClick = {
                            navController.navigate(item.lowercase()) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = GreenFresh,
                            selectedTextColor = GreenFresh,
                            indicatorColor = Color.Transparent,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        // O Espaço onde os ecrãs mudam
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Rotas
            composable("map") {
                // Placeholder se ainda não tiveres o MapScreen
                MapScreen()
            }

            composable("battery") {
                BatteryScreen()
            }

            composable("home") {
                HomeScreen()
            }

            composable("social") {
                // Placeholder temporário centrado
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("Social Screen (WIP)", color = Color.White)
                }
            }

            composable("performance") {
                // A nossa nova PerformanceScreen com o menu dinâmico
                PerformanceScreen()
            }

            // Nota: Normalmente o ForgotPassword fica fora do MainScreen (no NavGraph principal),
            // mas se quiseres manter aqui por agora, funciona.
            composable("forgot_password") {
                ForgotPasswordScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onLoginAfterReset = {
                        // Navega para fora deste NavHost (precisas de lógica na MainActivity para isto funcionar bem)
                        // Por agora deixamos assim:
                    }
                )
            }
        }
    }
}

// Pequeno Placeholder para o Mapa não dar erro se não tiveres o ficheiro
@Composable
fun MapScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text("Map Screen", color = Color.White)
    }
}