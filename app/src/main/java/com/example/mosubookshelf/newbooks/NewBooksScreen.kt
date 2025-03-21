package com.example.mosubookshelf.newbooks

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mosubookshelf.books.BooksView

@Composable
fun NewBooksScreen(
    navigateToDetail: (isbn13: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewBooksViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.books == null) {
        LinearProgressIndicator(modifier = modifier.fillMaxWidth())
    } else {
        BooksView(
            books = uiState.books!!,
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