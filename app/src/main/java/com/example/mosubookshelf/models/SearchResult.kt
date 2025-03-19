package com.example.mosubookshelf.models

data class SearchResultVO(
    val total: Int,
    val page: Int,
    val books: List<BookVO>,
) {
    companion object {
        val sample1 = SearchResultVO(
            total = 28,
            page = 1,
            books = listOf(BookVO.sample1, BookVO.sample2),
        )
    }
}

data class SearchResultDTO(
    val error: String?,
    val total: String?,
    val page: String?,
    val books: List<BookDTO>,
)