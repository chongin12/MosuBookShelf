package com.example.mosubookshelf.repository

import com.example.mosubookshelf.models.*

interface BookRepository {
    suspend fun getNewBooks(): Result<List<BookDTO>>
    suspend fun getBookDetail(isbn13: String): Result<BookDetailDTO>
    suspend fun searchBooks(query: String): Result<SearchResultDTO>
    suspend fun searchBooks(query: String, page: Int): Result<SearchResultDTO>
}