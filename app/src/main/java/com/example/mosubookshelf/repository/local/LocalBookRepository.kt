package com.example.mosubookshelf.repository.local

import android.content.Context
import androidx.room.*
import com.example.mosubookshelf.models.*
import com.example.mosubookshelf.repository.BookCacheRepository

@Entity(tableName = "book_details")
data class BookDetailEntity(
    @PrimaryKey val isbn13: String,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "subtitle") val subtitle: String?,
    @ColumnInfo(name = "authors") val authors: String?,
    @ColumnInfo(name = "publisher") val publisher: String?,
    @ColumnInfo(name = "language") val language: String?,
    @ColumnInfo(name = "isbn10") val isbn10: String?,
    @ColumnInfo(name = "pages") val pages: String?,
    @ColumnInfo(name = "year") val year: String?,
    @ColumnInfo(name = "rating") val rating: String?,
    @ColumnInfo(name = "desc") val desc: String?,
    @ColumnInfo(name = "price") val price: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "url") val url: String?,
)

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val isbn13: String,
    @ColumnInfo val title: String?,
    @ColumnInfo val subtitle: String?,
    @ColumnInfo val price: String?,
    @ColumnInfo val image: String?,
    @ColumnInfo val url: String?,
)

@Entity(tableName = "search_results", primaryKeys = ["keyword", "page"])
data class SearchResultEntity(
    val keyword: String,
    val page: String,
    @ColumnInfo val total: String?,
)

@Entity(
    tableName = "search_book_cross_ref",
    primaryKeys = ["keyword", "page", "isbn13"],
    foreignKeys = [
        ForeignKey(
            entity = SearchResultEntity::class,
            parentColumns = ["keyword", "page"],
            childColumns = ["keyword", "page"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["isbn13"],
            childColumns = ["isbn13"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class SearchBookCrossRef(
    val keyword: String,
    val isbn13: String,
    val page: String,
    val bookIndex: Int,
)

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

@Database(
    entities = [BookDetailEntity::class, BookEntity::class, SearchResultEntity::class, SearchBookCrossRef::class],
    version = 2,
)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        private var instance: BookDatabase? = null

        @Synchronized
        fun getInstance(context: Context): BookDatabase {
            if (instance == null) {
                synchronized(BookDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BookDatabase::class.java,
                        "book-database",
                    ).build()
                }
            }
            return instance!!
        }
    }
}

class LocalBookRepository(context: Context): BookCacheRepository {

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

    override fun cacheBook(book: BookDetailDTO) {
        insertBook(bookDetailDTO = book)
    }

    override fun cacheSearchResult(keyword: String, searchResult: SearchResultDTO) {
        db.bookDao().saveSearchResults(
            keyword = keyword,
            total = searchResult.total,
            page = searchResult.page ?: "1",
            books = searchResult.books.map { it.convertToEntity() },
        )
    }

    fun insertBook(bookDetailDTO: BookDetailDTO) {
        db.bookDao().insert(bookDetailDTO.convertToEntity())
    }

    private fun BookDetailEntity.convertToDTO(): BookDetailDTO {
        return BookDetailDTO(
            error = "0",
            title = title,
            subtitle = subtitle,
            authors = authors,
            publisher = publisher,
            language = language,
            isbn10 = isbn10,
            isbn13 = isbn13,
            pages = pages,
            year = year,
            rating = rating,
            desc = desc,
            price = price,
            image = image,
            url = url
        )
    }

    private fun BookEntity.convert(): BookDTO {
        return BookDTO(
            title = title,
            subtitle = subtitle,
            isbn13 = isbn13,
            price = price,
            image = image,
            url = url,
        )
    }

    private fun BookDTO.convertToEntity(): BookEntity {
        return BookEntity(
            isbn13 = isbn13 ?: "",
            title = title,
            subtitle = subtitle,
            price = price,
            image = image,
            url = url
        )
    }

    private fun BookDetailDTO.convertToEntity(): BookDetailEntity {
        return BookDetailEntity(
            title = title,
            subtitle = subtitle,
            authors = authors,
            publisher = publisher,
            language = language,
            isbn10 = isbn10,
            isbn13 = isbn13 ?: "",
            pages = pages,
            year = year,
            rating = rating,
            desc = desc,
            price = price,
            image = image,
            url = url
        )
    }
}