package com.mo.movie.android.features.movies.presentation

import AppDropdown
import DDLItem
import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mo.movie.android.CURRENT_LANGUAGE
import com.mo.movie.android.R
import com.mo.movie.android.core.composables.HandleRequestStateUi
import com.mo.movie.android.core.composables.Height
import com.mo.movie.android.core.composables.text.AppText
import com.mo.movie.android.core.utils.extentions.scrollToFirst
import com.mo.movie.android.features.home.presentation.composables.MovieCard
import com.mo.movie.android.theme.backgroundLight
import com.mo.movie.features.movies.domain.enums.SortType
import com.mo.movie.features.movies.presentation.MoviesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(navController: NavHostController, viewModel: MoviesViewModel) {
    val context = LocalContext.current
    val items = SortType.entries.toTypedArray()
    var selectedItem by remember {
        mutableStateOf(items.first())
    }
    val moviesState = viewModel.moviesState.collectAsState()
    val gridState = rememberLazyGridState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scrollBehaviorTopOnly = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        initData(viewModel, SortType.POPULAR)
    }
    Scaffold(
        modifier = Modifier
            .padding(bottom = 44.dp)
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        AppText(
                            modifier = Modifier.padding(top = 25.dp),
                            text = context.getString(R.string.discover_movies),
                            fontSize = 24.sp
                        )
                    },
                    scrollBehavior = scrollBehaviorTopOnly,
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.background,
                    ),
                )
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AppText(
                                modifier = Modifier.padding(start = 4.dp, end = 8.dp),
                                text = context.getString(R.string.sort),
                                fontSize = 20.sp
                            )
                            AppDropdown(
                                modifier = Modifier
                                    .animateContentSize(spring())
                                    .border(
                                        width = 0.15.dp,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        shape = RoundedCornerShape(4.dp)
                                    ),
                                items = items.map {
                                    DDLItem(
                                        value = it,
                                        title = when(it){
                                            SortType.POPULAR -> context.getString(R.string.popular)
                                            SortType.TOP_RATED ->context.getString(R.string.top_rated)
                                            SortType.NOW_PLAYING -> context.getString(R.string.now_playing)
                                            SortType.UPCOMING ->context.getString(R.string.upcoming)
                                        },
                                    )
                                },
                                selectedItem = selectedItem,
                                itemTextColor = backgroundLight,
                                hint = "pick one"
                            ) { newSelectedItem ->
                                selectedItem = newSelectedItem
                                initData(viewModel, selectedItem)
                                gridState.scrollToFirst(scope)
                            }
                        }
                    },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.background,
                    ),
                )
            }
        }

    ) { padding ->
        HandleRequestStateUi(
            state = moviesState.value,
            modifier = Modifier
                .padding(top = padding.calculateTopPadding())
                .padding(horizontal = 12.dp)
                .fillMaxSize(),
            onTryAgain = { initData(viewModel, selectedItem) }
        ) {
            val movies = viewModel.movies
            Box {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    columns = GridCells.Adaptive(minSize = 128.dp),
                    state = gridState
                ) {
                    items(count = movies.size) { index ->
                        val movie = movies[index]
                        MovieCard(navController = navController, movie = movie)
                        if (index == movies.size - 1) {
                            viewModel.getMovies(sort = selectedItem, language = CURRENT_LANGUAGE)
                            Height(height = 30.dp)
                        }
                    }
                }

                val showUpButton = gridState.firstVisibleItemIndex > 4
                if (showUpButton)
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 24.dp)
                            .align(Alignment.BottomEnd)
                            .size(40.dp)
                            .clip(RoundedCornerShape(40.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable {
                                gridState.scrollToFirst(scope)
                            }
                            .padding(10.dp),
                        imageVector = Icons.Filled.ArrowUpward,
                        contentDescription = null,
                        tint = backgroundLight
                    )

            }
        }
    }

}

private fun initData(viewModel: MoviesViewModel, sort: SortType) {
    viewModel.clear()
    viewModel.getMovies(sort = sort, language = CURRENT_LANGUAGE)
}

