package com.example.myapplication.ui.screen

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.ui.utils.windowSizeClassOf

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun HomeRoute(windowSize: WindowSizeClass) {
    val windowSizeClass = windowSizeClassOf(LocalContext.current as Activity)
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact  -> HomeCompact()
        WindowWidthSizeClass.Medium   -> HomeMedium()
        WindowWidthSizeClass.Expanded -> HomeExpanded()
        else                          -> HomeCompact()
    }
}