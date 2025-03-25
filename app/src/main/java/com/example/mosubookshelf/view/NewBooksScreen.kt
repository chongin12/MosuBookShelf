package com.example.mosubookshelf.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mosubookshelf.useCase.newBooks.MockNewBooksUseCase
import com.example.mosubookshelf.viewModel.NewBooksViewModel

@Composable
fun NewBooksScreen(
    navigateToDetail: (isbn13: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewBooksViewModel = hiltViewModel(),
) {
    val booksState by viewModel.booksState.collectAsStateWithLifecycle()
    val books = booksState
    if (books == null) {
        LinearProgressIndicator(modifier = modifier.fillMaxWidth())
    } else {
        BooksView(
            books = books,
            modifier = modifier,
            navigateToDetail = navigateToDetail,
        )
    }
}



@Preview(showBackground = true)
@Composable
fun NewBooksScreenPreview() {
    NewBooksScreen(navigateToDetail = {}, viewModel = NewBooksViewModel(useCase = MockNewBooksUseCase()))
}