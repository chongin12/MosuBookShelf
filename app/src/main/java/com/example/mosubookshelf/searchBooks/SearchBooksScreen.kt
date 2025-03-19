package com.example.mosubookshelf.searchBooks

import androidx.compose.foundation.layout.Column
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
    val queryString by viewModel.queryString.collectAsStateWithLifecycle()
    val searchResult by viewModel.searchResult.collectAsStateWithLifecycle()
    Column {
        TextField(
            value = queryString,
            onValueChange = { value ->
                viewModel.updateQueryString(value)
            }
        )
        Text(searchResult.toString())
    }

}
