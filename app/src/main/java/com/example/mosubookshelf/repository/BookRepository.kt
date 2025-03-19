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
}

interface BookRepository {
    suspend fun getNewBooks(): List<BookDTO>
    suspend fun getBookDetail(isbn13: String): BookDetailDTO
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

    override suspend fun getNewBooks(): List<BookDTO> {
        return api.getNewBooks().books
    }

    override suspend fun getBookDetail(isbn13: String): BookDetailDTO {
        return api.getBookDetail(isbn13 = isbn13)
    }
}

class MockBookRepository: BookRepository {
    override suspend fun getNewBooks(): List<BookDTO> {
        return listOf(BookDTO.sample1, BookDTO.sample1)
    }

    override suspend fun getBookDetail(isbn13: String): BookDetailDTO {
        return BookDetailDTO.sample1
    }
}