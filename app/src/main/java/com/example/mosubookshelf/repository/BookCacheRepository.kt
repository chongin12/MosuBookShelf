package com.example.mosubookshelf.repository

import com.example.mosubookshelf.models.BookDetailDTO


interface BookCacheRepository: BookRepository {
    fun cacheBook(book: BookDetailDTO)
}