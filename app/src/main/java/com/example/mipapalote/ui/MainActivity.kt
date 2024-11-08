// MainActivity.kt
package com.example.mipapalote

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mipapalote.ui.*
import com.example.mipapalote.ui.theme.MiPapaloteTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.initialize(this)
        Log.d("FirebaseInit", "Firebase se ha inicializado correctamente")
        setContent {
            MiPapaloteTheme {
                MiPapaloteApp()  // Inicializa la función principal de la app con el tema
            }
        }
    }
}

@Composable
fun MiPapaloteApp() {
    // Inicializa el controlador de navegación
    val navController = rememberNavController()

    // Configura las rutas y pantallas de la aplicación usando NavHost
    NavHost(
        navController = navController,
        startDestination = "login" // Pantalla inicial
    ) {
        // Pantalla de inicio de sesión
        composable("login") { LoginScreen(navController) }

        // Pantalla de registro de usuario
        composable("register") { RegisterScreen(navController) }

        // Pantalla para restablecer la contraseña
        composable("resetPassword") { ResetPasswordScreen(navController) }

        // Pantalla principal del usuario (Home)
        composable("home") { HomeScreen(navController) }

        // Pantalla del dashboard (tablero principal)
        composable("dashboard") { DashboardScreen(navController) }

        // Pantalla de escaneo de código QR
        composable("qr") { QRScreen(navController) }

        // Pantalla de notificaciones
        composable("notifications") { NotificationsScreen(navController) }

        // Pantalla para cerrar sesión
        composable("logout") { ProfileScreen(navController) }
    }
}