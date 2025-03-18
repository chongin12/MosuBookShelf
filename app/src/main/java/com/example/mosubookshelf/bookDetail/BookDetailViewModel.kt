package com.example.mosubookshelf.bookDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosubookshelf.models.BookDetailVO
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


data class BookDetailUiState(
    var book: BookDetailVO? = null,
)

class BookDetailViewModel(val useCase: BookDetailUseCase): ViewModel() {
    private val _uiState = MutableStateFlow(BookDetailUiState())
    val uiState: StateFlow<BookDetailUiState> = _uiState.asStateFlow()

    fun fetchBookDetail(isbn13: String) {
        viewModelScope.launch {
            val fetchedBookDetail = withContext(Dispatchers.IO) {
                useCase.getBookDetail(isbn13 = isbn13)
            }
            println("fetched : $fetchedBookDetail")
            _uiState.update { currentState ->
                currentState.copy(
                    book = fetchedBookDetail
                )
            }
        }
    }
}
