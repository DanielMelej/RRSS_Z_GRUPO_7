package com.example.tomatados.data.remote.api

import com.example.tomatados.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceholderApiService {

    @GET("posts")
    suspend fun obtenerPosts(): Response<List<Post>>

    @GET("posts/{id}")
    suspend fun obtenerPostPorId(
        @Path("id") id: Int
    ): Response<Post>
}