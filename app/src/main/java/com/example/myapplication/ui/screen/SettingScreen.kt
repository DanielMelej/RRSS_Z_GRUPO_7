package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.viewmodel.MainViewModel

@Composable
fun SettingScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Configuración",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Opciones de cuenta",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        // Botón principal para cerrar sesión
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Text("Cerrar sesión")
        }

        // Diálogo de confirmación
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Cerrar sesión") },
                text = { Text("¿Seguro que deseas cerrar sesión?") },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog = false
                        // Navegar al inicio
                        navController.navigate("inicio") {
                            popUpTo(0) // Limpia el backstack para evitar volver con “atrás”
                        }
                    }) {
                        Text("Sí, cerrar sesión")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}