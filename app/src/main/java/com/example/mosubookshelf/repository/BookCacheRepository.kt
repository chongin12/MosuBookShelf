package com.example.mosubookshelf.repository

import com.example.mosubookshelf.models.BookDetailDTO
import com.example.mosubookshelf.models.SearchResultDTO


interface BookCacheRepository: BookRepository {
    suspend fun cacheBook(book: BookDetailDTO)
    suspend fun cacheSearchResult(keyword: String, searchResult: SearchResultDTO)
    suspend fun getBookMemo(isbn13: String): Result<String>
    suspend fun insertBookMemo(isbn13: String, memo: String)
    suspend fun updateBookMemo(isbn13: String, memo: String)
}