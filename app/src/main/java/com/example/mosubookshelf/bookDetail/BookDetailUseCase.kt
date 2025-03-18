package com.example.mosubookshelf.bookDetail

import com.example.mosubookshelf.models.BookDetailVO

interface BookDetailUseCase {
    suspend fun getBookDetail(isbn13: String): BookDetailVO
}

class MockBookDetailUseCase: BookDetailUseCase {
    override suspend fun getBookDetail(isbn13: String): BookDetailVO {
        return BookDetailVO.sample1
    }
}