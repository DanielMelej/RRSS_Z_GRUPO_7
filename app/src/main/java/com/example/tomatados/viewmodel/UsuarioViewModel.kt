package com.example.tomatados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tomatados.data.repository.UsuarioRepository
import com.example.tomatados.model.Usuario
import com.example.tomatados.model.UsuarioErrores
import com.example.tomatados.model.UsuarioUiState
import com.example.tomatados.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel : AndroidViewModel {

    // Repositorios (inicializados en constructor real o constructor para testing)
    lateinit var apiRepository: UsuarioRepository
    lateinit var prefsRepository: UserPreferencesRepository

    // Constructor PRINCIPAL (APP REAL)
    constructor(application: Application) : super(application) {
        apiRepository = UsuarioRepository()
        prefsRepository = UserPreferencesRepository(application)
        iniciarObservacionPrefs()
    }

    // Constructor SECUNDARIO (UNIT TESTS)
    constructor(
        mockRepo: UsuarioRepository,
        mockPrefs: UserPreferencesRepository
    ) : super(Application()) {
        apiRepository = mockRepo
        prefsRepository = mockPrefs
        iniciarObservacionPrefs()
    }

    // State
    private val _estado = MutableStateFlow(UsuarioUiState())
    val estado: StateFlow<UsuarioUiState> = _estado

    private val _usuarioActivo = MutableStateFlow<Usuario?>(null)
    val usuarioActivo: StateFlow<Usuario?> = _usuarioActivo

    // Observa DataStore en ambos constructores
    private fun iniciarObservacionPrefs() {
        viewModelScope.launch {
            prefsRepository.userData.collect { data ->
                val userName = data["username"] ?: ""
                val fullName = data["fullname"] ?: ""
                val email = data["email"] ?: ""
                val token = data["token"] ?: ""

                if (userName.isNotEmpty() && token.isNotEmpty()) {

                    _usuarioActivo.value = Usuario(
                        userName = userName,
                        fullName = fullName,
                        email = email,
                        token = token
                    )

                    _estado.update {
                        it.copy(
                            userName = userName,
                            email = email,
                            token = token
                        )
                    }
                }
            }
        }
    }

    // ======================================
    // ACTUALIZACIÓN DE CAMPOS
    // ======================================
    fun onUserNameChange(v: String) {
        _estado.update { it.copy(userName = v, errores = it.errores.copy(userName = null)) }
    }

    fun onPrimerNombreChange(v: String) {
        _estado.update { it.copy(primerNombre = v, errores = it.errores.copy(primerNombre = null)) }
    }

    fun onSegundoNombreChange(v: String) {
        _estado.update { it.copy(segundoNombre = v) }
    }

    fun onPrimerApellidoChange(v: String) {
        _estado.update { it.copy(primerApellido = v, errores = it.errores.copy(primerApellido = null)) }
    }

    fun onSegundoApellidoChange(v: String) {
        _estado.update { it.copy(segundoApellido = v, errores = it.errores.copy(segundoApellido = null)) }
    }

    fun onEmailChange(v: String) {
        _estado.update { it.copy(email = v, errores = it.errores.copy(email = null)) }
    }

    fun onPasswordChange(v: String) {
        _estado.update { it.copy(password = v, errores = it.errores.copy(password = null)) }
    }

    fun onConfirmPasswordChange(v: String) {
        _estado.update { it.copy(confirmPassword = v, errores = it.errores.copy(confirmPassword = null)) }
    }

    // ======================================
    // VALIDACIÓN REGISTRO
    // ======================================
    fun validarFormulario(): Boolean {
        val e = estado.value

        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

        val errores = UsuarioErrores(
            userName = if (e.userName.isBlank()) "Campo obligatorio" else null,
            primerNombre = if (e.primerNombre.isBlank()) "Campo obligatorio" else null,
            primerApellido = if (e.primerApellido.isBlank()) "Campo obligatorio" else null,
            segundoApellido = if (e.segundoApellido.isBlank()) "Campo obligatorio" else null,
            email =
                if (e.email.isBlank()) "Campo obligatorio"
                else if (!emailRegex.matches(e.email)) "Correo inválido"
                else null,
            password = if (e.password.length < 6) "Mínimo 6 caracteres" else null,
            confirmPassword = if (e.confirmPassword != e.password) "Las contraseñas no coinciden" else null
        )

        _estado.update { it.copy(errores = errores) }

        return errores.userName == null &&
                errores.primerNombre == null &&
                errores.primerApellido == null &&
                errores.segundoApellido == null &&
                errores.email == null &&
                errores.password == null &&
                errores.confirmPassword == null
    }

    // ======================================
    // VALIDAR LOGIN
    // ======================================
    fun validarLogin(): Boolean {
        val e = estado.value

        val errs = UsuarioErrores(
            userName = if (e.userName.isBlank()) "Campo obligatorio" else null,
            password = if (e.password.isBlank()) "Ingresa tu contraseña" else null
        )

        _estado.update { it.copy(errores = errs) }

        return errs.userName == null && errs.password == null
    }

    // ======================================
    // REGISTRO API
    // ======================================
    suspend fun registrarUsuario(): Boolean {
        if (!validarFormulario()) return false

        _estado.update { it.copy(isLoading = true) }

        val e = estado.value
        val resultado = apiRepository.registrarUsuario(
            e.userName, e.primerNombre, e.segundoNombre,
            e.primerApellido, e.segundoApellido,
            e.email, e.password
        )

        _estado.update { it.copy(isLoading = false) }

        return resultado.isSuccess
    }

    // ======================================
    // LOGIN API
    // ======================================
    suspend fun iniciarSesion(): Boolean {
        if (!validarLogin()) return false

        _estado.update { it.copy(isLoading = true) }

        val e = estado.value
        val resultado = apiRepository.login(e.userName, e.password)

        _estado.update { it.copy(isLoading = false) }

        return resultado.fold(
            onSuccess = { login ->
                viewModelScope.launch {
                    prefsRepository.saveUser(
                        login.userName, login.fullName, login.email, login.token
                    )
                }

                _usuarioActivo.value = Usuario(
                    userName = login.userName,
                    fullName = login.fullName,
                    email = login.email,
                    token = login.token
                )

                true
            },
            onFailure = {
                _estado.update {
                    it.copy(errores = it.errores.copy(password = "Credenciales incorrectas"))
                }
                false
            }
        )
    }

    // ======================================
    // CERRAR SESIÓN
    // ======================================
    fun cerrarSesion() {
        viewModelScope.launch {
            prefsRepository.clearUser()
            _usuarioActivo.value = null
            _estado.value = UsuarioUiState()
        }
    }
}