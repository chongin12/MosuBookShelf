package com.example.mosubookshelf.models

data class BookDetailVO(
    val title: String,
    val subtitle: String,
    val authors: List<String>,
    val publisher: String,
    val language: String,
    val isbn13: String,
    val pages: String,
    val year: String,
    val rating: Int,
    val desc: String,
    val price: String,
    val imageURL: String,
    val link: String,
) {
    companion object {
        val sample1 = BookDetailVO(
            title = "Designing Across Senses",
            subtitle = "A Multimodal Approach to Product Design",
            authors = listOf("Christine Park", "John Alderman"),
            publisher = "O\'Reilly Media",
            language = "English",
            isbn13 = "9781491954249",
            pages = "296",
            year = "2018",
            rating = 3,
            desc = "Today we have the ability to connect speech, touch, haptic, and gestural interfaces into products that engage several human senses at once. This practical book explores examples from current designers and devices to describe how these products blend multiple interface modes together into a cohesive ...",
            price = "$12.34",
            imageURL = "https://itbook.store/img/books/9781491954249.png",
            link = "https://itbook.store/books/9781491954249",
        )
    }
}

data class BookDetailDTO(
    val error: String?,
    val title: String?,
    val subtitle: String?,
    val authors: String?,
    val publisher: String?,
    val language: String?,
    val isbn10: String?,
    val isbn13: String?,
    val pages: String?,
    val year: String?,
    val rating: String?,
    val desc: String?,
    val price: String?,
    val image: String?,
    val url: String?,
) {
    companion object {
        val sample1 = BookDetailDTO(
            error = "0",
            title = "Designing Across Senses",
            subtitle = "A Multimodal Approach to Product Design",
            authors = "Christine Park, John Alderman",
            publisher = "O\'Reilly Media",
            language = "English",
            isbn13 = "9781491954249",
            isbn10 = "1491954248",
            pages = "296",
            year = "2018",
            rating = "3",
            desc = "Today we have the ability to connect speech, touch, haptic, and gestural interfaces into products that engage several human senses at once. This practical book explores examples from current designers and devices to describe how these products blend multiple interface modes together into a cohesive ...",
            price = "$12.34",
            image = "https://itbook.store/img/books/9781491954249.png",
            url = "https://itbook.store/books/9781491954249",
        )
    }
}