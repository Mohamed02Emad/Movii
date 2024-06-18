package com.mo.movie.android.features.home.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mo.movie.android.CURRENT_LANGUAGE
import com.mo.movie.android.R
import com.mo.movie.android.core.composables.HandleRequestStateUi
import com.mo.movie.android.core.composables.Height
import com.mo.movie.android.core.composables.ImagesSlider
import com.mo.movie.android.core.composables.Width
import com.mo.movie.android.features.home.presentation.composables.HomeFilterButton
import com.mo.movie.android.features.home.presentation.composables.MovieCard
import com.mo.movie.android.theme.fontFamilyOverPass
import com.mo.movie.core.utils.logit
import com.mo.movie.features.home.presentation.HomeViewModel

@SuppressLint("ReturnFromAwaitPointerEventScope")
@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavHostController) {
    LaunchedEffect(key1 = true) {
        viewModel.clear()
        viewModel.getTrendingMovies(language = CURRENT_LANGUAGE)
    }
    val context = LocalContext.current
    val moviesState = viewModel.moviesState.collectAsState()
    val filterState = viewModel.currentFilter.collectAsState()
    var isCollapsed by remember {
        mutableStateOf(false)
    }
    val sizePercentage by remember {
        mutableFloatStateOf(1f)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
        ) {
            if (isCollapsed.not()) {
                Height(10.dp)
                ImagesSlider(
                    images = listOf(
                        painterResource(id = R.drawable.home_image),
                        painterResource(id = R.drawable.home_image2),
                        painterResource(id = R.drawable.home_image3),
                        painterResource(id = R.drawable.home_image4),
                    )
                )
                Height(5.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        context.getString(R.string.trending),
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp
                        ),
                        fontFamily = fontFamilyOverPass,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 18.sp
                        )
                    )

                    Row {
                        HomeFilterButton(
                            selectedFilter = filterState.value
                        ) { newFilter ->
                            viewModel.clear()
                            viewModel.getTrendingMovies(newFilter, CURRENT_LANGUAGE)
                        }
                        Width(width = 16.dp)

                    }
                }
            }
        }
        Height(12.dp)
        HandleRequestStateUi(
            state = moviesState.value, modifier = Modifier
                .fillMaxSize()
        ) {
            val movies = viewModel.movies
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        while (true) {
                            val MIN_SCROLL_THRESHOLD = 40.dp
                            var previousY = 0f
                            val dragOffset = awaitPointerEventScope {
                                val touchPoint = awaitPointerEvent()
                                previousY = touchPoint.changes.last().previousPosition.y
                                val event = awaitPointerEvent()
                                val currentY = event.changes.last().position.y
                                logit("previus : ${previousY}\ncurrentY : ${currentY}")
                                currentY - previousY
                            }
                            if (Math.abs(dragOffset) > MIN_SCROLL_THRESHOLD.toPx()) {
                                val scrollDirection = when {
                                    dragOffset > 0 -> ScrollDirection.UP
                                    dragOffset < 0 -> ScrollDirection.DOWN
                                    else -> ScrollDirection.NONE
                                }
                                if (scrollDirection == ScrollDirection.UP) {
                                    logit("up")
                                    isCollapsed = false
                                } else if (scrollDirection == ScrollDirection.DOWN) {
                                    logit("down")
                                    isCollapsed = true
                                }
                            }
                        }
                    },
                columns = GridCells.Adaptive(minSize = 128.dp),

            ) {
                items(count = movies.size) { index ->
                    val movie = movies[index]
                    MovieCard(navController = navController, movie = movie )
                    if(index == movies.size - 1){
                        viewModel.getTrendingMovies(language = CURRENT_LANGUAGE)
                        Height(height = 30.dp)
                    }
                }
            }
        }
    }

}

enum class ScrollDirection {
    UP,
    DOWN,
    NONE
}
