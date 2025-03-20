package com.example.mosubookshelf.searchBooks

import com.example.mosubookshelf.models.SearchResultDTO
import com.example.mosubookshelf.models.SearchResultVO
import com.example.mosubookshelf.newbooks.convert
import com.example.mosubookshelf.repository.BookRepository

interface SearchBooksUseCase {
    suspend fun searchBooks(query: String): Result<SearchResultVO>
    suspend fun searchBooksWithPage(query: String, page: Int): Result<SearchResultVO>
}

class DefaultSearchBooksUseCase(private val repository: BookRepository): SearchBooksUseCase {
    override suspend fun searchBooks(query: String): Result<SearchResultVO> {
        return repository.searchBooks(query = query).map { it.convert() }
    }

    override suspend fun searchBooksWithPage(query: String, page: Int): Result<SearchResultVO> {
        return repository.searchBooks(query = query, page = page).map { it.convert() }
    }
}

private fun SearchResultDTO.convert(): SearchResultVO {
    return SearchResultVO(
        total = this.total?.toInt() ?: 0,
        page = this.page?.toInt() ?: 0,
        books = this.books.map { it.convert() },
    )
}

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