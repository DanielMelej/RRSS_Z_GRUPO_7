package com.example.tomatados.viewmodel

import com.example.tomatados.data.repository.PostsRepository
import com.example.tomatados.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class PostsViewModelTest {

    private lateinit var mockRepo: PostsRepository
    private lateinit var viewModel: PostsViewModel

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        mockRepo = mock()
        viewModel = PostsViewModel(mockRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun cargarPosts_success() = runTest(dispatcher) {
        val listaFake = listOf(
            Post(1, 1, "titulo 1", "contenido 1"),
            Post(2, 1, "titulo 2", "contenido 2")
        )

        whenever(mockRepo.obtenerPosts()).thenReturn(Result.success(listaFake))

        viewModel.cargarPosts()
        advanceUntilIdle()

        assertEquals(listaFake, viewModel.posts.value)
        assertFalse(viewModel.isLoading.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun cargarPosts_error() = runTest(dispatcher) {
        whenever(mockRepo.obtenerPosts()).thenReturn(Result.failure(Exception("Error")))

        viewModel.cargarPosts()
        advanceUntilIdle()

        assertTrue(viewModel.error.value?.contains("Error") == true)
        assertTrue(viewModel.posts.value.isEmpty())
        assertFalse(viewModel.isLoading.value)
    }
}