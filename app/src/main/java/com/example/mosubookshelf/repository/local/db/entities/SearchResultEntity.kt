package com.example.mosubookshelf.repository.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "search_results", primaryKeys = ["keyword", "page"])
data class SearchResultEntity(
    val keyword: String,
    val page: String,
    @ColumnInfo val total: String?,
)