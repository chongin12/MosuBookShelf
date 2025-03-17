package com.example.mosubookshelf.newbooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosubookshelf.models.BookVO
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

final class NewBooksViewModel(val useCase: NewBooksUseCase): ViewModel() {
    private val _uiState = MutableStateFlow(NewBooksUiState())
    val uiState: StateFlow<NewBooksUiState> = _uiState.asStateFlow()

    fun fetchNewBooks() {
        viewModelScope.launch {
            val fetchedNewBooks = useCase.getNewBooks()
            println("fetched : $fetchedNewBooks")
            _uiState.value = NewBooksUiState(fetchedNewBooks)
        }
    }
}

data class NewBooksUiState(
    var books: List<BookVO>? = null
)