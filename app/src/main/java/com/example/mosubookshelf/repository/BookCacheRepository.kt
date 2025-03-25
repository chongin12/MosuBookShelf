package com.example.mosubookshelf.repository

import com.example.mosubookshelf.models.BookDetailDTO
import com.example.mosubookshelf.models.SearchResultDTO


interface BookCacheRepository: BookRepository {
    suspend fun cacheBook(book: BookDetailDTO): Result<Unit>
    suspend fun cacheSearchResult(keyword: String, searchResult: SearchResultDTO): Result<Unit>
    suspend fun getBookMemo(isbn13: String): Result<String>
    suspend fun insertBookMemo(isbn13: String, memo: String): Result<Unit>
}