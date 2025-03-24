package com.example.mosubookshelf.useCase

import com.example.mosubookshelf.models.BookDetailDTO
import com.example.mosubookshelf.models.BookDetailVO
import com.example.mosubookshelf.repository.*

interface BookDetailUseCase {
    suspend fun getBookDetail(isbn13: String): Result<BookDetailVO>
}

class DefaultBookDetailUseCase(private val repository: BookRepository, private val cache: BookCacheRepository): BookDetailUseCase {
    override suspend fun getBookDetail(isbn13: String): Result<BookDetailVO> {
        var result = cache.getBookDetail(isbn13 = isbn13)
        if (result.getOrNull() == null) {
            result = repository.getBookDetail(isbn13)
                .onSuccess {
                    cache.cacheBook(it)
                }
        }

        return result.map { it.convert() }
    }
}

class MockBookDetailUseCase: BookDetailUseCase {
    override suspend fun getBookDetail(isbn13: String): Result<BookDetailVO> {
        return Result.success(BookDetailVO.sample1)
    }
}