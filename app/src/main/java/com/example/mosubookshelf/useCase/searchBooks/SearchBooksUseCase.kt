package com.example.mosubookshelf.useCase.searchBooks

import com.example.mosubookshelf.models.SearchResultVO
import com.example.mosubookshelf.repository.BookCacheRepository
import com.example.mosubookshelf.repository.BookRepository
import com.example.mosubookshelf.useCase.convert

interface SearchBooksUseCase {
    suspend fun searchBooks(query: String): Result<SearchResultVO>
    suspend fun searchBooksWithPage(query: String, page: Int): Result<SearchResultVO>
}
