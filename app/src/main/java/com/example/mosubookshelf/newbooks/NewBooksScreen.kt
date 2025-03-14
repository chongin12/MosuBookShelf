package com.example.mosubookshelf.newbooks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mosubookshelf.models.BookVO

@Composable
fun NewBooksScreen(modifier: Modifier = Modifier) {
    val books: List<BookVO> = listOf(BookVO.sample1, BookVO.sample1)
    NewBooksView(books, modifier)
}

@Composable
fun NewBooksView(books: List<BookVO>, modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        items(books) { book ->
            BookView(book)
        }
    }
}

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
        ) {
            Text(book.imageURL, Modifier.align(Alignment.TopStart))
            Text(book.priceString, Modifier.align(Alignment.BottomEnd))
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
    NewBooksScreen()
}