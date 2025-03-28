package com.example.mosubookshelf.useCase.newBooks

import com.example.mosubookshelf.models.BookVO

class MockNewBooksUseCase(): NewBooksUseCase {
    override suspend fun getNewBooks(): Result<List<BookVO>> {
        return Result.success(listOf(BookVO.sample1, BookVO.sample2))
    }
}
