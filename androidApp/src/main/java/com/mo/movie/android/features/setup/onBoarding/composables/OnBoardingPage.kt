package com.mo.movie.android.features.setup.onBoarding.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mo.movie.android.core.composables.text.AppText

@Composable
fun OnBoardingPage(modifier: Modifier = Modifier, image: Int, text: String) {
    Column(verticalArrangement = Arrangement.SpaceEvenly, modifier = modifier) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .fillMaxWidth()
                .height(260.dp)
        )
        Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.Center) {
            AppText(text = text , modifier = Modifier
                .padding(horizontal = 16.dp) )
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}