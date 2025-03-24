package com.example.mosubookshelf.repository.local.db.entities

import androidx.room.*

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