package com.example.myfulgora.ui.screens.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.myfulgora.R
import com.example.myfulgora.data.model.BikeState
import com.example.myfulgora.ui.theme.AppIcons
import com.example.myfulgora.ui.theme.BlackBrand
import com.example.myfulgora.ui.theme.GreenFresh
import com.example.myfulgora.ui.viewmodel.HomeUiState
import com.example.myfulgora.ui.viewmodel.MotaViewModel
import kotlinx.coroutines.launch

// Classe auxiliar atualizada para aceitar Painter ou ImageVector
data class DrawerItemData(
    val title: String,
    val icon: Any,
    val route: String
)

@Composable
fun MainScreen() {
    val viewModel: MotaViewModel = viewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }

    val menuItems = listOf(
        DrawerItemData("Home", AppIcons.Navbar.Home, "home"),
        DrawerItemData("Profile", Icons.Outlined.Person, "profile"),
        DrawerItemData("Map", AppIcons.Navbar.Map, "map"),
        DrawerItemData("Battery", AppIcons.Navbar.Battery, "battery"),
        DrawerItemData("Social", AppIcons.Navbar.Social, "social"),
        DrawerItemData("Performance", AppIcons.Navbar.Performance, "performance"),
        DrawerItemData("Settings", Icons.Outlined.Settings, "settings")
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val currentBikeState = when(val state = uiState) {
        is HomeUiState.Success -> state.bikeState
        else -> BikeState()
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    ModalDrawerSheet(
                        drawerShape = RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp),
                        drawerContainerColor = Color(0xFF121212),
                        modifier = Modifier.width(320.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(vertical = 24.dp)
                        ) {
                            // Header: Back + Logo
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { scope.launch { drawerState.close() } }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.Gray)
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Image(
                                    painter = painterResource(id = R.drawable.logo_app),
                                    contentDescription = "myFULGORA",
                                    modifier = Modifier.height(28.dp)
                                )
                                Spacer(modifier = Modifier.weight(1.2f))
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Search Bar
                            TextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).height(50.dp),
                                placeholder = { Text("Search", color = Color.Gray, fontSize = 14.sp) },
                                leadingIcon = { Icon(Icons.Default.Search, null, tint = Color.Gray, modifier = Modifier.size(20.dp)) },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color(0xFF1E1E1E),
                                    unfocusedContainerColor = Color(0xFF1E1E1E),
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            // Menu Items
                            Column(modifier = Modifier.fillMaxWidth()) {
                                menuItems.forEach { item ->
                                    DrawerItem(
                                        label = item.title,
                                        icon = item.icon,
                                        selected = currentRoute == item.route,
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
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            // Bottom Items
                            DrawerItem(
                                label = "Help",
                                icon = Icons.Outlined.HelpOutline,
                                onClick = { /* Help */ }
                            )
                            DrawerItem(
                                label = "Log Out",
                                icon = Icons.AutoMirrored.Filled.Logout,
                                onClick = { /* Logout */ }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Scaffold(
                    bottomBar = {
                        NavigationBar(containerColor = BlackBrand, contentColor = Color.Gray) {
                            val bottomItems = listOf("Map", "Battery", "Home", "Social", "Performance")
                            val bottomIcons = listOf(AppIcons.Navbar.Map, AppIcons.Navbar.Battery, AppIcons.Navbar.Home, AppIcons.Navbar.Social, AppIcons.Navbar.Performance)
                            bottomItems.forEachIndexed { index, item ->
                                val route = item.lowercase()
                                NavigationBarItem(
                                    icon = { Icon(painterResource(id = bottomIcons[index]), item, modifier = Modifier.size(24.dp)) },
                                    label = { Text(item) },
                                    selected = currentRoute == route,
                                    alwaysShowLabel = false,
                                    onClick = {
                                        navController.navigate(route) {
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
                    NavHost(navController = navController, startDestination = "home", modifier = Modifier.padding(innerPadding)) {
                        composable("map") { MapScreen() }
                        composable("profile") { ProfileScreen(onMenuClick = { scope.launch { drawerState.open() } }) }
                        composable("battery") { BatteryScreen(state = currentBikeState, onMenuClick = { scope.launch { drawerState.open() } }) }
                        composable("home") { HomeScreen(state = currentBikeState, onMenuClick = { scope.launch { drawerState.open() } }) }
                        composable("social") { SocialScreen(onMenuClick = { scope.launch { drawerState.open() } }) }
                        composable("performance") { PerformanceScreen(onMenuClick = { scope.launch { drawerState.open() } }) }
                        composable("settings") { /* Settings Screen */ }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerItem(
    label: String,
    icon: Any,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    val color = if (selected) GreenFresh else Color.White
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = color,
            fontSize = 18.sp,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
        )
        Spacer(modifier = Modifier.width(16.dp))
        when (icon) {
            is ImageVector -> Icon(icon, null, tint = color, modifier = Modifier.size(24.dp))
            is Int -> Icon(painterResource(id = icon), null, tint = color, modifier = Modifier.size(24.dp))
        }
    }
}