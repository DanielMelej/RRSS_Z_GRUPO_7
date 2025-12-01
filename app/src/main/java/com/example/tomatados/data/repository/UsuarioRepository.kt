package com.example.tomatados.data.repository

import android.util.Log
import com.example.tomatados.data.remote.RetrofitClient
import com.example.tomatados.data.remote.dto.LoginRequestDTO
import com.example.tomatados.data.remote.dto.LoginResponseDTO
import com.example.tomatados.data.remote.dto.RegistroRequestDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsuarioRepository {

    private val apiService = RetrofitClient.usuarioApiService

    suspend fun registrarUsuario(
        userName: String,
        primerNombre: String,
        segundoNombre: String,
        primerApellido: String,
        segundoApellido: String,
        email: String,
        password: String
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val request = RegistroRequestDTO(
                username = userName,
                primerNombre = primerNombre,
                segundoNombre = if (segundoNombre.isBlank()) null else segundoNombre,
                primerApellido = primerApellido,
                segundoApellido = segundoApellido,
                email = email,
                contrasena = password
            )

            Log.d("UsuarioRepository", "📤 Enviando registro:")
            Log.d("UsuarioRepository", "   username: $userName")
            Log.d("UsuarioRepository", "   primerNombre: $primerNombre")
            Log.d("UsuarioRepository", "   segundoNombre: $segundoNombre")
            Log.d("UsuarioRepository", "   primerApellido: $primerApellido")
            Log.d("UsuarioRepository", "   segundoApellido: $segundoApellido")
            Log.d("UsuarioRepository", "   email: $email")

            val response = apiService.registrarUsuario(request)

            Log.d("UsuarioRepository", "📥 Respuesta: ${response.code()}")

            if (response.isSuccessful || response.code() == 201) {
                Log.d("UsuarioRepository", "✅ Usuario registrado correctamente")
                Result.success("Usuario registrado correctamente")
            } else {
                val errorBody = response.errorBody()?.string() ?: "Error desconocido"
                Log.e("UsuarioRepository", "❌ Error ${response.code()}: $errorBody")
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Log.e("UsuarioRepository", "❌ Excepción en registro", e)
            Result.failure(e)
        }
    }

    suspend fun login(
        emailOUsername: String,
        password: String
    ): Result<LoginResponseDTO> = withContext(Dispatchers.IO) {
        try {
            val request = LoginRequestDTO(emailOUsername, password)
            val response = apiService.login(request)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Credenciales inválidas"
                Log.e("UsuarioRepository", "Error login: $errorBody")
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Log.e("UsuarioRepository", "Excepción en login", e)
            Result.failure(e)
        }
    }
}