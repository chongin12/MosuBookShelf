package com.example.mosubookshelf.repository.local

import android.content.Context
import androidx.room.*
import com.example.mosubookshelf.models.*
import com.example.mosubookshelf.repository.BookCacheRepository

@Entity
data class BookEntity(
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

@Dao
interface BookDao {
    @Insert
    fun insert(book: BookEntity)

    @Update
    fun update(book: BookEntity)

    @Delete
    fun delete(book: BookEntity)

    @Query("SELECT * FROM BookEntity WHERE isbn13 = :isbn13")
    fun getBook(isbn13: String): BookEntity
}

@Database(entities = [BookEntity::class], version = 1)
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
        return runCatching { db.bookDao().getBook(isbn13).convert() }
    }

    override suspend fun searchBooks(query: String): Result<SearchResultDTO> {
        return Result.failure(Exception("searchBooks는 로컬 DB 사용하지 않음."))
    }

    override suspend fun searchBooks(query: String, page: Int): Result<SearchResultDTO> {
        return Result.failure(Exception("searchBooks는 로컬 DB 사용하지 않음."))
    }

    override fun cacheBook(book: BookDetailDTO) {
        insertBook(bookDetailDTO = book)
    }

    fun insertBook(bookDetailDTO: BookDetailDTO) {
        db.bookDao().insert(bookDetailDTO.convert())
    }

    fun BookEntity.convert(): BookDetailDTO {
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

    fun BookDetailDTO.convert(): BookEntity {
        return BookEntity(
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