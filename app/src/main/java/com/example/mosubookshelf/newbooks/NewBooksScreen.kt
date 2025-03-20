package com.example.mosubookshelf.newbooks

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mosubookshelf.books.BooksView

@Composable
fun NewBooksScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (isbn13: String) -> Unit,
    viewModel: NewBooksViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.books == null) {
        Text("Loading...", modifier)
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