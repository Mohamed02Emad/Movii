package com.mo.movie.android.features.details.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mo.movie.android.core.composables.text.AppText
import com.mo.movie.android.core.utils.extentions.round

@Composable
fun RateComposable(
    modifier: Modifier = Modifier,
    rate: Float = 0f,
    rateRange: Int = 10,
    size: Dp = 48.dp,
    notActiveColor: Color = Color(0xFF575656),
    activeColor: Color = MaterialTheme.colorScheme.primary,
    showRate : Boolean = false,
) {
    require(rate in 0f..rateRange.toFloat()) { "Rate must be between 0.0 and $rateRange" }
    require(rateRange > 0) { "Rate range must be a positive integer" }

    val sweepAngle = (rate / rateRange) * 360f
    val leftAngle = 360 - sweepAngle
    val width = 12f
    Box(modifier = modifier.size(size),contentAlignment = Alignment.Center) {
        Canvas(modifier = modifier.size(size)) {
            drawArc(
                color = notActiveColor,
                startAngle = sweepAngle,
                size = Size(width = size.toPx(), height = size.toPx()),
                sweepAngle = leftAngle,
                useCenter = false,
                style = Stroke(
                    width = width,
                )
            )
            drawArc(
                color = activeColor,
                startAngle = 0f,
                size = Size(width = size.toPx(), height = size.toPx()),
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(
                    width = width,
                )
            )
        }
        if (showRate) {
            AppText(text = rate.round(2).toString(), fontSize = 13.sp)
        }
    }
}
