package com.mo.movie.android.core.composables

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.mo.movie.android.features.setup.onBoarding.composables.PagerIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesSlider(
    images: List<Painter>,
) {
    val pagerState = rememberPagerState(pageCount = { images.size })
    LaunchedEffect(pagerState.settledPage) {
            val nextPage =
                if (pagerState.currentPage == pagerState.pageCount - 1) 0
                else (pagerState.currentPage + 1) % (pagerState.pageCount)
            yield()
            delay(5000)
            pagerState.animateScrollToPage(
                page = nextPage,
                animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy , stiffness = Spring.StiffnessVeryLow)
            )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 7f)
        ) { page ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .graphicsLayer {
                        val pageOffset = pagerState.currentPageOffsetFraction.absoluteValue
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                Image(
                    painter = images[page],
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Height(10.dp)
        PagerIndicator(
            pagerState = pagerState,
            count = images.size,
            width = 8.dp,
            height = 8.dp,
            activeLineWidth = 8.001.dp
        )
    }
}