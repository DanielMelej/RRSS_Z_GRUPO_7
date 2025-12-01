package com.example.tomatados.data.remote.api

import com.example.tomatados.data.remote.dto.LoginRequestDTO
import com.example.tomatados.data.remote.dto.LoginResponseDTO
import com.example.tomatados.data.remote.dto.RegistroRequestDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioApiService {

    /**
     * Endpoint: POST /api/usuarios/registro-kotlin
     * Registra un nuevo usuario con campos separados
     */
    @POST("api/usuarios/registro-kotlin")
    suspend fun registrarUsuario(
        @Body request: RegistroRequestDTO
    ): Response<Unit>

    /**
     * Endpoint: POST /api/usuarios/login-simple
     * Inicia sesión y obtiene token JWT
     */
    @POST("api/usuarios/login-simple")
    suspend fun login(
        @Body request: LoginRequestDTO
    ): Response<LoginResponseDTO>
}