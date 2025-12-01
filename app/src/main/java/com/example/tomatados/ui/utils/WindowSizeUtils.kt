package com.example.tomatados.ui.utils

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun obtenerWindowSizeClass(): WindowSizeClass {
    val context = LocalContext.current

    // Buscar la Activity en el contexto
    val activity = context as? android.app.Activity
        ?: throw IllegalStateException("WindowSizeClass requiere una Activity")

    return calculateWindowSizeClass(activity)
}