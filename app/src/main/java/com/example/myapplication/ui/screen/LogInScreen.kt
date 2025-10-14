package com.example.myapplication.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.myapplication.viewmodel.UsuarioViewModel

@Composable
fun LogInScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {
    val estado by usuarioViewModel.estado.collectAsState()
    val (passVisible, setPassVisible) = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.tomatito_login),
            contentDescription = "Ilustración de inicio de sesión",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(Modifier.height(8.dp))
        Text("Inicio de Sesión", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(24.dp))

        // Usuario
        OutlinedTextField(
            value = estado.userName,
            onValueChange = usuarioViewModel::onUserNameChange,
            label = { Text("Nombre de usuario") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = estado.errores.userName != null,
            supportingText = {
                estado.errores.userName?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            )
        )

        Spacer(Modifier.height(16.dp))

        // Contraseña
        OutlinedTextField(
            value = estado.password,
            onValueChange = usuarioViewModel::onPasswordChange,
            label = { Text("Contraseña") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = estado.errores.password != null,
            supportingText = {
                estado.errores.password?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                TextButton(onClick = { setPassVisible(!passVisible) }) {
                    Text(if (passVisible) "Ocultar" else "Mostrar")
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            )
        )

        Spacer(Modifier.height(16.dp))

        // Continuar
        Button(
            onClick = {
                if (usuarioViewModel.validarLogin()) {
                    // Navega a tu pantalla de inicio (ajusta la ruta si usas otra)
                    navController.navigate("profile") {
                        popUpTo("profile") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            },
            enabled = estado.userName.trim().isNotEmpty() && estado.password.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuar")
        }

        Spacer(Modifier.height(16.dp))

        // Volver a inicio
        Button(
            onClick = {
                navController.navigate("inicio") {
                    popUpTo("inicio") { inclusive = false }
                    launchSingleTop = true
                }
            }
        ) {
            Text("Volver al Inicio")
        }
    }
}