Tomatados 🍅
Descripción general

Tomatados es una aplicación móvil desarrollada en Kotlin con Android Studio y Jetpack Compose.
Propone una red social lúdica donde los usuarios registrados pueden evaluarse entre sí con una nota de 1 a 5, además de agregar etiquetas y comentarios.
La versión actual implementa el flujo de registro, inicio y cierre de sesión, con persistencia local mediante DataStore Preferences y navegación usando Jetpack Navigation.

Objetivos

Diseñar una interfaz moderna con Jetpack Compose.
Aplicar el patrón MVVM para separar la lógica de la vista.
Gestionar el estado del usuario con DataStore.
Implementar una navegación fluida y desacoplada.
Sentar la base para el futuro sistema de calificaciones y comentarios.

Arquitectura

La app sigue una estructura MVVM compuesta por:
Model: Clases Usuario, UsuarioErrores, UsuarioUiState para el manejo de datos y validaciones.
Repository: UserPreferencesRepository gestiona la persistencia local.
ViewModel: Controla el estado y la navegación entre pantallas.
UI: Pantallas creadas con Compose (HomeScreen, LoginScreen, RegistroScreen).

Tecnologías

Kotlin
Jetpack Compose
Material 3
Navigation Compose
DataStore Preferences
Arquitectura MVVM

Flujo actual

Home: Pantalla de bienvenida con acceso a login o registro.
Registro: Creación de usuario con validación de campos.
Login: Inicio de sesión con datos persistidos.
Pantalla principal: acceso al contenido base (en desarrollo).

Desarrollo futuro

Sistema de calificación y comentarios entre usuarios.
Incorporación de etiquetas y perfiles personalizados.

Integración con Firebase o API externa.

Diseño visual avanzado y modo oscuro.
