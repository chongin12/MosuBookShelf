package com.example.mosubookshelf.searchBooks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mosubookshelf.books.BooksView


@Composable
fun SearchBooksScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchBooksViewModel = hiltViewModel(),
    navigateToDetail: (isbn13: String) -> Unit,
) {
    val queryString by viewModel.queryString.collectAsStateWithLifecycle()
    val searchResult by viewModel.searchResult.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    Column(modifier = modifier) {
        TextField(
            value = queryString,
            onValueChange = { value ->
                viewModel.updateQueryString(value)
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        )
        val books = searchResult.flatMap { it.books }
        Box {
            BooksView(
                books = books,
                whenHitBottom = {
                    println("hit bottom!")
                    viewModel.searchNextBooks(queryString)
                },
            ) { navigateToDetail(it) }
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .background(Color.White, shape = CircleShape)
                        .padding(8.dp)
                )
            }
        }
    }

}
