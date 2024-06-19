package com.mo.movie.android.features.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mo.movie.android.CURRENT_LANGUAGE
import com.mo.movie.android.R
import com.mo.movie.android.core.composables.HandleRequestStateUi
import com.mo.movie.android.core.composables.Height
import com.mo.movie.android.core.composables.Width
import com.mo.movie.android.core.composables.YoutubeView
import com.mo.movie.android.core.composables.buttons.BackButton
import com.mo.movie.android.core.composables.text.AppText
import com.mo.movie.android.core.navigation.pop
import com.mo.movie.android.core.utils.UiUtils.showToast
import com.mo.movie.android.features.details.composables.RateComposable
import com.mo.movie.android.features.home.presentation.composables.MovieCard
import com.mo.movie.android.theme.backgroundLight
import com.mo.movie.core.remote.IMAGES_BASE_URL
import com.mo.movie.features.details.presentaion.DetailsViewModel

@Composable
fun DetailsScreen(navController: NavHostController, id: Int, viewModel: DetailsViewModel) {
    LaunchedEffect(key1 = true) {
        initData(id, viewModel)
    }
    val context = LocalContext.current
    val movieState = viewModel.movieState.collectAsState()
    val movieDetails = viewModel.movieDetails.collectAsState().value
    val cast = viewModel.cast.collectAsState().value
    val videos = viewModel.videos.collectAsState().value
    val recommendations = viewModel.recommendations.collectAsState().value
    var screenVideosState by remember {
        mutableStateOf(true)
    }
    navController.addOnDestinationChangedListener { _, destination, arguments ->
        val idFromDestination = arguments?.getString("id") ?: "-1"
        screenVideosState = if (idFromDestination == id.toString()) {
            true
        } else {
            false
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HandleRequestStateUi(modifier = Modifier.fillMaxSize(),state = movieState.value) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Box {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9.5f)
                                .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                                .blur(10.dp),
                            contentScale = ContentScale.Crop,
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(IMAGES_BASE_URL + movieDetails?.background)
                                .crossfade(true)
                                .diskCacheKey(movieDetails?.background)
                                .build(),
                            error = painterResource(id = R.drawable.logo),
                            contentDescription = movieDetails?.title,
                        )
//                        if (Build.VERSION.SDK_INT < 31)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(16f / 9.5f)
                                    .clip(
                                        RoundedCornerShape(
                                            bottomEnd = 16.dp,
                                            bottomStart = 16.dp
                                        )
                                    )
                                    .background(
                                        Brush.linearGradient(
                                            listOf(
                                                Color(0, 0, 0, 200),
                                                Color(0, 0, 0, 160),
                                                Color(0, 0, 0, 140),
                                                Color(0, 0, 0, 120),
                                                Color(0, 0, 0, 100),
                                            )
                                        )
                                    )
                                    .blur(16.dp)
                            ) {}
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9.5f)
                                .align(Alignment.TopStart)
                        ) {
                            val shape = RoundedCornerShape(8.dp)
                            AsyncImage(
                                modifier = Modifier
                                    .align(Alignment.Bottom)
                                    .padding(start = 12.dp, bottom = 8.dp, top = 12.dp)
                                    .width(115.dp)
                                    .aspectRatio(9 / 14f)
                                    .clip(shape)
                                    .border(width = 0.05.dp, color = backgroundLight, shape = shape)
                                    .background(MaterialTheme.colorScheme.secondaryContainer),
                                contentScale = ContentScale.Crop,
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(IMAGES_BASE_URL + movieDetails?.poster)
                                    .crossfade(true)
                                    .diskCacheKey(movieDetails?.poster)
                                    .build(),
                                error = painterResource(id = R.drawable.logo),
                                contentDescription = movieDetails?.title,
                            )
                            Column {
                                AppText(
                                    modifier = Modifier.padding(start = 10.dp, top = 26.dp),
                                    text = movieDetails?.title ?: ""
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(start = 10.dp)
                                            .size(20.dp),
                                        imageVector = Icons.Outlined.CalendarToday,
                                        contentDescription = null
                                    )
                                    Width(width = 4.dp)
                                    AppText(text = movieDetails?.releaseDate ?: "")
                                    val county =
                                        if (movieDetails?.originCountry != null) " (${movieDetails.originCountry.first()})" else ""
                                    AppText(text = county)
                                }
                                Height(height = 8.dp)
                                Box(
                                    Modifier.padding(start = 8.dp)
                                ) {
                                    RateComposable(
                                        rate = movieDetails?.voteAverage?.toFloat() ?: 0f,
                                        showRate = true,
                                    )
                                }
                                Height(height = 6.dp)
                                AppText(
                                    modifier = Modifier.padding(start = 10.dp),
                                    text = movieDetails?.tagLine ?: "",
                                    fontSize = 12.sp,
                                    fontColor = Color(187, 187, 187, 255)
                                )
                                LazyRow(modifier = Modifier.padding(start = 1.dp)) {
                                    val genres = movieDetails?.genres ?: emptyList()
                                    val shape = RoundedCornerShape(8.dp)
                                    items(genres.size) { index ->
                                        Box(
                                            modifier = Modifier
                                                .padding(horizontal = 4.dp, vertical = 4.dp)
                                                .padding(
                                                    start = if (index == 0) 6.dp else 0.dp,
                                                    end = if (index == genres.size - 1) 6.dp else 0.dp
                                                )
                                                .clip(shape)
                                                .border(
                                                    width = 1.dp,
                                                    color = MaterialTheme.colorScheme.primary,
                                                    shape = shape
                                                )
                                                .background(
                                                    MaterialTheme.colorScheme.secondaryContainer.copy(
                                                        alpha = 0.5f
                                                    )
                                                )
                                                .padding(vertical = 4.dp, horizontal = 6.dp)
                                        ) {
                                            AppText(
                                                text = genres[index].name,
                                                fontColor = backgroundLight,
                                                fontSize = 13.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    videos?.let { videosList ->
                        val hasTrailer = videosList.any { it.type == "Trailer" }
                        if(hasTrailer) {
                            val trailer = videosList.first { it.type == "Trailer" }
                            Column {
                                Height(height = 12.dp)
                                AppText(
                                    modifier = Modifier.padding(horizontal = 6.dp),
                                    text = context.getString(R.string.trailer),
                                )
                                Height(height = 6.dp)
                                if(screenVideosState)
                                YoutubeView(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                        .fillMaxWidth()
                                        .aspectRatio(16 / 9f)
                                        .clip(shape = RoundedCornerShape(12.dp))
                                        .border(
                                            width = 0.05.dp,
                                            color = backgroundLight,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .background(MaterialTheme.colorScheme.secondaryContainer),
                                    videoId = trailer.key,
                                    autoPlay = true,
                                )
                            }
                        }
                    }
                }
                item {
                    movieDetails?.overview?.let {
                        Column {
                            Height(height = 12.dp)
                            AppText(
                                modifier = Modifier.padding(horizontal = 12.dp),
                                text = context.getString(R.string.overview),
                            )
                            AppText(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = it,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Start,
                                fontColor = Color(187, 187, 187, 255)
                            )
                        }
                    }
                }
                item {
                    cast?.cast?.let { castList ->
                        Column {
                            Height(height = 12.dp)
                            AppText(
                                modifier = Modifier.padding(horizontal = 12.dp),
                                text = context.getString(R.string.cast),
                            )
                            Height(height = 6.dp)
                            LazyRow(modifier = Modifier.padding(start = 1.dp)) {
                                val shape = RoundedCornerShape(8.dp)
                                item {
                                    Width(width = 6.dp)
                                }
                                items(castList.size) { index ->
                                    val castItem = castList[index]
                                    Column(
                                        modifier = Modifier
                                            .padding(start = 6.dp, end = 6.dp, bottom = 8.dp)
                                            .width(140.dp)
                                    ) {
                                        AsyncImage(
                                            modifier = Modifier
                                                .width(140.dp)
                                                .aspectRatio(9 / 12f)
                                                .clip(shape)
                                                .border(
                                                    width = 0.05.dp,
                                                    color = backgroundLight,
                                                    shape = shape
                                                )
                                                .background(MaterialTheme.colorScheme.secondaryContainer),
                                            contentScale = ContentScale.Crop,
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(IMAGES_BASE_URL + castItem.profilePath)
                                                .crossfade(true)
                                                .diskCacheKey(castItem.profilePath)
                                                .error(R.drawable.guest)
                                                .build(),
                                            error = painterResource(id = R.drawable.logo_with_black_bg),
                                            contentDescription = castItem.name,
                                        )
                                        AppText(
                                            modifier = Modifier
                                                .padding(top = 2.dp)
                                                .width(140.dp),
                                            text = castItem.name,
                                            fontSize = 12.5.sp
                                        )
                                    }
                                }
                                item {
                                    Width(width = 6.dp)
                                }
                            }
                        }
                    }
                }
                item {
                    videos?.let { videosList ->
                        Column {
                            Height(height = 12.dp)
                            AppText(
                                modifier = Modifier.padding(horizontal = 12.dp),
                                text = context.getString(R.string.videos),
                            )
                            Height(height = 6.dp)
                            LazyRow(modifier = Modifier.padding(start = 1.dp)) {
                                val shape = RoundedCornerShape(8.dp)
                                item {
                                    Width(width = 6.dp)
                                }
                                items(videosList.size) { index ->
                                    val video = videosList[index]
                                    Column(
                                        modifier = Modifier
                                            .padding(start = 6.dp, end = 6.dp, bottom = 8.dp)
                                            .width(320.dp)
                                    ) {
                                        if(screenVideosState)
                                        YoutubeView(
                                            modifier = Modifier
                                                .width(320.dp)
                                                .aspectRatio(16 / 9f)
                                                .clip(shape)
                                                .border(
                                                    width = 0.05.dp,
                                                    color = backgroundLight,
                                                    shape = shape
                                                )
                                                .background(MaterialTheme.colorScheme.secondaryContainer),
                                            videoId = video.key
                                        )
                                    }
                                }
                                item {
                                    Width(width = 6.dp)
                                }
                            }
                        }
                    }
                }
                item {
                    recommendations?.let { recommendationsList ->
                        Column {
                            Height(height = 12.dp)
                            AppText(
                                modifier = Modifier.padding(horizontal = 12.dp),
                                text = context.getString(R.string.recommendations),
                            )
                            Height(height = 6.dp)
                            LazyRow(modifier = Modifier.padding(start = 1.dp)) {
                                item {
                                    Width(width = 6.dp)
                                }
                                items(recommendationsList.size) { index ->
                                    val movie = recommendationsList[index]
                                    Column(
                                        modifier = Modifier
                                            .padding(start = 6.dp, end = 6.dp, bottom = 8.dp)
                                            .width(185.dp)
                                    ) {
                                        MovieCard(navController = navController, movie = movie )
                                    }
                                }
                                item {
                                    Width(width = 6.dp)
                                }
                            }
                        }
                    }
                }
                item{
                    Height(height = 20.dp)
                }
            }
        }
        Height(height = 8.dp)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(40.dp)
            .padding(horizontal = 16.dp), contentAlignment = Alignment.TopEnd
    ) {
        BackButton {
            navController.pop()
        }
    }
}

private fun initData(id: Int, viewModel: DetailsViewModel) {
    viewModel.clear()
    viewModel.getTrendingMovies(id, CURRENT_LANGUAGE)
    viewModel.getCast(id)
    viewModel.getVideos(id)
    viewModel.getRecommendations(id , language = CURRENT_LANGUAGE)
}
