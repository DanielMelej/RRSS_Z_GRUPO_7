package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.UsuarioErrores
import com.example.myapplication.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel: ViewModel() {

    private val _estado = MutableStateFlow(UsuarioUiState())

    val estado: StateFlow<UsuarioUiState> = _estado

    fun onUserNameChange(valor: String) {
        _estado.update { it.copy(userName = valor, errores = it.errores.copy(userName = null)) }
    }

    fun onFullNameChange(valor: String) {
        _estado.update { it.copy(fullName = valor, errores = it.errores.copy(fullName = null)) }
    }

    fun onEmailChange(valor: String) {
        _estado.update { it.copy(email = valor, errores = it.errores.copy(email = null)) }
    }

    fun onPasswordChange(valor: String) {
        _estado.update { it.copy(password = valor, errores = it.errores.copy(password = null)) }
    }

    fun onConfirmPasswordChange(valor: String) {
        _estado.update {
            it.copy(
                confirmPassword = valor,
                errores = it.errores.copy(confirmPassword = null)
            )
        }
    }

    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            userName = if (estadoActual.userName.isBlank()) "Campo Obligatorio" else null,
            fullName = if (estadoActual.fullName.isBlank()) "Campo Obligatorio" else null,
            email = if (!estadoActual.email.contains("@")) "Correo inválido" else null,
            password = if (estadoActual.password.length < 6) "Debe tener al menos 6 caracteres" else null,
            confirmPassword = if(estadoActual.confirmPassword != estadoActual.password) "Las contraseñas deben coincidir" else null
        )

        val hayErrores = listOfNotNull(
            errores.userName,
            errores.fullName,
            errores.email,
            errores.password,
            errores.confirmPassword
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }

    fun validarLogin(): Boolean {
        val s = _estado.value
        val errs = UsuarioErrores(
            userName = if (s.userName.trim().isEmpty()) "Campo obligatorio" else null,
            password = if (s.password.isEmpty()) "Ingresa tu contraseña" else null
        )

        val hayErrores = listOfNotNull(
            errs.userName, errs.password
        ).isNotEmpty()

        // Solo actualizamos los errores que nos interesan en login
        _estado.update {
            it.copy(
                errores = it.errores.copy(
                    userName = errs.userName,
                    password = errs.password
                )
            )
        }

        return !hayErrores
    }

}