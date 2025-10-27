package com.example.tomatados.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tomatados.navigation.Screen
import com.example.tomatados.ui.components.AnimatedButton
import com.example.tomatados.ui.components.AnimatedEntry
import com.example.tomatados.ui.components.BouncingLogo
import com.example.tomatados.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    Scaffold { innerPadding ->
        AnimatedEntry {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Logo animado
                BouncingLogo()

                Spacer(modifier = Modifier.height(24.dp))

                // Título principal estilizado
                Text(
                    text = "Tomatado!",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    textAlign = TextAlign.Center,
                    fontSize = 38.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtítulo con actitud
                Text(
                    text = "Reacciona. Opina. ¡Expresate!",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Botón Login (elegante y llamativo)
                AnimatedButton(
                    text = "Iniciar sesión",
                    onClick = { viewModel.navigateTo(Screen.Login) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Botón Registro (versión secundaria)
                Button(
                    onClick = { viewModel.navigateTo(Screen.Registro) },
                    modifier = Modifier
                        .width(240.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "Crear cuenta",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}