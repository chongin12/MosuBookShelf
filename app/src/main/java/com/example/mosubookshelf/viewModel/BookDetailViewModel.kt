package com.example.mosubookshelf.viewModel

import androidx.lifecycle.*
import com.example.mosubookshelf.useCase.bookDetail.BookDetailUseCase
import com.example.mosubookshelf.models.BookDetailVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val useCase: BookDetailUseCase,
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val isbn13LiveData: LiveData<String> = savedStateHandle.getLiveData("isbn13")
    private val isbn13: String?
        get() {
            return isbn13LiveData.value
        }

    private val _bookDetailState = MutableStateFlow<BookDetailVO?>(null)
    val bookDetailState: StateFlow<BookDetailVO?> = _bookDetailState.asStateFlow()

    private val _memoState = MutableStateFlow("")
    val memoState: StateFlow<String> = _memoState.asStateFlow()

    init {
        println("viewmodel init, isbn13 = $isbn13")
        bindMemoState()
        fetchBookDetail()
        fetchBookMemo()
    }

    @OptIn(FlowPreview::class)
    private fun bindMemoState() = viewModelScope.launch {
        memoState
            .debounce(1000)
            .drop(1) // 처음 한 번은 초기값이 들어오기 때문에 무시해야 합니다.
            .combine(bookDetailState) { memo, bookDetail -> memo to bookDetail }
            .collect { (memo, bookDetail) ->
                bookDetail?.isbn13?.let { isbn13 ->
                    useCase.insertBookMemo(isbn13 = isbn13, memo = memo)
                }
            }
    }

    private fun fetchBookDetail() {
        isbn13?.let {
            viewModelScope.launch {
                useCase.getBookDetail(isbn13 = it)
                    .onSuccess { fetchedBookDetails ->
                        println("fetched : $fetchedBookDetails")
                        _bookDetailState.update { fetchedBookDetails }
                    }.onFailure {
                        println(it)
                    }
            }
        }
    }

    private fun fetchBookMemo() {
        isbn13?.let {
            viewModelScope.launch {
                useCase.getBookMemo(it)
                    .onSuccess { memo ->
                        _memoState.update { memo }
                    }
            }
        }
    }

    fun updateBookMemo(memo: String) {
        _memoState.update { memo }
    }
}
