package com.example.mosubookshelf.repository.remote

import com.example.mosubookshelf.models.*
import retrofit2.http.GET
import retrofit2.http.Path

interface ITBookAPI {
    @GET("new")
    suspend fun getNewBooks(): NewBooksResponse

    @GET("books/{isbn13}")
    suspend fun getBookDetail(@Path(value = "isbn13") isbn13: String): BookDetailDTO

    @GET("search/{query}")
    suspend fun searchBooks(@Path(value = "query") query: String): SearchResultDTO

    @GET("search/{query}/{page}")
    suspend fun searchBooksWithPage(
        @Path(value = "query") query: String,
        @Path(value = "page") page: Int,
    ): SearchResultDTO
}