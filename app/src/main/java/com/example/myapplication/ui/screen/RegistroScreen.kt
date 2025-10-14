package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.viewmodel.UsuarioViewModel

@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: UsuarioViewModel

) {
    val estado by viewModel.estado.collectAsState()

    Column (
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Registro de Usuario", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(
            value = estado.userName,
            onValueChange = viewModel::onUserNameChange,
            label = {Text("Nombre de usuario")},
            isError = estado.errores.userName != null,
            supportingText = {
                estado.errores.userName?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.fullName,
            onValueChange = viewModel::onFullNameChange,
            label = {Text("Nombre completo")},
            isError = estado.errores.fullName!= null,
            supportingText = {
                estado.errores.fullName?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.email,
            onValueChange = viewModel::onEmailChange,
            label = {Text("Correo electronico")},
            isError = estado.errores.email!= null,
            supportingText = {
                estado.errores.email?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.password,
            onValueChange = viewModel::onPasswordChange,
            label = {Text("Ingrese una contraseña")},
            visualTransformation = PasswordVisualTransformation(),
            isError = estado.errores.password!= null,
            supportingText = {
                estado.errores.password?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.confirmPassword,
            onValueChange = viewModel::onConfirmPasswordChange,
            label = {Text("Confirma contraseña")},
            visualTransformation = PasswordVisualTransformation(),
            isError = estado.errores.confirmPassword!= null,
            supportingText = {
                estado.errores.confirmPassword?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (viewModel.validarFormulario()) {
                    navController.navigate("resumen")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }


        Button(onClick = { navController.navigate("inicio") },
            modifier = Modifier.fillMaxWidth()) {
            Text("Volver a Inicio")
        }
    }
}