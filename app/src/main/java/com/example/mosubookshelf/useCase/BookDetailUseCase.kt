package com.example.mosubookshelf.useCase

import com.example.mosubookshelf.models.BookDetailVO
import com.example.mosubookshelf.repository.*

interface BookDetailUseCase {
    suspend fun getBookDetail(isbn13: String): Result<BookDetailVO>
    suspend fun getBookMemo(isbn13: String): Result<String>
    suspend fun updateBookMemo(isbn13: String, memo: String)
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

    override suspend fun getBookMemo(isbn13: String): Result<String> {
        println("get memo from : $isbn13")
        return cache.getBookMemo(isbn13 = isbn13)
    }

    override suspend fun updateBookMemo(isbn13: String, memo: String) {
        println("update book memo : $memo")
        val result = cache.getBookMemo(isbn13 = isbn13)
        if (result.isFailure) {
            cache.insertBookMemo(isbn13 = isbn13, memo = memo)
        } else {
            cache.updateBookMemo(isbn13 = isbn13, memo = memo)
        }
    }
}

class MockBookDetailUseCase: BookDetailUseCase {
    override suspend fun getBookDetail(isbn13: String): Result<BookDetailVO> {
        return Result.success(BookDetailVO.sample1)
    }

    override suspend fun getBookMemo(isbn13: String): Result<String> {
        return Result.success("memo1")
    }

    override suspend fun updateBookMemo(isbn13: String, memo: String) {
        println("update : $isbn13 with $memo")
    }
}