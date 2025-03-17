package com.example.mosubookshelf.repository

import com.example.mosubookshelf.models.BookDTO
import com.example.mosubookshelf.models.NewBooksResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface ITBookAPI {
    @GET("new")
    suspend fun getNewBooks(): NewBooksResponse
}

interface BookRepository {
    suspend fun getNewBooks(): List<BookDTO>
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
}

class MockBookRepository: BookRepository {
    override suspend fun getNewBooks(): List<BookDTO> {
        return listOf(BookDTO.sample1, BookDTO.sample1)
    }
}