package com.example.mosubookshelf.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosubookshelf.useCase.NewBooksUseCase
import com.example.mosubookshelf.models.BookVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class NewBooksUiState(
    var books: List<BookVO>? = null
)

@HiltViewModel
class NewBooksViewModel @Inject constructor(
    val useCase: NewBooksUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(NewBooksUiState())
    val uiState: StateFlow<NewBooksUiState> = _uiState.asStateFlow()

    init {
        fetchNewBooks()
    }

    private fun fetchNewBooks() {
        viewModelScope.launch {
            val fetchedNewBooks = withContext(Dispatchers.IO) {
                useCase.getNewBooks().getOrNull()
            }
            if (fetchedNewBooks != null) {
                println("fetched : $fetchedNewBooks")
                _uiState.update { currentState ->
                    currentState.copy(
                        books = fetchedNewBooks
                    )
                }
            }
        }
    }
}
