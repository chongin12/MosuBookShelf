package com.example.mosubookshelf.searchBooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosubookshelf.models.SearchResultVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchBooksViewModel @Inject constructor(
    private val useCase: SearchBooksUseCase
): ViewModel() {
    private val _queryString: MutableStateFlow<String> = MutableStateFlow("")
    val queryString = _queryString.asStateFlow()

    private val _searchResult = MutableStateFlow<List<SearchResultVO>>(listOf())
    val searchResult = _searchResult.asStateFlow()


    @OptIn(FlowPreview::class)
    private val debouncedSearchQuery: Flow<String> = queryString
        .debounce(1000)
        .filter { it.isNotEmpty() }
        .distinctUntilChanged()

    init {
        bind()
    }

    private fun bind() {
        viewModelScope.launch {
            debouncedSearchQuery
                .onEach {
                    println("search result reset")
                    _searchResult.update { listOf() }
                }
                .collectLatest { query ->
                    searchBooks(query)
                }
        }
    }

    fun searchBooks(query: String) {
        viewModelScope.launch {
            val fetchedResult = withContext(Dispatchers.IO) {
                useCase.searchBooks(query = query)
            }
            println("searched : $fetchedResult")
            _searchResult.update { currentState ->
                currentState + fetchedResult
            }
        }
    }

    fun searchNextBooks(query: String) {
        val nextPage = searchResult.value.last().page + 1
        viewModelScope.launch {
            val fetchedResult = withContext(Dispatchers.IO) {
                useCase.searchBooksWithPage(query = query, page = nextPage)
            }
            println("searched $nextPage page : $fetchedResult")
            _searchResult.update { currentState ->
                currentState + fetchedResult
            }
        }
    }

    fun updateQueryString(query: String) {
        _queryString.update { query }
    }
}

