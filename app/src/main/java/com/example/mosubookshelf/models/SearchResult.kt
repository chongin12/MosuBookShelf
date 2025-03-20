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
            books = listOf(BookVO.sample1, BookVO.sample2, BookVO.sample1, BookVO.sample2, BookVO.sample1),
        )
        val sample2 = SearchResultVO(
            total = 28,
            page = 2,
            books = listOf(BookVO.sample1, BookVO.sample2, BookVO.sample1, BookVO.sample2, BookVO.sample1),
        )
        val sample3 = SearchResultVO(
            total = 28,
            page = 3,
            books = listOf(BookVO.sample1, BookVO.sample2, BookVO.sample1, BookVO.sample2, BookVO.sample1),
        )
        val empty = SearchResultVO(
            total = 28,
            page = 4,
            books = listOf()
        )
    }
}

data class SearchResultDTO(
    val error: String?,
    val total: String?,
    val page: String?,
    val books: List<BookDTO>,
) {
    companion object {
        val sample1 = SearchResultDTO(
            error = "0",
            total = "28",
            page = "1",
            books = listOf(BookDTO.sample1, BookDTO.sample2)
        )
    }
}