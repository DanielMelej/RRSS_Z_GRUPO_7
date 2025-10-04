package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.utils.windowSizeClassOf
import com.example.myapplication.ui.screen.HomeRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme(dynamicColor = false) {   // desactivamos dinámicos para usar tus colores fijos
                val wsc = windowSizeClassOf(this)        // calcula el WindowSizeClass
                HomeRoute(windowSize = wsc)              // selecciona Compact/Medium/Expanded
            }
        }
    }
}