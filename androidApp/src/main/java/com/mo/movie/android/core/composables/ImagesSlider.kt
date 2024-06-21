package com.mo.movie.android.core.composables

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.mo.movie.android.R
import com.mo.movie.android.features.setup.onBoarding.composables.PagerIndicator
import com.mo.movie.android.theme.backgroundLight
import com.mo.movie.android.theme.fontFamilyBebas
import com.mo.movie.android.theme.fontFamilyOverPass
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesSlider(
    images: List<Painter>,
) {
    val context = LocalContext.current
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
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
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
                    Box {
                        Image(
                            painter = images[page],
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        if (page == 0)
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.horizontalGradient(
                                            listOf(
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
                                                Color.Transparent,
                                            )
                                        )
                                    )
                            ) {
                                Column {
                                    Text(
                                        context.getString(R.string.see_whats_next),
                                        modifier = Modifier.padding(
                                            top = 40.dp,
                                            start = 8.dp,
                                            end = 8.dp
                                        ),
                                        fontFamily = fontFamilyBebas,
                                        fontWeight = FontWeight.Bold,
                                        style = TextStyle(color = backgroundLight, fontSize = 22.sp)
                                    )
                                    Text(
                                        context.getString(R.string.watch_anytime),
                                        modifier = Modifier.padding(
                                            start = 8.dp,
                                            end = 8.dp
                                        ),
                                        fontFamily = fontFamilyOverPass,
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Italic,
                                        style = TextStyle(color = backgroundLight, fontSize = 11.sp)
                                    )
                                }

                            }
                    }
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
}