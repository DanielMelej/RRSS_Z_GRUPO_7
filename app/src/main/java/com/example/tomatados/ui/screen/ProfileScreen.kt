package com.example.tomatados.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tomatados.ui.components.ImagenInteligente
import com.example.tomatados.viewmodel.ProfileViewModel
import java.io.File

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    val context = LocalContext.current
    val imageUri by viewModel.imageUri.collectAsState()

    // URI temporal para la cámara
    var tempUri: Uri? by remember { mutableStateOf(null) }

    // Launcher para galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.setImage(uri)
    }

    // Launcher para cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            viewModel.setImage(tempUri)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImagenInteligente(imageUri)

        Spacer(Modifier.height(20.dp))

        Button(onClick = {
            galleryLauncher.launch("image/*")
        }) {
            Text("Seleccionar desde galería")
        }

        Spacer(Modifier.height(12.dp))

        Button(onClick = {
            tempUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                File(context.cacheDir, "foto_perfil.jpg")
            )
            cameraLauncher.launch(tempUri!!)
        }) {
            Text("Tomar foto con cámara")
        }
    }
}