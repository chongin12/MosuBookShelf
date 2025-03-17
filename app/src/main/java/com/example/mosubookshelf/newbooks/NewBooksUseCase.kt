package com.example.mosubookshelf.newbooks

import com.example.mosubookshelf.models.BookVO

class NewBooksUseCase {
    fun getNewBooks(): Array<BookVO> {
        return arrayOf(BookVO.sample1, BookVO.sample2)
    }
}