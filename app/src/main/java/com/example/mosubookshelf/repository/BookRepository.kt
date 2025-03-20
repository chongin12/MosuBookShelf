package com.example.mosubookshelf.repository

import com.example.mosubookshelf.models.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

interface BookRepository {
    suspend fun getNewBooks(): Result<List<BookDTO>>
    suspend fun getBookDetail(isbn13: String): Result<BookDetailDTO>
    suspend fun searchBooks(query: String): Result<SearchResultDTO>
    suspend fun searchBooks(query: String, page: Int): Result<SearchResultDTO>
}

class RemoteBookRepository: BookRepository {
    private val BASE_URL = "https://api.itbook.store/1.0/"

    private val api: ITBookAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITBookAPI::class.java)
    }

    override suspend fun getNewBooks(): Result<List<BookDTO>> {
        return runCatching { api.getNewBooks().books }
    }

    override suspend fun getBookDetail(isbn13: String): Result<BookDetailDTO> {
        return runCatching { api.getBookDetail(isbn13 = isbn13) }
    }

    override suspend fun searchBooks(query: String): Result<SearchResultDTO> {
        return runCatching { api.searchBooks(query = query) }
    }

    override suspend fun searchBooks(query: String, page: Int): Result<SearchResultDTO> {
        return runCatching { api.searchBooksWithPage(query = query, page = page) }
    }
}

class MockBookRepository: BookRepository {
    override suspend fun getNewBooks(): Result<List<BookDTO>> {
        return Result.success(listOf(BookDTO.sample1, BookDTO.sample1))
    }

    override suspend fun getBookDetail(isbn13: String): Result<BookDetailDTO> {
        return Result.success(BookDetailDTO.sample1)
    }

    override suspend fun searchBooks(query: String): Result<SearchResultDTO> {
        return Result.success(SearchResultDTO.sample1)
    }

    override suspend fun searchBooks(query: String, page: Int): Result<SearchResultDTO> {
        return Result.success(SearchResultDTO.sample1)
    }
}