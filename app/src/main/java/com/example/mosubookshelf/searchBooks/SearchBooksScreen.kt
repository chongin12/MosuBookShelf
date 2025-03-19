package com.example.mosubookshelf.searchBooks

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mosubookshelf.newbooks.BooksView


@Composable
fun SearchBooksScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchBooksViewModel = hiltViewModel(),
    navigateToDetail: (isbn13: String) -> Unit,
) {
    val queryString by viewModel.queryString.collectAsStateWithLifecycle()
    val searchResult by viewModel.searchResult.collectAsStateWithLifecycle()
    Column {
        TextField(
            value = queryString,
            onValueChange = { value ->
                viewModel.updateQueryString(value)
            }
        )
        val books = searchResult.flatMap { it.books }
        BooksView(books) { navigateToDetail(it) }
    }

}
