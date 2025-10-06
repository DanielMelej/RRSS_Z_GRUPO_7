package com.example.myapplication.navigation

sealed class Screen(val route: String) {

    data object Home : Screen("home")
    data object Profile : Screen("profile")

    data object Setting : Screen("setting")

    data object LogIn: Screen("logIn")

    data object SignUp: Screen("signUp")

    data class Detail(val itemId: String) : Screen("detail/{itemId}") {
        fun buildRoute(): String {
            return "detail/$itemId"
        }
    }
}