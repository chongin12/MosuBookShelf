package com.example.mosubookshelf.repository.local.db

import androidx.room.*
import com.example.mosubookshelf.repository.local.db.entities.*


@Dao
interface BookDao {
    // Book
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(books: List<BookEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchResult(searchResult: SearchResultEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchBookCrossRefs(crossRefs: List<SearchBookCrossRef>)

    // Book Detail
    @Insert
    fun insert(bookDetail: BookDetailEntity)

    @Update
    fun update(bookDetail: BookDetailEntity)

    @Delete
    fun delete(bookDetail: BookDetailEntity)

    @Query("SELECT * FROM book_details WHERE isbn13 = :isbn13")
    fun getBookDetail(isbn13: String): BookDetailEntity

    // Search Result
    @Transaction
    @Query("SELECT * FROM search_results WHERE keyword = :keyword and page = :page")
    fun getSearchResult(keyword: String, page: String): SearchResultEntity?

    @Query("""
        SELECT b.* FROM books b
        INNER JOIN search_book_cross_ref ref ON b.isbn13 = ref.isbn13
        WHERE ref.keyword = :keyword AND ref.page = :page
        ORDER BY ref.bookIndex
    """)
    fun searchBooks(keyword: String, page: String): List<BookEntity>


    @Transaction
    fun saveSearchResults(keyword: String, total: String?, page: String, books: List<BookEntity>) {
        val searchResult = SearchResultEntity(keyword = keyword, page = page, total = total)
        insertSearchResult(searchResult)
        insertBooks(books)

        val crossRefs = books.mapIndexed { index, book ->
            SearchBookCrossRef(keyword = keyword, isbn13 = book.isbn13, page = page, bookIndex = index)
        }
        insertSearchBookCrossRefs(crossRefs = crossRefs)
    }
}
