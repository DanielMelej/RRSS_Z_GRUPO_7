package com.example.tomatados.data.remote

import com.example.tomatados.data.remote.api.UsuarioApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Objeto singleton que configura y proporciona la instancia de Retrofit
 */
object RetrofitClient {

    // 🔹 URL base de tu API
    // Para emulador Android: usa 10.0.2.2 en lugar de localhost
    private const val BASE_URL = "http://10.0.2.2:8081/"

    // 🔹 Cliente HTTP con logging (para ver las peticiones en el log)
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // 🔹 Instancia de Retrofit
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 🔹 Servicio de API de usuarios
    val usuarioApiService: UsuarioApiService by lazy {
        retrofit.create(UsuarioApiService::class.java)
    }
}