package com.example.mosubookshelf.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosubookshelf.useCase.newBooks.NewBooksUseCase
import com.example.mosubookshelf.models.BookVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NewBooksViewModel @Inject constructor(
    val useCase: NewBooksUseCase
): ViewModel() {
    private val _booksState = MutableStateFlow<List<BookVO>?>(null)
    val booksState: StateFlow<List<BookVO>?> = _booksState.asStateFlow()

    init {
        fetchNewBooks()
    }

    private fun fetchNewBooks() {
        viewModelScope.launch {
            val fetchedNewBooks = useCase.getNewBooks().getOrNull()
            if (fetchedNewBooks != null) {
                println("fetched : $fetchedNewBooks")
                _booksState.update { fetchedNewBooks }
            }
        }
    }
}
