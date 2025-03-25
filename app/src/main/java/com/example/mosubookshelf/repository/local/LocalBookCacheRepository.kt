package com.example.mosubookshelf.repository.local

import android.content.Context
import com.example.mosubookshelf.models.*
import com.example.mosubookshelf.repository.*
import com.example.mosubookshelf.repository.convertToDTO
import com.example.mosubookshelf.repository.local.db.BookDatabase
import com.example.mosubookshelf.repository.local.db.entities.BookMemoEntity

class LocalBookCacheRepository(context: Context): BookCacheRepository {

    private val db = BookDatabase.getInstance(context)

    override suspend fun getNewBooks(): Result<List<BookDTO>> {
        return Result.failure(Exception("getNewBooks는 로컬 DB 사용하지 않음."))
    }

    override suspend fun getBookDetail(isbn13: String): Result<BookDetailDTO> {
        return runCatching { db.bookDao().getBookDetail(isbn13 = isbn13).convertToDTO() }
    }

    override suspend fun searchBooks(query: String): Result<SearchResultDTO> {
        return searchBooks(query = query, page = 1)
    }

    override suspend fun searchBooks(query: String, page: Int): Result<SearchResultDTO> {
        return runCatching {
            val books = db.bookDao().searchBooks(keyword = query, page = page.toString()).map { it.convert() }
            val searchResult = db.bookDao().getSearchResult(keyword = query, page = page.toString())
            if (books.isEmpty()) {
                return Result.failure(Exception("내부 DB에 저장된 검색 결과 없음."))
            } else {
                return Result.success(
                    SearchResultDTO(
                        error = "0",
                        total = searchResult?.total,
                        page = searchResult?.page,
                        books = books,
                    )
                )
            }
        }
    }

    override suspend fun cacheBook(book: BookDetailDTO) {
        insertBook(bookDetailDTO = book)
    }

    override suspend fun cacheSearchResult(keyword: String, searchResult: SearchResultDTO) {
        db.bookDao().saveSearchResults(
            keyword = keyword,
            total = searchResult.total,
            page = searchResult.page ?: "1",
            books = searchResult.books.map { it.convertToEntity() },
        )
    }

    override suspend fun getBookMemo(isbn13: String): Result<String> {
        return runCatching { db.bookDao().getBookMemo(isbn13).memo }
    }

    override suspend fun insertBookMemo(isbn13: String, memo: String) {
        db.bookDao().insertBookMemo(BookMemoEntity(isbn13 = isbn13, memo = memo))
    }

    override suspend fun updateBookMemo(isbn13: String, memo: String) {
        db.bookDao().updateBookMemo(BookMemoEntity(isbn13 = isbn13, memo = memo))
    }

    suspend fun insertBook(bookDetailDTO: BookDetailDTO) {
        db.bookDao().insert(bookDetailDTO.convertToEntity())
    }






}