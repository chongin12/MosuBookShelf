package com.example.mosubookshelf.useCase.bookDetail

import com.example.mosubookshelf.models.BookDetailVO


class MockBookDetailUseCase: BookDetailUseCase {
    override suspend fun getBookDetail(isbn13: String): Result<BookDetailVO> {
        return Result.success(BookDetailVO.sample1)
    }

    override suspend fun getBookMemo(isbn13: String): Result<String> {
        return Result.success("memo1")
    }

    override suspend fun insertBookMemo(isbn13: String, memo: String): Result<Unit> {
        println("update : $isbn13 with $memo")
        return Result.success(Unit)
    }
}