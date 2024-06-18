package com.mo.movie.android.core.composables.buttons
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mo.movie.android.IS_RTL

@Composable
fun BackButton(
    onClick: () -> Unit
) {
       Box(
           modifier = Modifier
               .size(35.dp)
               .clip(RoundedCornerShape(35.dp))
               .clickable(onClick = onClick),
           contentAlignment = Alignment.Center
       ) {
           Icon(
               imageVector = if(IS_RTL.not())  Icons.Default.ArrowForward else Icons.Default.ArrowBack,
               contentDescription = null,
               modifier = Modifier.size(24.dp),
               tint = MaterialTheme.colorScheme.onSurface
           )
       }
}

@Preview
@Composable
private fun Preview() {
    BackButton(){

    }
}