package com.example.mosubookshelf.repository

import com.example.mosubookshelf.models.BookDetailDTO
import com.example.mosubookshelf.models.SearchResultDTO


interface BookCacheRepository: BookRepository {
    fun cacheBook(book: BookDetailDTO)
    fun cacheSearchResult(keyword: String, searchResult: SearchResultDTO)
}