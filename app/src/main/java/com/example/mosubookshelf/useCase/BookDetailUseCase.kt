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

    private fun BookDetailDTO.convert(): BookDetailVO {
        val splittedAuthors = this.authors?.split(',')?.map { it.trim() }
        return BookDetailVO(
            title = this.title ?: "",
            subtitle = this.subtitle ?: "",
            authors = splittedAuthors ?: listOf(),
            publisher = this.publisher ?: "",
            language = this.language ?: "English",
            isbn13 = this.isbn13 ?: "",
            pages = this.pages ?: "",
            year = this.year ?: "",
            rating = this.rating?.toInt() ?: 0,
            desc = this.desc ?: "",
            price = this.price ?: "",
            imageURL = this.image ?: "",
            link = this.url ?: ""
        )
    }
}

class MockBookDetailUseCase: BookDetailUseCase {
    override suspend fun getBookDetail(isbn13: String): Result<BookDetailVO> {
        return Result.success(BookDetailVO.sample1)
    }
}