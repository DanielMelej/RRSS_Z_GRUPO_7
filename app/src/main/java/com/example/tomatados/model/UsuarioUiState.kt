package com.example.tomatados.model

data class UsuarioUiState(
    val userName: String = "",

    // ✅ NUEVOS CAMPOS SEPARADOS
    val primerNombre: String = "",
    val segundoNombre: String = "",
    val primerApellido: String = "",
    val segundoApellido: String = "",

    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errores: UsuarioErrores = UsuarioErrores(),
    val isLoading: Boolean = false,
    val token: String? = null
)