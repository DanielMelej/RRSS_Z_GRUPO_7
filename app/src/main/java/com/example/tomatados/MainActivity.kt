package com.example.tomatados

import SettingsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.screen.RegistroScreen
import com.example.tomatados.navigation.NavigationEvent
import com.example.tomatados.navigation.Screen
import com.example.tomatados.ui.screen.HomeScreen
import com.example.tomatados.ui.screen.LoginScreen
import com.example.tomatados.ui.screen.MainScreen
import com.example.tomatados.ui.screen.PostsScreen
import com.example.tomatados.ui.screen.ProfileScreen
import com.example.tomatados.ui.theme.TomatadosTheme
import com.example.tomatados.viewmodel.MainViewModel
import com.example.tomatados.viewmodel.UsuarioViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TomatadosTheme {

                val viewModel: MainViewModel = viewModel()
                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    viewModel.navigationEvents.collectLatest { event ->
                        when (event) {
                            is NavigationEvent.NavigateTo -> {
                                navController.navigate(event.route.route) {
                                    // Controlar el comportamiento de la pila
                                    event.popUpToRoute?.let {
                                        popUpTo(it.route) {
                                            inclusive = event.inclusive
                                        }
                                    }
                                    launchSingleTop = event.singleTop
                                    restoreState = true
                                }
                            }
                            is NavigationEvent.PopBackStack -> navController.popBackStack()
                            is NavigationEvent.NavigateUp -> navController.navigateUp()
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route, // Pantalla inicial
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(navController = navController, viewModel = viewModel)
                        }

                        composable(Screen.Settings.route) {
                            SettingsScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(route = Screen.Login.route) {
                            val usuarioViewModel: UsuarioViewModel = viewModel()
                            LoginScreen(navController = navController, usuarioViewModel = usuarioViewModel)
                        }

                        composable(route = Screen.Registro.route) {
                            val usuarioViewModel: UsuarioViewModel = viewModel()
                            RegistroScreen(
                                navController = navController,
                                viewModel = usuarioViewModel
                            )
                        }

                        composable(Screen.MainPrincipal.route) {
                            val usuarioViewModel: UsuarioViewModel = viewModel()
                            MainScreen(navController = navController, usuarioViewModel = usuarioViewModel)
                        }

                        composable(Screen.Posts.route) {
                            PostsScreen()
                        }
                    }
                }
            }
        }
    }
}