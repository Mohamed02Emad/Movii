package com.mo.movie.android.features.home.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mo.movie.android.R
import com.mo.movie.android.core.composables.Height
import com.mo.movie.android.core.composables.Width
import com.mo.movie.android.core.navigation.push
import com.mo.movie.android.core.utils.extentions.round
import com.mo.movie.android.theme.fontFamilyOverPass
import com.mo.movie.core.navigation.Screen
import com.mo.movie.core.remote.IMAGES_BASE_URL
import com.mo.movie.features.home.domain.models.Movie

@Composable
fun MovieCard(
    navController: NavHostController,
    movie: Movie,
) {
    val shape = RoundedCornerShape(12.dp)
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .aspectRatio(1 / 1.85f)
            .clip(shape)
            .border(width = 0.5.dp , color=MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f) , shape = shape)
            .clickable {
                openMovieDetails(movie , navController)
            },
        ) {
        Box( modifier = Modifier
            .aspectRatio(1 / 1.85f)
            .clip(shape)
            .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1 / 1.35f)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    contentScale = ContentScale.FillWidth,
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(IMAGES_BASE_URL + movie.posterPath)
                        .crossfade(true)
                        .diskCacheKey(movie.posterPath)
                        .build(),
                    error = painterResource(id = R.drawable.logo),
                    contentDescription = movie.title,
                )
                Height(height = 4.dp)
                Row(
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = movie.title,
                        maxLines = 1,
                        fontFamily = fontFamilyOverPass,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 16.sp
                        )
                    )
                    Width(width = 4.dp)
                    Text(
                        text = movie.originalLanguage ?: "",
                        maxLines = 1,
                        fontFamily = fontFamilyOverPass,
                        style = TextStyle(color = Color.Red, fontSize = 16.sp)
                    )
                }
                Height(height = 4.dp)
                movie.releaseDate?.let {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp)
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Start,
                            text = it,
                            maxLines = 1,
                            fontFamily = fontFamilyOverPass,
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 12.sp
                            )
                        )
                    }
                    Height(height = 4.dp)
                }
                Row(
                    modifier = Modifier.padding(horizontal = 4.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(26.dp)
                            .clip(RoundedCornerShape(35.dp))
                            .padding(2.dp),
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(214, 171, 90, 255)
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = movie.voteAverage.round(2).toString(),
                        maxLines = 1,
                        fontFamily = fontFamilyOverPass,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 12.sp
                        )
                    )
                }

            }
        }
    }
}

fun openMovieDetails(movie: Movie, navController: NavHostController) {
    val destination = Screen.Detail
    destination.updateRoute(movie.id)
    navController.push(destination )
}
