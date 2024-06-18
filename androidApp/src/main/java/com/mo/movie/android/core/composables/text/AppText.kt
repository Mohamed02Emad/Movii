package com.mo.movie.android.core.composables.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.mo.movie.android.theme.fontFamilyOverPass

@Composable
fun AppText(
    text: String,
    modifier : Modifier = Modifier,
    fontFamily: FontFamily = fontFamilyOverPass,
    fontSize: TextUnit = 18.sp,
    textAlign: TextAlign = TextAlign.Center,
    fontColor :Color = MaterialTheme.colorScheme.onPrimary,
) {
    Text(
        modifier = modifier,
        text = text,
        fontFamily = fontFamily,
        textAlign = textAlign,
        style = TextStyle(fontSize = fontSize, color = fontColor)
    )
}