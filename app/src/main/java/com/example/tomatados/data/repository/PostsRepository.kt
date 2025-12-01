package com.example.tomatados.data.repository

import android.util.Log
import com.example.tomatados.data.remote.JsonPlaceholderClient
import com.example.tomatados.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostsRepository {

    private val apiService = JsonPlaceholderClient.postsApiService

    suspend fun obtenerPosts(): Result<List<Post>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.obtenerPosts()

            if (response.isSuccessful && response.body() != null) {
                Log.d("PostsRepository", "✅ Posts obtenidos: ${response.body()!!.size}")
                Result.success(response.body()!!)
            } else {
                Log.e("PostsRepository", "❌ Error: ${response.code()}")
                Result.failure(Exception("Error al obtener posts"))
            }
        } catch (e: Exception) {
            Log.e("PostsRepository", "❌ Excepción", e)
            Result.failure(e)
        }
    }

    suspend fun obtenerPostPorId(id: Int): Result<Post> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.obtenerPostPorId(id)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Post no encontrado"))
            }
        } catch (e: Exception) {
            Log.e("PostsRepository", "Excepción al obtener post", e)
            Result.failure(e)
        }
    }
}