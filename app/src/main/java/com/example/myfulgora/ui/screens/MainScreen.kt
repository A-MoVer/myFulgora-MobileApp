package com.example.myfulgora.ui.screens.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import kotlinx.coroutines.launch
import com.example.myfulgora.ui.components.FulgoraDrawerItem
import com.example.myfulgora.ui.theme.AppIcons
import com.example.myfulgora.ui.theme.BlackBrand
import com.example.myfulgora.ui.theme.GreenFresh
import androidx.compose.ui.platform.LocalLayoutDirection // 👈 NOVO
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfulgora.data.model.BikeState
import com.example.myfulgora.ui.viewmodel.HomeUiState
import com.example.myfulgora.ui.viewmodel.MotaViewModel

// Classe auxiliar para organizar os itens do menu
data class DrawerItemData(
    val title: String,
    val icon: Int, // R.drawable...
    val route: String
)

@Composable
fun MainScreen() {
    val viewModel: MotaViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Lista de itens (igual ao que tinhas)
    val menuItems = listOf(
        DrawerItemData("Home", AppIcons.Navbar.Home, "home"),
        DrawerItemData("My Profile", AppIcons.Navbar.Home, "profile"), // Placeholder
        DrawerItemData("Map", AppIcons.Navbar.Map, "map"),
        DrawerItemData("Battery", AppIcons.Navbar.Battery, "battery"),
        DrawerItemData("Performance", AppIcons.Navbar.Performance, "performance"),
        DrawerItemData("Social", AppIcons.Navbar.Social, "social")
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Preparar o estado para passar à Home
    // Se for Loading, passamos um estado vazio. Se for Success, passamos os dados reais.
    val currentBikeState = when(val state = uiState) {
        is HomeUiState.Success -> state.bikeState
        else -> BikeState() // Estado vazio/zeros enquanto carrega
    }

    // 1. O TRUQUE: Mudar a direção do layout "Mestre" para RTL (Direita para Esquerda)
    // Isto faz com que o Drawer "pense" que o início do ecrã é à direita.
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = false,
            drawerContent = {
                // 2. CORREÇÃO: Voltamos a pôr o conteúdo do menu em LTR (Normal)
                // Se não fizéssemos isto, o texto "Alex Rider" ficaria alinhado ao contrário.
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                    ModalDrawerSheet(
                        drawerShape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp),
                        drawerContainerColor = Color(0xFF1E1E1E),
                        drawerContentColor = Color.White,
                        modifier = Modifier.width(300.dp)
                    ) {
                        // --- O TEU CONTEÚDO DO MENU (IGUAL) ---
                        // (Copiei o código que já tinhas para aqui)

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, end = 24.dp, top = 48.dp, bottom = 24.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(Color.Gray),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.size(32.dp))
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Alex Rider", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("alex.rider@fulgora.com", fontSize = 14.sp, color = Color.Gray)
                        }

                        HorizontalDivider(color = Color.Gray.copy(alpha = 0.3f), modifier = Modifier.padding(horizontal = 24.dp))
                        Spacer(modifier = Modifier.height(24.dp))

                        menuItems.forEach { item ->
                            FulgoraDrawerItem(
                                label = item.title,
                                painter = painterResource(id = item.icon),
                                selected = currentRoute == item.route,
                                iconColor = if (currentRoute == item.route) GreenFresh else Color.Gray,
                                textColor = if (currentRoute == item.route) GreenFresh else Color.White,
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                    scope.launch { drawerState.close() }
                                }
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        FulgoraDrawerItem(
                            label = "Settings",
                            painter = painterResource(id = AppIcons.Navbar.Home), // Placeholder
                            onClick = { scope.launch { drawerState.close() } },
                            iconColor = Color.Gray
                        )

                        FulgoraDrawerItem(
                            label = "Logout",
                            painter = painterResource(id = AppIcons.Navbar.Home), // Placeholder
                            onClick = { /* Logout */ },
                            textColor = Color(0xFFFF4444),
                            iconColor = Color(0xFFFF4444)
                        )

                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        ) {
            // 3. CORREÇÃO: O Ecrã Principal também tem de voltar a ser LTR
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                Scaffold(
                    bottomBar = {
                        // A tua BottomBar igualzinha...
                        NavigationBar(
                            containerColor = BlackBrand,
                            contentColor = Color.Gray
                        ) {
                            // (O código da tua BottomBar mantém-se igual aqui...)
                            // Vou simplificar para não ocupar muito espaço, mas mantém o teu código original da lista
                            val bottomItems = listOf("Map", "Battery", "Home", "Social", "Performance")
                            val bottomIcons = listOf(AppIcons.Navbar.Map, AppIcons.Navbar.Battery, AppIcons.Navbar.Home, AppIcons.Navbar.Social, AppIcons.Navbar.Performance)

                            bottomItems.forEachIndexed { index, item ->
                                val isSelected = currentRoute == item.lowercase()
                                NavigationBarItem(
                                    icon = { Icon(painterResource(id = bottomIcons[index]), contentDescription = item, modifier = Modifier.size(24.dp)) },
                                    label = { Text(item) },
                                    selected = isSelected,
                                    alwaysShowLabel = false,
                                    onClick = {
                                        navController.navigate(item.lowercase()) {
                                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = GreenFresh, selectedTextColor = GreenFresh,
                                        indicatorColor = Color.Transparent, unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("map") { MapScreen() }
                        composable("profile") {
                            ProfileScreen(onMenuClick = { scope.launch { drawerState.open() } })
                        }
                        composable("battery") {
                            BatteryScreen(
                                state = currentBikeState,
                                onMenuClick = { scope.launch { drawerState.open() } })
                        }
                        composable("home") {
                            HomeScreen(
                                state = currentBikeState,
                                onMenuClick = { scope.launch { drawerState.open() } })
                        }
                        composable("social") {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Social", color = Color.White) }
                        }
                        composable("performance") {
                            PerformanceScreen(onMenuClick = { scope.launch { drawerState.open() } })
                        }
                    }
                }
            }
        }
    }
}