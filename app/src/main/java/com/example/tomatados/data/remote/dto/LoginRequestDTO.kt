package com.example.tomatados.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para enviar credenciales de login a la API
 */
data class LoginRequestDTO(
    @SerializedName("emailOUsername")
    val emailOUsername: String,

    @SerializedName("contrasena")
    val contrasena: String
)