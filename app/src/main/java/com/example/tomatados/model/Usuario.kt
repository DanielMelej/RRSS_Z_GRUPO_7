package com.example.tomatados.model

// Modelo de usuario que representa los datos en la app
data class Usuario(
    val id: Int? = null,          // 🆕 ID del usuario (viene del servidor)
    val userName: String,
    val fullName: String,
    val email: String,
    val token: String? = null     // 🆕 Token JWT (opcional)
)