package com.example.myapplication.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.viewmodel.MainViewModel

@Composable
fun LogInScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var userError by remember { mutableStateOf(false) }
    var passError by remember { mutableStateOf(false) }
    var passVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.tomatito),
            contentDescription = "Tomatito Inicio",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Fit
        )

        Text("Inicio de Sesión")

        Spacer(Modifier.height(24.dp))

        // Usuario
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                if (userError && it.isNotBlank()) userError = false
            },
            label = { Text("Nombre de usuario") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = userError,
            supportingText = { if (userError) Text("Ingresa tu nombre de usuario") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            )
        )

        Spacer(Modifier.height(16.dp))

        // Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (passError && it.isNotBlank()) passError = false
            },
            label = { Text("Contraseña") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = passError,
            supportingText = { if (passError) Text("Ingresa tu contraseña") },
            visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val texto = if (passVisible) "Ocultar" else "Mostrar"
                TextButton(onClick = { passVisible = !passVisible }) { Text(texto) }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            )
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                // Validación mínima
                userError = username.isBlank()
                passError = password.isBlank()

                if (!userError && !passError) {
                    // Aquí iría la autenticación real (Auth0/Firebase/local).
                    // Por ahora podrías navegar a Home para probar:
                    // viewModel.navigateTo(Screen.Home)
                }
            },
            enabled = username.isNotBlank() && password.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuar")
        }
    }
}