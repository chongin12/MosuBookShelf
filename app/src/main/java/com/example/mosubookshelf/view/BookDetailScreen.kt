package com.example.mosubookshelf.view

import android.content.ClipData
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mosubookshelf.viewModel.BookDetailViewModel
import com.example.mosubookshelf.models.BookDetailVO

@Composable
fun BookDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: BookDetailViewModel = hiltViewModel()
) {
    val bookDetailState by viewModel.bookDetailState.collectAsStateWithLifecycle()
    val memoState by viewModel.memoState.collectAsStateWithLifecycle()
    val book = bookDetailState

    if (book != null) {
        BookDetailView(
            bookDetail = book,
            bookMemo = memoState,
            modifier = modifier,
            onMemoChange = { memo ->
                viewModel.updateBookMemo(memo = memo)
            },
        )
    } else {
        LinearProgressIndicator(modifier = modifier.fillMaxWidth())
    }
}

@Composable
fun BookDetailView(bookDetail: BookDetailVO, bookMemo: String, modifier: Modifier = Modifier, onMemoChange: (String) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        BookEssentialsView(
            imageURL = bookDetail.imageURL,
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
        BookMemoView(
            memo = bookMemo,
            onMemoChange = onMemoChange,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
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

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.35F),
                            Color.Black.copy(alpha = 0.45F),
                            Color.Black.copy(alpha = 0.55F),
                            Color.Black.copy(alpha = 0.65F),
                            Color.Black.copy(alpha = 0.75F),
                        ),
                    ),
                    shape = RoundedCornerShape(28.dp),
                )
        ) {
            GlideImage(
                model = imageURL,
                contentDescription = "book image",
                modifier = Modifier.weight(5F),
                contentScale = ContentScale.FillHeight,
            )
            Text(
                title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Serif,
                color = Color.White,
                modifier = Modifier.padding(8.dp),
            )
            Text(
                subtitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.padding(4.dp),
            )
            AuthorsYearPublisherView(
                authors = authors,
                year = year,
                publisher = publisher,
                modifier = Modifier
                    .weight(1F)
                    .wrapContentSize(),
            )
        }
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
                Text(
                    it,
                    color = Color.White,
                )
            }
        }
        Text(
            " · ",
            color = Color.White,
        )
        Text(
            year,
            color = Color.White,
        )
        Text(
            " · ",
            color = Color.White,
        )
        Text(
            publisher,
            color = Color.White,
        )
    }
}

@Composable
fun BookAdditionalDetail(
    price: String,
    rating: Int,
    desc: String,
    link: String,
    isbn13: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        val stars: String = {
            var ret = ""
            for (i in 0..<rating) {
                ret += "★"
            }
            for (i in rating..<5) {
                ret += "☆"
            }
            ret
        }()
        Text(
            text = "$price  ·  $stars",
            fontFamily = FontFamily.Serif,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(desc)
        Text(buildAnnotatedString {
            append("홈페이지에서 구매할 수 있어요!")
            withLink(
                LinkAnnotation.Url(
                    link,
                    TextLinkStyles(style = SpanStyle(color = Color.Blue))
                )
            ) {
                append(" (링크) ")
            }
        })

        // clipboard 로직이 왜 view에 있나요?
        // -> 일회성, 휘발성을 가진 간단한 임시 로직이고, 변경 가능성이 극히 적기 때문에
        //    굳이 viewModel로 넣는 것 보단 자연스러운 형태를 유지했습니다.
        val clipboardManager = LocalClipboardManager.current

        Button(onClick = {
            val clipData = ClipData.newPlainText("plain text", isbn13)
            val clipEntry = ClipEntry(clipData)
            clipboardManager.setClip(clipEntry)
        }) {
            Text("isbn13 정보 (copy)")
        }
    }
}

@Composable
fun BookMemoView(memo: String, modifier: Modifier = Modifier, onMemoChange: (String) -> Unit) {
    TextField(
        memo,
        onValueChange = { value ->
            onMemoChange(value)
        },
        modifier
    )
}
