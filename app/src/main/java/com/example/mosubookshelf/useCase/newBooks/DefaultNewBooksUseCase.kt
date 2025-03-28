package com.example.mosubookshelf.useCase.newBooks

import com.example.mosubookshelf.models.BookVO
import com.example.mosubookshelf.repository.BookRepository
import com.example.mosubookshelf.useCase.convert

class DefaultNewBooksUseCase(private val repository: BookRepository): NewBooksUseCase {
    override suspend fun getNewBooks(): Result<List<BookVO>> {
        return repository.getNewBooks().map { it.map { bookDTO -> bookDTO.convert() } }
    }
}
