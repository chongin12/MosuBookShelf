package com.example.mosubookshelf.repository.local

import com.example.mosubookshelf.models.*
import com.example.mosubookshelf.repository.BookRepository

class LocalBookRepository: BookRepository {

    override suspend fun getNewBooks(): Result<List<BookDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun getBookDetail(isbn13: String): Result<BookDetailDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun searchBooks(query: String): Result<SearchResultDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun searchBooks(query: String, page: Int): Result<SearchResultDTO> {
        TODO("Not yet implemented")
    }
}