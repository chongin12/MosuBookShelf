package com.example.mosubookshelf.useCase.newBooks

import com.example.mosubookshelf.models.*

// UseCase를 interface로 따로 빼둔 이유?
// 정보 은닉의 관점에서 접근. UseCase를 사용하는 쪽에서는 repository 포함 고수준의 존재를 알 필요가 없다.
interface NewBooksUseCase {
    suspend fun getNewBooks(): Result<List<BookVO>>
}
