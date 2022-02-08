package com.ssindher.headlines.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.ssindher.headlines.R
import com.ssindher.headlines.data.model.NewsArticle
import com.ssindher.headlines.ui.theme.TextCaption
import com.ssindher.headlines.ui.theme.TextHeading
import com.ssindher.headlines.ui.theme.robotoSlab
import com.ssindher.headlines.util.Constants
import com.ssindher.headlines.util.getFormattedDate

@Composable
fun NewsDetailsScreen(article: NewsArticle? = Constants.localNewsList[0], onBackPress: () -> Unit) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (backIcon, articleImage, scrimImage, titleText, sourceText, dateText, descText) = createRefs()

        Image(
            painter = rememberImagePainter(data = article?.urlToImage),
            contentDescription = "Article Image",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(articleImage) {
                    linkTo(parent.start, parent.top, parent.end, parent.bottom)
                    width = Dimension.matchParent
                    height = Dimension.matchParent
                }
        )

        Image(
            painter = rememberImagePainter(R.drawable.backdrop_gradient, builder = {
                decoder(SvgDecoder(LocalContext.current))
            }),
            contentDescription = "Image Scrim",
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(scrimImage) {
                    linkTo(parent.start, parent.top, parent.end, parent.bottom)
                    width = Dimension.matchParent
                    height = Dimension.matchParent
                }
        )

        IconButton(
            onClick = onBackPress,
            modifier = Modifier
                .size(42.dp)
                .constrainAs(backIcon) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                }
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                contentDescription = "Back Icon",
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = article?.description ?: "",
            fontSize = 14.sp,
            fontFamily = robotoSlab,
            fontWeight = FontWeight.Normal,
            color = TextCaption,
            modifier = Modifier.constrainAs(descText) {
                start.linkTo(parent.start, 24.dp)
                end.linkTo(parent.end, 24.dp)
                bottom.linkTo(parent.bottom, 8.dp)
                width = Dimension.fillToConstraints
            }
        )

        Text(
            text = article?.source?.name ?: "",
            fontSize = 20.sp,
            fontFamily = robotoSlab,
            fontWeight = FontWeight.Normal,
            color = TextHeading,
            modifier = Modifier.constrainAs(sourceText) {
                linkTo(parent.start, dateText.start, 24.dp, 12.dp, bias = 0f)
                bottom.linkTo(descText.top, 16.dp)
            }
        )

        Text(
            text = getFormattedDate(article?.publishedAt ?: ""),
            fontSize = 20.sp,
            fontFamily = robotoSlab,
            fontWeight = FontWeight.Normal,
            color = TextHeading,
            modifier = Modifier.constrainAs(dateText) {
                end.linkTo(parent.end, 24.dp)
                bottom.linkTo(descText.top, 16.dp)
            }
        )

        Text(
            text = article?.title ?: "",
            fontSize = 29.sp,
            fontFamily = robotoSlab,
            fontWeight = FontWeight.Bold,
            color = TextHeading,
            modifier = Modifier.constrainAs(titleText) {
                start.linkTo(parent.start, 24.dp)
                end.linkTo(parent.end, 24.dp)
                bottom.linkTo(sourceText.top, 64.dp)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Preview
@Composable
fun NewsDetailsPreview() {
    NewsDetailsScreen {}
}