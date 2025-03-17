package com.example.mosubookshelf.newbooks

import com.example.mosubookshelf.models.*
import com.example.mosubookshelf.repository.BookRepository

interface NewBooksUseCase {
    suspend fun getNewBooks(): List<BookVO>
}

class DefaultNewBooksUseCase(private val repository: BookRepository): NewBooksUseCase {
    override suspend fun getNewBooks(): List<BookVO> {
        return repository.getNewBooks().map { it.convert() }
    }

    private fun BookDTO.convert(): BookVO {
        return BookVO(
            title = title ?: "",
            subtitle = subtitle ?: "",
            isbn13 = isbn13 ?: "",
            priceString = price ?: "",
            imageURL = image ?: "",
            url = url ?: ""
        )
    }
}

class MockNewBooksUseCase(): NewBooksUseCase {
    override suspend fun getNewBooks(): List<BookVO> {
        return listOf(BookVO.sample1, BookVO.sample2)
    }
}
