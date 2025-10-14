package com.example.myapplication.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("inicio") },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.tomatito_registro),
                            contentDescription = "Inicio"
                        )
                    },
                    label = { Text("Inicio") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Ya estás en perfil */ },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("setting") },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Ajustes") },
                    label = { Text("Ajustes") }
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // 📸 Foto de perfil
            Image(
                painter = painterResource(id = R.drawable.tomatito_registro), // reemplaza con tu imagen
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(180.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ⭐ Nivel de tomate (ejemplo estático)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                // rating stars
                repeat(5) { index ->
                    Icon(
                        painter = painterResource(
                            id = if (index == 0) R.drawable.tomatito_incio else R.drawable.tomatito_incio
                        ),
                        contentDescription = "Nivel ${index + 1}",
                        tint = if (index == 0) Color(0xFFFF9800) else Color.Gray,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    text = "3",
                    style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFFD84315))
                )
            }

            Spacer(Modifier.height(12.dp))

            // 👤 Datos del usuario
            Text(
                text = estado.fullName.ifBlank { "NOMBRE" },
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Nivel de Tomate: 🍅 Novato",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Tags: #Ácido #Fresco",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Comentarios",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "“Excelente app para valorar tomates 🍅”",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}