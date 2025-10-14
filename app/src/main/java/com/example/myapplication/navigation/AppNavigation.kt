package com.example.myapplication.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.InicioScreen
import com.example.myapplication.ui.screen.LogInScreen
import com.example.myapplication.ui.screen.ProfileScreen
import com.example.myapplication.ui.screen.RegistroScreen
import com.example.myapplication.ui.screen.ResumenScreen
import com.example.myapplication.ui.screen.SettingScreen
import com.example.myapplication.viewmodel.MainViewModel
import com.example.myapplication.viewmodel.UsuarioViewModel

@Composable

fun AppNavigation () {
    val navController = rememberNavController()
    val usuarioViewModel : UsuarioViewModel = viewModel()
    val mainViewModel: MainViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {

        composable("inicio") {
            InicioScreen(navController = navController)
        }
        composable ("registro") {
            RegistroScreen(navController, usuarioViewModel)
        }

        composable ("logIn") {
            LogInScreen(navController, usuarioViewModel)
        }

        composable ("profile") {
            ProfileScreen(navController, usuarioViewModel)
        }

        composable(route = "setting") {
            SettingScreen(navController, mainViewModel)
        }


        composable ("resumen"){
            ResumenScreen(usuarioViewModel)
        }
    }


}