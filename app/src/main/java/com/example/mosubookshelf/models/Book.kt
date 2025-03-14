package com.example.mosubookshelf.models

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
    }
}

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
            title = "An Introduction to C & GUI Programming, 2nd Edition",
            subtitle = "Architecting, Designing, and Deploying on the Snowflake Data Cloud",
            isbn13 = "9781912047451",
            price = "$12.94",
            image = "https://itbook.store/img/books/9781912047451.png",
            url = "https://itbook.store/books/9781912047451",
        )
    }
}