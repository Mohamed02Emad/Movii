package com.mo.movie.android.features.home.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import collapseHeightBy
import com.mo.movie.android.CURRENT_LANGUAGE
import com.mo.movie.android.R
import com.mo.movie.android.core.composables.HandleRequestStateUi
import com.mo.movie.android.core.composables.Height
import com.mo.movie.android.core.composables.ImagesSlider
import com.mo.movie.android.core.composables.Width
import com.mo.movie.android.features.home.presentation.composables.HomeFilterButton
import com.mo.movie.android.features.home.presentation.composables.MovieCard
import com.mo.movie.android.theme.backgroundLight
import com.mo.movie.android.theme.fontFamilyOverPass
import com.mo.movie.features.home.presentation.HomeViewModel
import kotlinx.coroutines.launch
import verticalScrollListener

@SuppressLint("ReturnFromAwaitPointerEventScope")
@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavHostController) {
    LaunchedEffect(key1 = true) {
        initData(viewModel)
    }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val moviesState = viewModel.moviesState.collectAsState()
    val filterState = viewModel.currentFilter.collectAsState()
    var sizePercentage by remember {
        mutableStateOf(1f)
    }
    val alpha = if (sizePercentage in 0f..0.2f) {
        sizePercentage / 0.2f
    } else {
        1f
    }
    val gridState = rememberLazyGridState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessVeryLow
                    )
                )
                .collapseHeightBy(sizePercentage)
        ) {
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
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alpha)
                .collapseHeightBy(sizePercentage),
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
        Height(12.dp)
        HandleRequestStateUi(
            state = moviesState.value, modifier = Modifier
                .fillMaxSize(),
            onTryAgain = { initData(viewModel) }
        ) {
            val movies = viewModel.movies
            Box {
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxSize()
                        .verticalScrollListener(initialScrollPercentage = sizePercentage) { currentScrollValue ->
                            sizePercentage = currentScrollValue
                        },
                    columns = GridCells.Adaptive(minSize = 128.dp),
                    state = gridState
                ) {
                    items(count = movies.size) { index ->
                        val movie = movies[index]
                        MovieCard(navController = navController, movie = movie)
                        if (index == movies.size - 1) {
                            viewModel.getTrendingMovies(language = CURRENT_LANGUAGE)
                            Height(height = 30.dp)
                        }
                    }
                }

                val showUpButton = gridState.firstVisibleItemIndex > 4
                if (showUpButton)
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 12.dp , vertical = 24.dp)
                            .align(Alignment.BottomEnd)
                            .size(40.dp)
                            .clip(RoundedCornerShape(40.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable {
                                scope.launch {
                                    val isScrolling = gridState.isScrollInProgress
                                    if(isScrolling) return@launch
                                    gridState.animateScrollToItem(index = 0)
                                    sizePercentage = 1f
                                }
                            }
                            .padding(10.dp),
                        imageVector =  Icons.Filled.ArrowUpward,
                        contentDescription = null,
                        tint = backgroundLight
                    )

            }
        }
    }

}

private fun initData(viewModel: HomeViewModel) {
    viewModel.clear()
    viewModel.getTrendingMovies(language = CURRENT_LANGUAGE)
}
