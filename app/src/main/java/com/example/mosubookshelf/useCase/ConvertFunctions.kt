package com.example.mosubookshelf.useCase

import com.example.mosubookshelf.models.*

internal fun BookDetailDTO.convert(): BookDetailVO {
    val splittedAuthors = this.authors?.split(',')?.map { it.trim() }
    return BookDetailVO(
        title = this.title ?: "",
        subtitle = this.subtitle ?: "",
        authors = splittedAuthors ?: listOf(),
        publisher = this.publisher ?: "",
        language = this.language ?: "English",
        isbn13 = this.isbn13 ?: "",
        pages = this.pages ?: "",
        year = this.year ?: "",
        rating = this.rating?.toInt() ?: 0,
        desc = this.desc ?: "",
        price = this.price ?: "",
        imageURL = this.image ?: "",
        link = this.url ?: ""
    )
}

internal fun BookDTO.convert(): BookVO {
    return BookVO(
        title = title ?: "",
        subtitle = subtitle ?: "",
        isbn13 = isbn13 ?: "",
        priceString = price ?: "",
        imageURL = image ?: "",
        url = url ?: ""
    )
}

internal fun SearchResultDTO.convert(): SearchResultVO {
    return SearchResultVO(
        total = this.total?.toInt() ?: 0,
        page = this.page?.toInt() ?: 0,
        books = this.books.map { it.convert() },
    )
}