package com.example.mosubookshelf.repository.local.db.entities

import androidx.room.*

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val isbn13: String,
    @ColumnInfo val title: String?,
    @ColumnInfo val subtitle: String?,
    @ColumnInfo val price: String?,
    @ColumnInfo val image: String?,
    @ColumnInfo val url: String?,
)