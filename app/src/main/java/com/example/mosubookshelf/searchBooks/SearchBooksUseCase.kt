package com.example.mosubookshelf.searchBooks

import com.example.mosubookshelf.models.SearchResultVO

interface SearchBooksUseCase {
    suspend fun searchBooks(query: String): SearchResultVO
    suspend fun searchBooksWithPage(query: String, page: Int): SearchResultVO
}

class MockSearchBooksUseCase: SearchBooksUseCase {
    override suspend fun searchBooks(query: String): SearchResultVO {
        return SearchResultVO.sample1
    }

    override suspend fun searchBooksWithPage(query: String, page: Int): SearchResultVO {
        return when (page) {
            1 -> SearchResultVO.sample1
            2 -> SearchResultVO.sample2
            3 -> SearchResultVO.sample3
            else -> SearchResultVO.empty
        }
    }
}