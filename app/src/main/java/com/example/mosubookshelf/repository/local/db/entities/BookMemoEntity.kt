package com.example.mosubookshelf.repository.local.db.entities

import androidx.room.*

@Entity(tableName = "book_memos")
data class BookMemoEntity(
    @PrimaryKey val isbn13: String,
    @ColumnInfo val memo: String,
)