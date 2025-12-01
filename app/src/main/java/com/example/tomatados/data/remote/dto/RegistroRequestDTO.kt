package com.example.tomatados.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RegistroRequestDTO(
    @SerializedName("username")
    val username: String,

    @SerializedName("primerNombre")
    val primerNombre: String,

    @SerializedName("segundoNombre")
    val segundoNombre: String?,

    @SerializedName("primerApellido")
    val primerApellido: String,

    @SerializedName("segundoApellido")
    val segundoApellido: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("contrasena")
    val contrasena: String
)