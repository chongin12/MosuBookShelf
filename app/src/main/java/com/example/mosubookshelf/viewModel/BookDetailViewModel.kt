package com.example.mosubookshelf.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosubookshelf.useCase.BookDetailUseCase
import com.example.mosubookshelf.models.BookDetailVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


data class BookDetailUiState(
    var book: BookDetailVO? = null,
)

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val useCase: BookDetailUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(BookDetailUiState())
    val uiState: StateFlow<BookDetailUiState> = _uiState.asStateFlow()

    fun fetchBookDetail(isbn13: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                useCase.getBookDetail(isbn13 = isbn13)
            }.onSuccess { fetchedBookDetails ->
                println("fetched : $fetchedBookDetails")
                _uiState.update { currentState ->
                    currentState.copy(
                        book = fetchedBookDetails
                    )
                }
            }.onFailure {
                println(it)
            }
        }
    }
}
