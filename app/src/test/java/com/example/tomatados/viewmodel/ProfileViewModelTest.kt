package com.example.tomatados.viewmodel

import android.net.Uri
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class ProfileViewModelTest {

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setup() {
        viewModel = ProfileViewModel()
    }

    @Test
    fun setImage_asignaUriCorrectamente() {
        val fakeUri: Uri = mock()

        viewModel.setImage(fakeUri)

        assertEquals(fakeUri, viewModel.imageUri.value)
    }

    @Test
    fun setImage_nullLimpiaImagen() {
        viewModel.setImage(null)

        assertNull(viewModel.imageUri.value)
    }
}
