package com.example.mosubookshelf.useCase.bookDetail

import com.example.mosubookshelf.models.BookDetailVO

interface BookDetailUseCase {
    suspend fun getBookDetail(isbn13: String): Result<BookDetailVO>
    suspend fun getBookMemo(isbn13: String): Result<String>
    suspend fun updateBookMemo(isbn13: String, memo: String)
}

