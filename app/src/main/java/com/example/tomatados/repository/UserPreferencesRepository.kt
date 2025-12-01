package com.example.tomatados.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extensión global para acceder al DataStore desde el contexto
val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferencesRepository(private val context: Context) {

    companion object {
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val FULLNAME_KEY = stringPreferencesKey("fullname")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token") // 🆕 Agregar token
    }

    // 🔹 Guarda todos los datos del usuario (incluyendo el token)
    suspend fun saveUser(
        userName: String,
        fullName: String,
        email: String,
        token: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = userName
            prefs[FULLNAME_KEY] = fullName
            prefs[EMAIL_KEY] = email
            prefs[TOKEN_KEY] = token
        }
    }

    // 🔹 Obtiene los datos del usuario guardado (flujo observable)
    val userData: Flow<Map<String, String>> = context.dataStore.data.map { prefs ->
        mapOf(
            "username" to (prefs[USERNAME_KEY] ?: ""),
            "fullname" to (prefs[FULLNAME_KEY] ?: ""),
            "email" to (prefs[EMAIL_KEY] ?: ""),
            "token" to (prefs[TOKEN_KEY] ?: "")
        )
    }

    // 🔹 Obtiene solo el token
    val token: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[TOKEN_KEY]
    }

    // 🔹 Limpia todos los datos del usuario (cerrar sesión)
    suspend fun clearUser() {
        context.dataStore.edit { prefs -> prefs.clear() }
    }
}