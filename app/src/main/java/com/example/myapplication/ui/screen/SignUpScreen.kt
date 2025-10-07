package com.example.myapplication.ui.screen

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.myapplication.navigation.Screen
import com.example.myapplication.viewmodel.MainViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    var username by rememberSaveable { mutableStateOf("") }
    var fullName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var userError by rememberSaveable { mutableStateOf(false) }
    var fullNameError by rememberSaveable { mutableStateOf(false) }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var passError by rememberSaveable { mutableStateOf(false) }
    var confirmPassError by rememberSaveable { mutableStateOf(false) }

    var passVisible by rememberSaveable { mutableStateOf(false) }
    var confirmPassVisible by rememberSaveable { mutableStateOf(false) }

    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scroll),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.tomatito_registro),
            contentDescription = "Ilustración de registro con tomate",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(Modifier.height(8.dp))
        Text("Registro de usuario", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(16.dp))

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

        Spacer(Modifier.height(12.dp))

        // Nombre completo
        OutlinedTextField(
            value = fullName,
            onValueChange = {
                fullName = it
                if (fullNameError && it.isNotBlank()) fullNameError = false
            },
            label = { Text("Nombre completo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = fullNameError,
            supportingText = { if (fullNameError) Text("Ingresa tu nombre completo") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            )
        )

        Spacer(Modifier.height(12.dp))

        // Correo
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                if (emailError && it.isNotBlank()) emailError = false
            },
            label = { Text("Correo electrónico") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = emailError,
            supportingText = { if (emailError) Text("Ingresa un correo válido") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            )
        )

        Spacer(Modifier.height(12.dp))

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
            supportingText = { if (passError) Text("Mínimo 6 caracteres") },
            visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val texto = if (passVisible) "Ocultar" else "Mostrar"
                TextButton(onClick = { passVisible = !passVisible }) { Text(texto) }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            )
        )

        Spacer(Modifier.height(12.dp))

        // Confirmar contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                if (confirmPassError && it.isNotBlank()) confirmPassError = false
            },
            label = { Text("Confirmar contraseña") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = confirmPassError,
            supportingText = { if (confirmPassError) Text("Debe coincidir con la contraseña") },
            visualTransformation = if (confirmPassVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val texto = if (confirmPassVisible) "Ocultar" else "Mostrar"
                TextButton(onClick = { confirmPassVisible = !confirmPassVisible }) { Text(texto) }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            )
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                userError = username.trim().isBlank()
                fullNameError = fullName.trim().isBlank()
                emailError = email.trim().isBlank() ||
                        !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
                passError = password.length < 6
                confirmPassError = confirmPassword != password

                if (!userError && !fullNameError && !emailError && !passError && !confirmPassError) {
                    viewModel.navigateTo(Screen.Home)
                }
            },
            enabled = username.trim().isNotBlank() &&
                    fullName.trim().isNotBlank() &&
                    email.trim().isNotBlank() &&
                    password.isNotBlank() &&
                    confirmPassword.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear cuenta")
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { viewModel.navigateTo(Screen.Home) }) {
            Text("Volver al inicio")
        }
    }
}