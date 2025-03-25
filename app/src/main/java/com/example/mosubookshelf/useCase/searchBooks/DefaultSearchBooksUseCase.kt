package com.example.mosubookshelf.useCase.searchBooks

import com.example.mosubookshelf.models.SearchResultVO
import com.example.mosubookshelf.repository.BookCacheRepository
import com.example.mosubookshelf.repository.BookRepository
import com.example.mosubookshelf.useCase.convert

class DefaultSearchBooksUseCase(
    private val repository: BookRepository,
    private val cacheRepository: BookCacheRepository
): SearchBooksUseCase {
    override suspend fun searchBooks(query: String): Result<SearchResultVO> {
        var result = cacheRepository.searchBooks(query = query)
        if (result.getOrNull() == null) {
            result = repository.searchBooks(query = query)
                .onSuccess {
                    cacheRepository.cacheSearchResult(keyword = query, searchResult = it)
                }
        }
        return result.map { it.convert() }
    }

    override suspend fun searchBooksWithPage(query: String, page: Int): Result<SearchResultVO> {
        var result = cacheRepository.searchBooks(query = query, page = page)
        if (result.getOrNull() == null) {
            result = repository.searchBooks(query = query, page = page)
                .onSuccess {
                    cacheRepository.cacheSearchResult(keyword = query, searchResult = it)
                }
        }
        return result.map { it.convert() }
    }
}

