package com.example.myapplication.ui.screen


import androidx.compose.runtime.Composable
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.utils.obtenerWindowSizeClass

@Composable
fun HomeScreenMain(){
    val windowSizeClass = obtenerWindowSizeClass()
    when (windowSizeClass.widthSizeClass){
        WindowWidthSizeClass.Compact -> HomeScreenCompacta()
        WindowWidthSizeClass.Medium -> HomeScreenMediana()
        WindowWidthSizeClass.Expanded -> HomeScreenExtendida()
    }
}

@Preview(name = "Compacta", widthDp = 360, heightDp = 800)
@Composable
fun PreviewCompacta(){
    HomeScreenCompacta()
}

@Preview(name = "Mediana", widthDp = 600, heightDp = 800)
@Composable
fun PreviewMediana(){
    HomeScreenMediana()
}

@Preview(name = "Extendida", widthDp = 840, heightDp = 800)
@Composable
fun PreviewExtendida(){
    HomeScreenExtendida()
}