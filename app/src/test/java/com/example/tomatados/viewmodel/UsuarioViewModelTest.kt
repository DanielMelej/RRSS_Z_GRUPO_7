package com.example.tomatados.viewmodel

import com.example.tomatados.data.remote.dto.LoginResponseDTO
import com.example.tomatados.data.repository.UsuarioRepository
import com.example.tomatados.repository.UserPreferencesRepository
import com.example.tomatados.model.UsuarioUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class UsuarioViewModelTest {

    private lateinit var mockRepo: UsuarioRepository
    private lateinit var mockPrefs: UserPreferencesRepository
    private lateinit var fakePrefsFlow: MutableStateFlow<Map<String, String>>

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: UsuarioViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        mockRepo = mock()
        mockPrefs = mock()

        fakePrefsFlow = MutableStateFlow(
            mapOf(
                "username" to "",
                "fullname" to "",
                "email" to "",
                "token" to ""
            )
        )

        whenever(mockPrefs.userData).thenReturn(fakePrefsFlow)

        viewModel = UsuarioViewModel(mockRepo, mockPrefs)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun validarFormulario_falla_con_campos_vacios() = runTest(testDispatcher) {
        val ok = viewModel.validarFormulario()
        assertFalse(ok)
    }

    @Test
    fun registrarUsuario_success() = runTest(testDispatcher) {
        viewModel.onUserNameChange("daniel")
        viewModel.onPrimerNombreChange("Daniel")
        viewModel.onPrimerApellidoChange("Melej")
        viewModel.onSegundoApellidoChange("Pinto")
        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("123456")
        viewModel.onConfirmPasswordChange("123456")

        whenever(
            mockRepo.registrarUsuario(any(), any(), any(), any(), any(), any(), any())
        ).thenReturn(Result.success("OK"))

        val ok = viewModel.registrarUsuario()
        assertTrue(ok)
    }

    @Test
    fun iniciarSesion_success() = runTest(testDispatcher) {
        viewModel.onUserNameChange("daniel")
        viewModel.onPasswordChange("123456")

        whenever(mockRepo.login(any(), any())).thenReturn(
            Result.success(
                LoginResponseDTO(
                    userName = "daniel",
                    fullName = "Daniel Melej",
                    email = "daniel@test.com",
                    token = "abc123"
                )
            )
        )

        val ok = viewModel.iniciarSesion()
        assertTrue(ok)
        assertEquals("daniel", viewModel.usuarioActivo.value?.userName)
    }

    @Test
    fun iniciarSesion_falla() = runTest(testDispatcher) {
        viewModel.onUserNameChange("daniel")
        viewModel.onPasswordChange("123456")

        whenever(mockRepo.login(any(), any()))
            .thenReturn(Result.failure(Exception("Error")))

        val ok = viewModel.iniciarSesion()

        assertFalse(ok)
        assertNotNull(viewModel.estado.value.errores.password)
    }

    @Test
    fun cerrarSesion_limpia_estado() = runTest(testDispatcher) {
        viewModel.cerrarSesion()

        assertNull(viewModel.usuarioActivo.value)
        assertEquals(UsuarioUiState(), viewModel.estado.value)
    }

    @Test
    fun validarFormulario_emailInvalido() = runTest(testDispatcher) {
        viewModel.onUserNameChange("daniel")
        viewModel.onPrimerNombreChange("Daniel")
        viewModel.onPrimerApellidoChange("Melej")
        viewModel.onSegundoApellidoChange("Pinto")
        viewModel.onEmailChange("correo_malo")
        viewModel.onPasswordChange("123456")
        viewModel.onConfirmPasswordChange("123456")

        val ok = viewModel.validarFormulario()

        assertFalse(ok)
        assertEquals("Correo inválido", viewModel.estado.value.errores.email)
    }

    @Test
    fun validarFormulario_passwordsNoCoinciden() = runTest(testDispatcher) {
        viewModel.onUserNameChange("daniel")
        viewModel.onPrimerNombreChange("Daniel")
        viewModel.onPrimerApellidoChange("Melej")
        viewModel.onSegundoApellidoChange("Pinto")
        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("123456")
        viewModel.onConfirmPasswordChange("000000")

        val ok = viewModel.validarFormulario()

        assertFalse(ok)
        assertEquals("Las contraseñas no coinciden", viewModel.estado.value.errores.confirmPassword)
    }

    @Test
    fun registrarUsuario_failure_actualizaErrores() = runTest(testDispatcher) {
        // Campos válidos
        viewModel.onUserNameChange("daniel")
        viewModel.onPrimerNombreChange("Daniel")
        viewModel.onPrimerApellidoChange("Melej")
        viewModel.onSegundoApellidoChange("Pinto")
        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("123456")
        viewModel.onConfirmPasswordChange("123456")

        whenever(
            mockRepo.registrarUsuario(any(), any(), any(), any(), any(), any(), any())
        ).thenReturn(Result.failure(Exception("Error de API")))

        val ok = viewModel.registrarUsuario()

        assertFalse(ok)
        assertFalse(viewModel.estado.value.isLoading)
    }

    @Test
    fun validarLogin_errorPassword() = runTest(testDispatcher) {
        viewModel.onUserNameChange("daniel")
        viewModel.onPasswordChange("")

        val ok = viewModel.validarLogin()

        assertFalse(ok)
        assertEquals("Ingresa tu contraseña", viewModel.estado.value.errores.password)
    }

    
}