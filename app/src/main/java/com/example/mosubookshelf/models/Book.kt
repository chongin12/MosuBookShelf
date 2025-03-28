package com.example.mosubookshelf.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

data class BookVO(
    val title: String,
    val subtitle: String,
    val isbn13: String,
    val priceString: String,
    val imageURL: String,
    val url: String,
) {
    companion object {
        val sample1 = BookVO(
            title = "An Introduction to C & GUI Programming, 2nd Edition",
            subtitle = "Architecting, Designing, and Deploying on the Snowflake Data Cloud",
            isbn13 = "9781912047451",
            priceString = "$12.94",
            imageURL = "https://itbook.store/img/books/9781912047451.png",
            url = "https://itbook.store/books/9781912047451",
        )
        val sample2 = BookVO(
            title = "Programming iOS 11",
            subtitle = "Dive Deep into Views, View Controllers, and Frameworks",
            isbn13 = "9781491999226",
            priceString = "$59.17",
            imageURL = "https://itbook.store/img/books/9781491999226.png",
            url = "https://itbook.store/books/9781491999226",
        )
    }
}

@Serializable
data class BookDTO(
    val title: String?,
    val subtitle: String?,
    val isbn13: String?,
    val price: String?,
    val image: String?,
    val url: String?,
) {
    companion object {
        val sample1 = BookDTO(
            title = "Programming iOS 11",
            subtitle = "Dive Deep into Views, View Controllers, and Frameworks",
            isbn13 = "9781491999226",
            price = "\$59.17",
            image = "https://itbook.store/img/books/9781491999226.png",
            url = "https://itbook.store/books/9781491999226",
        )

        val sample2 = BookDTO(
            title = "2An Introduction to C & GUI Programming, 2nd Edition",
            subtitle = "2Architecting, Designing, and Deploying on the Snowflake Data Cloud",
            isbn13 = "9781912047451",
            price = "$12.94",
            image = "https://itbook.store/img/books/9781912047451.png",
            url = "https://itbook.store/books/9781912047451",
        )
    }
}

@Serializable
data class NewBooksResponse(
    val error: String?,
    val total: String?,
    val books: List<BookDTO>,
)

@OptIn(ExperimentalSerializationApi::class)
val DefaultJsonSerializer by lazy {
    Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = true
        explicitNulls = true
        coerceInputValues = true
    }
}