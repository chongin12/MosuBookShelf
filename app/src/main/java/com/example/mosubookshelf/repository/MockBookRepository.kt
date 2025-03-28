package com.example.mosubookshelf.repository

import com.example.mosubookshelf.models.*

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