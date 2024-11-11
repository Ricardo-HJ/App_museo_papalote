import com.example.App_museo_papalote.R

sealed class Screen(val route: String, val icon: Int, val title: String) {
    object Home : Screen("home", R.drawable.ic_home, "Inicio")
    object Dashboard : Screen("dashboard", R.drawable.ic_dashboard, "Dashboard")
    object QR : Screen("qr", R.drawable.ic_qr, "QR")
    object Notifications : Screen("notifications", R.drawable.ic_notifications, "Notificaciones")
    object Profile : Screen("profile", R.drawable.ic_profile, "Perfil")
}