package com.example.tomatados.ui.screen

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
import com.example.tomatados.R
import com.example.tomatados.navigation.Screen
import com.example.tomatados.ui.components.AnimatedEntry
import com.example.tomatados.viewmodel.UsuarioViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {
    val estado by usuarioViewModel.estado.collectAsState()
    val (passVisible, setPassVisible) = remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    AnimatedEntry {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Imagen superior
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Logo de Tomatados",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(12.dp))

            Text("Inicio de Sesión", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(24.dp))

            // Campo: Nombre de usuario
            OutlinedTextField(
                value = estado.userName,
                onValueChange = usuarioViewModel::onUserNameChange,
                label = { Text("Nombre de usuario o correo") },
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

            // Campo: Contraseña
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
                visualTransformation = if (passVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
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

            // Botón de inicio de sesión con indicador de carga
            Button(
                onClick = {
                    if (usuarioViewModel.validarLogin()) {
                        isLoading = true
                        mensajeError = null

                        scope.launch {
                            val inicioCorrecto = usuarioViewModel.iniciarSesion()

                            delay(800)
                            isLoading = false

                            if (inicioCorrecto) {
                                // Navegación al MainScreen
                                navController.navigate(Screen.MainPrincipal.route) {
                                    popUpTo(Screen.MainPrincipal.route) { inclusive = false }
                                    launchSingleTop = true
                                }
                            } else {
                                mensajeError = "Usuario no encontrado. Regístrate primero."
                            }
                        }
                    }
                },
                enabled = estado.userName.trim().isNotEmpty() &&
                        estado.password.isNotEmpty() &&
                        !isLoading,
                modifier = Modifier
                    .width(220.dp)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(22.dp)
                            .padding(end = 8.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                    Text("Cargando...")
                } else {
                    Text("Continuar")
                }
            }

            // Mensaje de error (solo visible si algo falla)
            mensajeError?.let {
                Spacer(Modifier.height(12.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(8.dp))

            // Botón para volver al inicio
            TextButton(onClick = { navController.navigate(Screen.Home.route) }) {
                Text("Volver al Inicio")
            }
        }
    }
}