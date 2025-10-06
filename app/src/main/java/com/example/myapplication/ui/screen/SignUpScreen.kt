package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.navigation.Screen
import com.example.myapplication.viewmodel.MainViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: MainViewModel
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Reigstrar Usuario")

        Spacer(modifier = Modifier.height(24.dp))
        Text("Nombre de Usuario")

        Spacer(modifier = Modifier.height(16.dp))
        Text("Contraseña")


        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.navigateTo(Screen.LogIn)
            }
        ) {
            Text("Iniciar tomate 🍅")
        }
    }


}