package com.example.mosubookshelf.repository.local.db

import android.content.Context
import androidx.room.*
import com.example.mosubookshelf.repository.local.db.entities.*

@Database(
    entities = [
        BookDetailEntity::class,
        BookEntity::class,
        SearchResultEntity::class,
        SearchBookCrossRef::class,
        BookMemoEntity::class,
    ],
    version = 1,
    exportSchema = false,
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
                        context,
                        BookDatabase::class.java,
                        "book-database",
                    ).build()
                }
            }
            return instance!!
        }
    }
}
