package com.example.mosubookshelf.repository.local.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey


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