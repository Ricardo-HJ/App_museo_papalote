package com.example.App_museo_papalote.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { /* Contenido de la pantalla Home */ }
        composable(Screen.Dashboard.route) { /* Contenido de la pantalla Dashboard */ }
        composable(Screen.QR.route) { /* Contenido de la pantalla QR */ }
        composable(Screen.Notifications.route) { /* Contenido de la pantalla Notifications */ }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable("login") { LoginScreen(navController) } // Asegúrate de que "login" esté definido
    }
}