package com.example.mosubookshelf.books

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mosubookshelf.models.BookVO
import kotlinx.coroutines.flow.*

@Composable
fun BooksView(
    books: List<BookVO>,
    modifier: Modifier = Modifier,
    whenHitBottom: (() -> Unit)? = null,
    navigateToDetail: (String) -> Unit,
) {
    val listState = rememberLazyListState()
    if (whenHitBottom != null) {
        LaunchedEffect(listState) {
            snapshotFlow {
                val layoutInfo = listState.layoutInfo
                val totalItemCount = layoutInfo.totalItemsCount
                val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
                totalItemCount to lastVisibleItemIndex
            }
                .filter { (totalItemCount, lastVisibleItemIndex) ->
                    totalItemCount != 0 && totalItemCount - 1 == lastVisibleItemIndex
                }
                .map { (totalItemCount, _) ->
                    totalItemCount
                }
                .distinctUntilChanged()
                .collect { _ ->
                    whenHitBottom.invoke()
                }
        }
    }

    LazyColumn(
        modifier = modifier,
        state = listState,
    ) {
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
                    .fillMaxSize(),
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