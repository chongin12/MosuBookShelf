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

    private val _memoState = MutableStateFlow("")
    val memoState: StateFlow<String> = _memoState.asStateFlow()

    private fun bindMemoState() = viewModelScope.launch {
        memoState
            .drop(1)
            .debounce(1000)
            .combine(uiState) { memo, ui -> memo to ui }
            .collect { (memo, ui) ->
                ui.book?.isbn13?.let { isbn13 ->
                    useCase.updateBookMemo(isbn13 = isbn13, memo = memo)
                }
            }

    }

    fun fetchBookDetail(isbn13: String) {
        viewModelScope.launch {
            useCase.getBookDetail(isbn13 = isbn13)
                .onSuccess { fetchedBookDetails ->
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

    fun fetchBookMemo(isbn13: String) {
        viewModelScope.launch {
            useCase.getBookMemo(isbn13)
                .onSuccess { memo ->
                    _memoState.update { memo }
                    bindMemoState()
                }
        }
    }

    fun updateBookMemo(memo: String) {
        viewModelScope.launch {
            _memoState.emit(memo)
        }
    }
}
