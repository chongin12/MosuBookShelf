package com.example.mosubookshelf.newbooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosubookshelf.models.BookVO
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class NewBooksViewModel(val useCase: NewBooksUseCase): ViewModel() {
    private val _uiState = MutableStateFlow(NewBooksUiState())
    val uiState: StateFlow<NewBooksUiState> = _uiState.asStateFlow()

    init {
        fetchNewBooks()
    }

    private fun fetchNewBooks() {
        viewModelScope.launch {
            val fetchedNewBooks = withContext(Dispatchers.IO) {
                useCase.getNewBooks()
            }
            println("fetched : $fetchedNewBooks")
            _uiState.update { currentState ->
                currentState.copy(
                    books = fetchedNewBooks
                )
            }
        }
    }
}

data class NewBooksUiState(
    var books: List<BookVO>? = null
)