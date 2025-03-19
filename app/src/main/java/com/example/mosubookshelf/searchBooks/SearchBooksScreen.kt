package com.example.mosubookshelf.searchBooks

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun SearchBooksScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchBooksViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TextField(
        value = uiState.queryString,
        onValueChange = { value ->
            viewModel.changeQuery(value)
        }
    )
}
