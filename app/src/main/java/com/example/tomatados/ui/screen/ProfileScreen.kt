package com.example.tomatados.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.tomatados.navigation.Screen
import com.example.tomatados.viewmodel.ProfileViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val context = LocalContext.current
    val imageUri by profileViewModel.imageUri.collectAsState()

    var selectedItem by remember { mutableStateOf(1) }
    var tempUri: Uri? by remember { mutableStateOf(null) }

    // ---------------------------
    //    LAUNCHER GALERÍA
    // ---------------------------
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        profileViewModel.setImage(uri)
    }

    // ---------------------------
    //    LAUNCHER CÁMARA
    // ---------------------------
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) profileViewModel.setImage(tempUri)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                // HOME
                NavigationBarItem(
                    selected = selectedItem == 0,
                    onClick = {
                        selectedItem = 0
                        navController.navigate(Screen.Home.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") }
                )

                // PROFILE (ACTUAL)
                NavigationBarItem(
                    selected = selectedItem == 1,
                    onClick = {
                        selectedItem = 1
                        // Estamos en Profile, así que no navegamos.
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ================================
            // FOTO DE PERFIL
            // ================================
            Image(
                painter = rememberAsyncImagePainter(imageUri ?: ""),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(160.dp)
                    .padding(10.dp)
            )

            Spacer(Modifier.height(16.dp))

            // -------------------------------
            // BOTÓN GALERÍA
            // -------------------------------
            Button(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Seleccionar desde galería")
            }

            Spacer(Modifier.height(10.dp))

            // -------------------------------
            // BOTÓN CÁMARA
            // -------------------------------
            Button(
                onClick = {
                    tempUri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.provider",
                        File(context.cacheDir, "foto_perfil.jpg")
                    )
                    cameraLauncher.launch(tempUri!!)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Tomar foto con cámara")
            }

            Spacer(Modifier.height(30.dp))

            Text(
                "¡Bienvenido a tu Perfil!",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}