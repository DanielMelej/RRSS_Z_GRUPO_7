package com.example.tomatados.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")

    object Profile : Screen("profile_page")

    object Settings : Screen("settings")
    object Login : Screen("login")

    object Registro : Screen("registro")

    object MainPrincipal : Screen("principal_page")
    object Posts : Screen("posts")

    data class Detail(val itemId: String) : Screen("detail_page/{itemId}") {
        fun buildRoute(): String {
            return route.replace("{itemId}", itemId)
        }
    }
}