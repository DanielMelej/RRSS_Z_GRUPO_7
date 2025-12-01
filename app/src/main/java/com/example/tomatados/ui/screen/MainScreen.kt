package com.example.tomatados.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tomatados.R
import com.example.tomatados.navigation.Screen
import com.example.tomatados.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {
    val estado by usuarioViewModel.estado.collectAsState()
    val scope = rememberCoroutineScope()
    var selectedTab by remember { mutableStateOf(0) }

    // ✅ Construir nombre completo desde campos separados
    val nombreCompleto = buildString {
        if (estado.primerNombre.isNotBlank()) {
            append(estado.primerNombre)
        }
        if (estado.segundoNombre.isNotBlank()) {
            append(" ${estado.segundoNombre}")
        }
        if (estado.primerApellido.isNotBlank()) {
            append(" ${estado.primerApellido}")
        }
        if (estado.segundoApellido.isNotBlank()) {
            append(" ${estado.segundoApellido}")
        }
    }.trim()

    // ✅ Obtener solo el primer nombre para el saludo
    val primerNombre = estado.primerNombre.ifBlank { estado.userName }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Text("🍅", fontSize = 22.sp) },
                    label = { Text("Inicio") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                        scope.launch {
                            usuarioViewModel.cerrarSesion()
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                            }
                        }
                    },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Cerrar sesión") },
                    label = { Text("Cerrar sesión") }
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Encabezado con nombre real del usuario
            Text(
                text = "¡Bienvenido, $primerNombre!",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(4.dp))

            // Mostrar username
            Text(
                text = "@${estado.userName}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Nivel de Tomate: Principiante 🍅",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(32.dp))

            // Card con información del usuario
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "Tu perfil",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))

                    if (nombreCompleto.isNotBlank()) {
                        Text("Nombre: $nombreCompleto")
                    }

                    if (estado.email.isNotBlank()) {
                        Text("Email: ${estado.email}")
                    }

                    Text("Usuario: ${estado.userName}")
                }
            }

            Spacer(Modifier.height(16.dp))

            // 🆕 Botón para ver publicaciones
            Button(
                onClick = { navController.navigate(Screen.Posts.route) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("📄 Ver Publicaciones de la Comunidad")
            }

            Spacer(Modifier.height(24.dp))

            // Imagen 1
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Contenido 1",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clickable { /* Navegar más adelante */ }
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(16.dp))

            // Imagen 2
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Contenido 2",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clickable { /* Navegar más adelante */ }
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop
            )
        }
    }
}