package com.example.myapplication.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun HomeCompact() {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
        Image(
            painter = painterResource(id = R.drawable.logo_tomatito),
            contentDescription = "Logo Tomatado",
            modifier = Modifier.size(120.dp) // tamaño en pixeles dp
        )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "TOMATADO",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Subtítulo gracioso
            Text(
                text = "Lanza un Tomatazo! 🍅",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 🔹 Botón principal
            Button(onClick = { /* acción futura */ }) {
                Text("Login")
            }

            Button(onClick = { /* acción futura */ }) {
                Text("Registro")

        }
    }
}

@Composable
fun HomeMedium() {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding).fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Text("TOMATADO 🍅", style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.height(12.dp))
            Text("Lanza un Tomatazo 🍅", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(28.dp))
            Button(onClick = {}, modifier = Modifier.width(220.dp)) { Text("Entrar") }
        }
    }
}

@Composable
fun HomeExpanded() {
    Scaffold { innerPadding ->
        Row(
            modifier = Modifier
                .padding(innerPadding).fillMaxSize().padding(32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text("TOMATADO 🍅", style = MaterialTheme.typography.displaySmall)
                Spacer(Modifier.height(16.dp))
                Text("Lanza un Tomatazo 🍅", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(32.dp))
                Button(onClick = {}, modifier = Modifier.width(260.dp)) { Text("Entrar") }
            }
            Spacer(Modifier.width(24.dp)) // aquí después puedes poner una imagen grande
        }
    }
}}