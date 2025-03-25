package com.example.mosubookshelf.useCase.bookDetail

import com.example.mosubookshelf.models.BookDetailVO
import com.example.mosubookshelf.repository.BookCacheRepository
import com.example.mosubookshelf.repository.BookRepository
import com.example.mosubookshelf.useCase.convert


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

    override suspend fun insertBookMemo(isbn13: String, memo: String): Result<Unit> {
        println("update book memo : $memo")
        return cache.insertBookMemo(isbn13 = isbn13, memo = memo)
    }
}