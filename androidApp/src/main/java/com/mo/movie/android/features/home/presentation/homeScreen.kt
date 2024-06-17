package com.mo.movie.android.features.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mo.movie.android.R
import com.mo.movie.android.core.composables.Height
import com.mo.movie.android.core.composables.ImagesSlider

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Top , horizontalAlignment = Alignment.CenterHorizontally) {
        Height(20.dp)
        ImagesSlider(
            images = listOf(
                painterResource(id = R.drawable.home_image),
                painterResource(id = R.drawable.home_image2),
                painterResource(id = R.drawable.home_image3),
                painterResource(id = R.drawable.home_image4),
            )
        )
    }
}