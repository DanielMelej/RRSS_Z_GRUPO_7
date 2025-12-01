package com.example.tomatados.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tomatados.data.repository.PostsRepository
import com.example.tomatados.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostsViewModel : ViewModel {

    // Repositorio usado por la app y por los tests
    private val repository: PostsRepository

    // Constructor REAL (App)
    constructor() {
        repository = PostsRepository()
    }

    // Constructor para TESTS (Mock)
    constructor(repo: PostsRepository) {
        repository = repo
    }

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun cargarPosts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val resultado = repository.obtenerPosts()

            resultado.fold(
                onSuccess = { listaPosts ->
                    _posts.value = listaPosts
                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _error.value = exception.message ?: "Error desconocido"
                    _isLoading.value = false
                }
            )
        }
    }
}