package com.example.tomatados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tomatados.model.Usuario
import com.example.tomatados.model.UsuarioErrores
import com.example.tomatados.model.UsuarioUiState
import com.example.tomatados.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserPreferencesRepository(application)

    private val _estado = MutableStateFlow(UsuarioUiState())
    val estado: StateFlow<UsuarioUiState> = _estado.asStateFlow()

    private val _usuarioActivo = MutableStateFlow<Usuario?>(null)
    val usuarioActivo: StateFlow<Usuario?> = _usuarioActivo.asStateFlow()

    init {
        // Al iniciar, intentamos recuperar el usuario persistido desde DataStore
        viewModelScope.launch {
            repository.userData.collect { data ->
                val userName = data["username"] ?: ""
                val fullName = data["fullname"] ?: ""
                val email = data["email"] ?: ""
                val password = data["password"] ?: ""

                if (userName.isNotEmpty()) {
                    _usuarioActivo.value = Usuario(userName, fullName, email, password)

                    // También actualizamos el estado visible (por ejemplo en MainScreen)
                    _estado.update {
                        it.copy(
                            userName = userName,
                            fullName = fullName,
                            email = email,
                            password = password
                        )
                    }
                }
            }
        }
    }

    // --- Actualizaciones de campos de texto ---
    fun onUserNameChange(v: String) { _estado.update { it.copy(userName = v, errores = it.errores.copy(userName = null)) } }
    fun onFullNameChange(v: String) { _estado.update { it.copy(fullName = v, errores = it.errores.copy(fullName = null)) } }
    fun onEmailChange(v: String) { _estado.update { it.copy(email = v, errores = it.errores.copy(email = null)) } }
    fun onPasswordChange(v: String) { _estado.update { it.copy(password = v, errores = it.errores.copy(password = null)) } }
    fun onConfirmPasswordChange(v: String) { _estado.update { it.copy(confirmPassword = v, errores = it.errores.copy(confirmPassword = null)) } }

    // --- Validaciones ---
    fun validarFormulario(): Boolean {
        val e = _estado.value
        val errores = UsuarioErrores(
            userName = if (e.userName.isBlank()) "Campo obligatorio" else null,
            fullName = if (e.fullName.isBlank()) "Campo obligatorio" else null,
            email = if (!e.email.contains("@")) "Correo inválido" else null,
            password = if (e.password.length < 6) "Mínimo 6 caracteres" else null,
            confirmPassword = if (e.confirmPassword != e.password) "No coinciden" else null
        )
        _estado.update { it.copy(errores = errores) }
        return listOfNotNull(
            errores.userName, errores.fullName, errores.email, errores.password, errores.confirmPassword
        ).isEmpty()
    }

    fun validarLogin(): Boolean {
        val s = _estado.value
        val errs = UsuarioErrores(
            userName = if (s.userName.trim().isEmpty()) "Campo obligatorio" else null,
            password = if (s.password.isEmpty()) "Ingresa tu contraseña" else null
        )

        val hayErrores = listOfNotNull(errs.userName, errs.password).isNotEmpty()

        _estado.update {
            it.copy(
                errores = it.errores.copy(
                    userName = errs.userName,
                    password = errs.password
                )
            )
        }

        return !hayErrores
    }

    // --- Registro ---
    fun registrarUsuario(): Boolean {
        val e = _estado.value
        if (!validarFormulario()) return false

        viewModelScope.launch {
            repository.saveUser(e.userName, e.fullName, e.email, e.password)
            println("✅ Usuario guardado en DataStore: ${e.userName}, ${e.fullName}, ${e.email}, ${e.password}")
        }

        _usuarioActivo.value = Usuario(e.userName, e.fullName, e.email, e.password)
        return true
    }

    // --- Inicio de sesión ---
    suspend fun iniciarSesion(): Boolean {
        val e = _estado.value
        val data = repository.userData.first() // obtiene los datos actuales del DataStore

        val storedUser = data["username"] ?: ""
        val storedEmail = data["email"] ?: ""
        val storedPassword = data["password"] ?: ""

        println("📦 DataStore contiene: $data")

        return if ((e.userName == storedUser || e.userName == storedEmail) &&
            e.password == storedPassword
        ) {
            _usuarioActivo.value = Usuario(
                storedUser,
                data["fullname"] ?: "",
                storedEmail,
                storedPassword
            )
            true
        } else {
            false
        }
    }

    // --- Cargar datos del usuario actual ---
    fun cargarUsuario() {
        viewModelScope.launch {
            repository.userData.collect { data ->
                val userName = data["username"] ?: ""
                val fullName = data["fullname"] ?: ""
                val email = data["email"] ?: ""

                if (userName.isNotEmpty()) {
                    _estado.update {
                        it.copy(
                            userName = userName,
                            fullName = fullName,
                            email = email
                        )
                    }
                }
            }
        }
    }

    // --- Cerrar sesión ---
    fun cerrarSesion() {
        viewModelScope.launch { repository.clearUser() }
        _usuarioActivo.value = null
    }
}