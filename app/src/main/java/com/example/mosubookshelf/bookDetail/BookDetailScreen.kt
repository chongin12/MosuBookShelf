package com.example.mosubookshelf.bookDetail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mosubookshelf.models.BookDetailVO
import org.w3c.dom.Text

@Composable
fun BookDetailScreen(isbn13: String, viewModel: BookDetailViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val book = uiState.book

    LaunchedEffect(Unit) {
        viewModel.fetchBookDetail(isbn13)
    }

    if (book != null) {
        BookDetailView(bookDetail = book)
    } else {
        Text("Loading...")
    }
}

@Composable
fun BookDetailView(bookDetail: BookDetailVO) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        BookEssentialsView(
            imageURL =  bookDetail.imageURL,
            title = bookDetail.title,
            authors = bookDetail.authors,
            year = bookDetail.year,
            publisher = bookDetail.publisher,
            subtitle = bookDetail.subtitle,
        )
        BookAdditionalDetail(
            price = bookDetail.price,
            rating = bookDetail.rating,
            desc = bookDetail.desc,
            link = bookDetail.link,
            isbn13 = bookDetail.isbn13,
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookEssentialsView(
    imageURL: String,
    title: String,
    authors: List<String>,
    year: String,
    publisher: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(100F / 130),
    ) {
        BookImageBackground(imageURL, Modifier.align(Alignment.Center))
        Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
            GlideImage(
                model = imageURL,
                contentDescription = "book image",
                modifier = Modifier.weight(5F),
                contentScale = ContentScale.FillHeight,
            )
            BlurredBackground(modifier = Modifier.weight(1F).wrapContentSize()) {
                Text(
                    title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Serif,
                    color = Color.White,
                    modifier = Modifier.padding(4.dp),
                )
            }
            BlurredBackground(modifier = Modifier.weight(1F).wrapContentSize()) {
                Text(
                    subtitle,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.padding(4.dp),
                )
            }
            AuthorsYearPublisherView(authors, year, publisher, modifier = Modifier.weight(1F).wrapContentSize())
        }
    }
}

@Composable
fun BlurredBackground(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = modifier
            .background(
                Color.Black.copy(alpha = 0.75F),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        content()
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookImageBackground(
    imageURL: String,
    modifier: Modifier = Modifier,
) {
    GlideImage(
        model = imageURL,
        contentDescription = "book image",
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .blur(28.dp),
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun AuthorsYearPublisherView(
    authors: List<String>,
    year: String,
    publisher: String,
    modifier: Modifier = Modifier,
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Column {
            authors.forEach {
                Text(it)
            }
        }
        Text(" · ")
        Text(year)
        Text(" · ")
        Text(publisher)
    }
}

@Composable
fun BookAdditionalDetail(
    price: String,
    rating: String,
    desc: String,
    link: String,
    isbn13: String,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("$price · $rating")
        Text(desc)
        Text("홈페이지에서 구매할 수 있어요! (링크)")
        Text("isbn13 정보 (copy)")
    }
}

@Preview
@Composable
fun BookDetailPreview() {
    BookDetailView(BookDetailVO.sample1)
}