package com.ssindher.headlines.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ssindher.headlines.R
import com.ssindher.headlines.data.model.NewsArticle
import com.ssindher.headlines.ui.theme.Black
import com.ssindher.headlines.ui.theme.GreyBackground
import com.ssindher.headlines.ui.theme.TextCaption
import com.ssindher.headlines.ui.theme.TextHeading
import com.ssindher.headlines.ui.theme.White
import com.ssindher.headlines.ui.theme.robotoSlab
import com.ssindher.headlines.util.getFormattedDate
import com.ssindher.headlines.util.getValidImageUrl

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    isNetworkAvailable: Boolean = false,
    viewModel: HomeViewModel,
    onClick: (article: NewsArticle) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val systemUiController = rememberSystemUiController()
    SideEffect { systemUiController.setStatusBarColor(color = Black) }

    val newsArticles = viewModel.newsList.observeAsState(listOf()).value
    val loadingState = viewModel.newsLoader.observeAsState(false).value
    val newsError = viewModel.newsError.observeAsState("").value

    Scaffold(
        topBar = { AppBar() },
        backgroundColor = GreyBackground,
        scaffoldState = scaffoldState,
        snackbarHost = { scaffoldState.snackbarHostState },
        modifier = Modifier.fillMaxSize()
    ) {
        EmptyState()
        /*if (newsArticles.isEmpty() && !isNetworkAvailable) {
            EmptyState()
        }
        if (newsError.isNotBlank()) {
            CustomSnackbar(snackbarHostState = scaffoldState.snackbarHostState)
            scope.launch { scaffoldState.snackbarHostState.showSnackbar(message = newsError) }
        }
        SwipeRefresh(
            modifier = Modifier.fillMaxSize(),
            state = rememberSwipeRefreshState(isRefreshing = loadingState),
            onRefresh = {
                if (isNetworkAvailable) {
                    viewModel.fetchNewsFromApi()
                } else {
                    viewModel.fetchNewsFromDB()
                }
            }) {
            LazyColumn {
                items(newsArticles) { article ->
                    NewsCard(
                        article = article,
                        isLoading = loadingState,
                        onClick = onClick
                    )
                }
            }
        }*/
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsCard(
    article: NewsArticle,
    modifier: Modifier = Modifier,
    onClick: (article: NewsArticle) -> Unit,
    isLoading: Boolean
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(6.dp),
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .placeholder(
                visible = isLoading,
                highlight = PlaceholderHighlight.shimmer(),
                color = Color.Gray
            ),
        onClick = { onClick(article) }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(216.dp)
        ) {
            val (articleImage, gradientImage, titleText, sourceText, publishDateText) = createRefs()

            // Background Image
            Image(
                painter = rememberImagePainter(
                    getValidImageUrl(article.urlToImage ?: ""),
                    builder = { crossfade(true) }
                ),
                contentDescription = "Article Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(articleImage) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )

            // Gradient Overlay
            Image(
                painter = rememberImagePainter(R.drawable.backdrop_gradient, builder = {
                    decoder(SvgDecoder(LocalContext.current))
                }),
                contentDescription = "Article Image",
                modifier = Modifier
                    .constrainAs(gradientImage) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            )

            // Article Heading
            Text(
                text = article.title ?: "",
                color = TextHeading,
                fontSize = 20.sp,
                fontFamily = robotoSlab,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.constrainAs(titleText) {
                    start.linkTo(parent.start, 12.dp)
                    end.linkTo(parent.end, 12.dp)
                    linkTo(
                        top = parent.top,
                        topMargin = 16.dp,
                        bottom = sourceText.top,
                        bottomMargin = 8.dp,
                        bias = 1f
                    )
                    width = Dimension.fillToConstraints
                }
            )

            // Source Name
            Text(
                text = article.source?.name ?: "",
                color = TextCaption,
                fontSize = 12.sp,
                fontFamily = robotoSlab,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(sourceText) {
                    start.linkTo(parent.start, 12.dp)
                    bottom.linkTo(parent.bottom, 12.dp)
                }
            )

            // Publishing Date
            Text(
                text = getFormattedDate(article.publishedAt ?: ""),
                color = TextCaption,
                fontSize = 12.sp,
                fontFamily = robotoSlab,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.constrainAs(publishDateText) {
                    start.linkTo(sourceText.end, 12.dp)
                    bottom.linkTo(parent.bottom, 12.dp)
                }
            )
        }
    }
}

@Composable
fun CustomSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {}
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) { data: SnackbarData ->
        Snackbar(
            modifier = Modifier.padding(16.dp),
            backgroundColor = Color.Red,
            content = {
                Text(
                    text = data.message,
                    style = MaterialTheme.typography.body2
                )
            },
            action = {
                data.actionLabel?.let { actionLabel ->
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = actionLabel,
                            color = White,
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun AppBar() {
    TopAppBar(backgroundColor = Black) {
        Text(
            text = "HEADLINES",
            color = White,
            fontSize = 20.sp,
            fontFamily = robotoSlab,
            fontWeight = FontWeight.Bold,
            letterSpacing = 4.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun EmptyState() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val lottieAnimation = createRef()
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.trophy))
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .fillMaxSize(0.65f)
                .constrainAs(lottieAnimation) {
                    linkTo(
                        parent.start,
                        parent.top,
                        parent.end,
                        parent.bottom,
                        64.dp,
                        64.dp,
                        64.dp,
                        64.dp,
                        verticalBias = 0.1f
                    )
                })
    }
}