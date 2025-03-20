package com.example.mosubookshelf.newbooks

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mosubookshelf.books.BooksView
import com.example.mosubookshelf.models.BookVO

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