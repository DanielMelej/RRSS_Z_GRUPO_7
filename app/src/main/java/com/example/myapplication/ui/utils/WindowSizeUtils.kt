package com.example.myapplication.ui.utils

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable


@Composable
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun windowSizeClassOf(activity: Activity): WindowSizeClass =
    calculateWindowSizeClass(activity)