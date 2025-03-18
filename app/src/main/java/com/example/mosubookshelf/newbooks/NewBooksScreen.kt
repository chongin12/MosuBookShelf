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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mosubookshelf.models.BookVO

@Composable
fun NewBooksScreen(
    modifier: Modifier = Modifier,
    viewModel: NewBooksViewModel,
    navigateToDetail: (isbn13: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.books == null) {
        Text("Loading...")
    } else {
        NewBooksView(uiState.books!!, modifier, navigateToDetail)
    }
}

@Composable
fun NewBooksView(
    books: List<BookVO>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    LazyColumn(modifier) {
        items(books) { book ->
            BookView(book, Modifier.clickable {
                navigateToDetail(book.isbn13)
            })
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookView(book: BookVO, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .weight(4F)
                .padding(8.dp)
                .fillMaxHeight()
                .aspectRatio((300.0 / 350.0).toFloat())
        ) {
            GlideImage(
                model = book.imageURL,
                contentDescription = "book image",
                modifier = Modifier.align(Alignment.Center)
                    .fillMaxHeight()
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = book.priceString,
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black)
                    .padding(8.dp),
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Cyan, Color.Magenta)
                    ),
                ),
            )
        }
        Column(modifier = Modifier.weight(5F).padding(8.dp)) {
            Text(
                text = book.title,
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = book.subtitle,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Icon(
            Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            "KeyboardArrowRight Icon",
            modifier = Modifier.weight(1F).padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewBooksScreenPreview() {
    NewBooksScreen(navigateToDetail = {}, viewModel = NewBooksViewModel(useCase = MockNewBooksUseCase()))
}