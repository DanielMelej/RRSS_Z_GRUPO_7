package com.example.myapplication.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import kotlinx.coroutines.launch

@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState()
    var mensajeExito by remember { mutableStateOf<String?>(null) }
    var mensajeError by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    AnimatedEntry {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                    .height(120.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(12.dp))

            Text("Registro de Usuario", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

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
                modifier = Modifier.fillMaxWidth(),
                enabled = !estado.isLoading,
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))

            // ✅ CAMPO: Primer Nombre
            OutlinedTextField(
                value = estado.primerNombre,
                onValueChange = viewModel::onPrimerNombreChange,
                label = { Text("Primer Nombre") },
                isError = estado.errores.primerNombre != null,
                supportingText = {
                    estado.errores.primerNombre?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !estado.isLoading,
                singleLine = true,
                placeholder = { Text("Ej: Juan") }
            )

            Spacer(Modifier.height(8.dp))

            // ✅ CAMPO: Segundo Nombre (OPCIONAL)
            OutlinedTextField(
                value = estado.segundoNombre,
                onValueChange = viewModel::onSegundoNombreChange,
                label = { Text("Segundo Nombre (Opcional)") },
                isError = estado.errores.segundoNombre != null,
                supportingText = {
                    estado.errores.segundoNombre?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !estado.isLoading,
                singleLine = true,
                placeholder = { Text("Ej: Carlos") }
            )

            Spacer(Modifier.height(8.dp))

            // ✅ CAMPO: Primer Apellido
            OutlinedTextField(
                value = estado.primerApellido,
                onValueChange = viewModel::onPrimerApellidoChange,
                label = { Text("Primer Apellido") },
                isError = estado.errores.primerApellido != null,
                supportingText = {
                    estado.errores.primerApellido?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !estado.isLoading,
                singleLine = true,
                placeholder = { Text("Ej: Pérez") }
            )

            Spacer(Modifier.height(8.dp))

            // ✅ CAMPO: Segundo Apellido
            OutlinedTextField(
                value = estado.segundoApellido,
                onValueChange = viewModel::onSegundoApellidoChange,
                label = { Text("Segundo Apellido") },
                isError = estado.errores.segundoApellido != null,
                supportingText = {
                    estado.errores.segundoApellido?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !estado.isLoading,
                singleLine = true,
                placeholder = { Text("Ej: González") }
            )

            Spacer(Modifier.height(8.dp))

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
                modifier = Modifier.fillMaxWidth(),
                enabled = !estado.isLoading,
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))

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
                modifier = Modifier.fillMaxWidth(),
                enabled = !estado.isLoading,
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))

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
                modifier = Modifier.fillMaxWidth(),
                enabled = !estado.isLoading,
                singleLine = true
            )

            Spacer(Modifier.height(20.dp))

            // Botón Registrar
            Button(
                onClick = {
                    mensajeExito = null
                    mensajeError = null

                    scope.launch {
                        val registrado = viewModel.registrarUsuario()

                        if (registrado) {
                            mensajeExito = "¡Registro exitoso! Redirigiendo al login..."
                            kotlinx.coroutines.delay(1500)

                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        } else {
                            mensajeError = "Error en el registro. Verifica los datos."
                        }
                    }
                },
                enabled = !estado.isLoading,
                modifier = Modifier
                    .width(220.dp)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                if (estado.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Registrando...")
                } else {
                    Text("Continuar")
                }
            }

            Spacer(Modifier.height(12.dp))

            // Mensaje de éxito
            mensajeExito?.let {
                Text(
                    it,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Mensaje de error
            mensajeError?.let {
                Text(
                    it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(8.dp))

            // Botón Volver al inicio
            TextButton(
                onClick = { navController.navigate(Screen.Home.route) },
                enabled = !estado.isLoading
            ) {
                Text("Volver al Inicio")
            }
        }
    }
}