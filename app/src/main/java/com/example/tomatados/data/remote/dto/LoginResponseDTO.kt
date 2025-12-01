package com.example.tomatados.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO que recibe la respuesta del login desde la API
 * Contiene el token JWT y datos del usuario
 */
data class LoginResponseDTO(
    @SerializedName("token")
    val token: String,

    @SerializedName("userName")
    val userName: String,

    @SerializedName("fullName")
    val fullName: String,

    @SerializedName("email")
    val email: String
)