package com.example.mosubookshelf.repository

import com.example.mosubookshelf.models.BookDTO
import com.example.mosubookshelf.models.BookDetailDTO
import com.example.mosubookshelf.repository.local.db.entities.BookDetailEntity
import com.example.mosubookshelf.repository.local.db.entities.BookEntity

internal fun BookDetailEntity.convertToDTO(): BookDetailDTO {
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

internal fun BookEntity.convert(): BookDTO {
    return BookDTO(
        title = title,
        subtitle = subtitle,
        isbn13 = isbn13,
        price = price,
        image = image,
        url = url,
    )
}

internal fun BookDTO.convertToEntity(): BookEntity {
    return BookEntity(
        isbn13 = isbn13 ?: "",
        title = title,
        subtitle = subtitle,
        price = price,
        image = image,
        url = url
    )
}

internal fun BookDetailDTO.convertToEntity(): BookDetailEntity {
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