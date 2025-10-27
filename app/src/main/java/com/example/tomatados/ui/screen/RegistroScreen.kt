package com.example.myapplication.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tomatados.R
import com.example.tomatados.navigation.Screen
import com.example.tomatados.ui.components.AnimatedEntry
import com.example.tomatados.viewmodel.UsuarioViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState()
    var isLoading by remember { mutableStateOf(false) } // controla la animación de carga
    val scope = rememberCoroutineScope() // permite usar corrutinas dentro de onClick

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

            Text("Registro de Usuario", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(20.dp))

            // Campo: Nombre de usuario
            OutlinedTextField(
                value = estado.userName,
                onValueChange = viewModel::onUserNameChange,
                label = { Text("Nombre de usuario") },
                isError = estado.errores.userName != null,
                supportingText = {
                    estado.errores.userName?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo: Nombre completo
            OutlinedTextField(
                value = estado.fullName,
                onValueChange = viewModel::onFullNameChange,
                label = { Text("Nombre completo") },
                isError = estado.errores.fullName != null,
                supportingText = {
                    estado.errores.fullName?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo: Correo electrónico
            OutlinedTextField(
                value = estado.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text("Correo electrónico") },
                isError = estado.errores.email != null,
                supportingText = {
                    estado.errores.email?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo: Contraseña
            OutlinedTextField(
                value = estado.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = estado.errores.password != null,
                supportingText = {
                    estado.errores.password?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo: Confirmar contraseña
            OutlinedTextField(
                value = estado.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                label = { Text("Confirma tu contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = estado.errores.confirmPassword != null,
                supportingText = {
                    estado.errores.confirmPassword?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            // Botón Registrar con indicador de carga
            Button(
                onClick = {
                    if (viewModel.validarFormulario()) {
                        isLoading = true
                        scope.launch {
                            // ✅ Registrar el usuario en DataStore
                            val registrado = viewModel.registrarUsuario()

                            delay(1000)
                            isLoading = false

                            if (registrado) {
                                // ✅ Luego de guardar, ir al login
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                }
                            }
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier
                    .width(220.dp)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(22.dp)
                    )
                    Text("Cargando...")
                } else {
                    Text("Continuar")
                }
            }

            Spacer(Modifier.height(12.dp))

            // Botón Volver al inicio
            TextButton(
                onClick = { navController.navigate(Screen.Home.route) },
                enabled = !isLoading
            ) {
                Text("Volver al Inicio")
            }
        }
    }
}