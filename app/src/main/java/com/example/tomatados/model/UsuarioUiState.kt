package com.example.tomatados.model

data class UsuarioUiState (
    val userName: String = "",
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errores: UsuarioErrores = UsuarioErrores()
)


