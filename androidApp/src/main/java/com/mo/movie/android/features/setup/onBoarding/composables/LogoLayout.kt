package com.mo.movie.android.features.setup.onBoarding.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mo.movie.android.R
import com.mo.movie.android.core.composables.HSpace
import com.mo.movie.android.core.composables.text.LargeText

@Composable
fun LogoLayout() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.width(165.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo"
        )
    }
}