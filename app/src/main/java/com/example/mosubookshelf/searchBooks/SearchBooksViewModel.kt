package com.example.mosubookshelf.searchBooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosubookshelf.models.SearchResultVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class SearchBooksUiState(
    var queryString: String,
    var searchResult: List<SearchResultVO>,
)

@HiltViewModel
class SearchBooksViewModel @Inject constructor(
    private val useCase: SearchBooksUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(SearchBooksUiState("", listOf()))
    val uiState: StateFlow<SearchBooksUiState> = _uiState.asStateFlow()

    fun searchBooks(query: String) {
        viewModelScope.launch {
            val fetchedResult = withContext(Dispatchers.IO) {
                useCase.searchBooks(query = query)
            }
            println("searched : $fetchedResult")
            _uiState.update { currentState ->
                currentState.copy(
                    searchResult = currentState.searchResult + fetchedResult
                )
            }
        }
    }

    fun changeQuery(query: String) {
        if (query == _uiState.value.queryString) return

        _uiState.update { currentState ->
            currentState.copy(
                queryString = query,
                searchResult = listOf()
            )
        }

        searchBooks(query = query)
    }
}

