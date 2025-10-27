package com.example.tomatados.model

// Modelo real de usuario guardado en memoria o DataStore
data class Usuario(
    val userName: String,
    val fullName: String,
    val email: String,
    val password: String
)