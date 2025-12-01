package com.example.tomatados.model

data class UsuarioErrores(
    val userName: String? = null,

    // ✅ NUEVOS ERRORES PARA CAMPOS SEPARADOS
    val primerNombre: String? = null,
    val segundoNombre: String? = null,
    val primerApellido: String? = null,
    val segundoApellido: String? = null,

    val email: String? = null,
    val password: String? = null,
    val confirmPassword: String? = null
)