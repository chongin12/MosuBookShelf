package com.example.mosubookshelf.repository.remote

import com.example.mosubookshelf.models.*
import com.example.mosubookshelf.repository.BookRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
