package com.example.mosubookshelf.useCase

import com.example.mosubookshelf.models.*
import com.example.mosubookshelf.repository.BookRepository

// UseCase를 interface로 따로 빼둔 이유?
// 정보 은닉의 관점에서 접근. UseCase를 사용하는 쪽에서는 repository 포함 고수준의 존재를 알 필요가 없다.
interface NewBooksUseCase {
    suspend fun getNewBooks(): Result<List<BookVO>>
}

class DefaultNewBooksUseCase(private val repository: BookRepository): NewBooksUseCase {
    override suspend fun getNewBooks(): Result<List<BookVO>> {
        return repository.getNewBooks().map { it.map { bookDTO -> bookDTO.convert() } }
    }
}

fun BookDTO.convert(): BookVO {
    return BookVO(
        title = title ?: "",
        subtitle = subtitle ?: "",
        isbn13 = isbn13 ?: "",
        priceString = price ?: "",
        imageURL = image ?: "",
        url = url ?: ""
    )
}

class MockNewBooksUseCase(): NewBooksUseCase {
    override suspend fun getNewBooks(): Result<List<BookVO>> {
        return Result.success(listOf(BookVO.sample1, BookVO.sample2))
    }
}
