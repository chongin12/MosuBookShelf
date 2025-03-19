package com.example.mosubookshelf.searchBooks

import com.example.mosubookshelf.models.SearchResultVO

interface SearchBooksUseCase {
    suspend fun searchBooks(query: String): List<SearchResultVO>
}

class MockSearchBooksUseCase: SearchBooksUseCase {
    override suspend fun searchBooks(query: String): List<SearchResultVO> {
        return listOf(SearchResultVO.sample1)
    }
}