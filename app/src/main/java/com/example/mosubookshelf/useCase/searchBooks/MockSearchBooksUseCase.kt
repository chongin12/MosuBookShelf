package com.example.mosubookshelf.useCase.searchBooks

import com.example.mosubookshelf.models.SearchResultVO

class MockSearchBooksUseCase: SearchBooksUseCase {
    override suspend fun searchBooks(query: String): Result<SearchResultVO> {
        return Result.success(SearchResultVO.sample1)
    }

    override suspend fun searchBooksWithPage(query: String, page: Int): Result<SearchResultVO> {
        return Result.success(when (page) {
            1 -> SearchResultVO.sample1
            2 -> SearchResultVO.sample2
            3 -> SearchResultVO.sample3
            else -> SearchResultVO.empty
        })
    }
}