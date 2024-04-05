package com.mo.newbase.android.core.composables.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mo.newbase.android.theme.rippleColor

@Composable
fun TextButton(
    text: String,
    textSize: TextUnit = 14.sp,
    rippleColour: Color = rippleColor,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    hPadding: Dp = 0.dp,
    vPadding: Dp = 0.dp,
    onClick: () -> Unit,
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val clickableModifier = Modifier
        .clip(MaterialTheme.shapes.medium)
        .clickable(
            interactionSource = interactionSource,
            indication = rememberRipple(color = rippleColour)
        ) {
            onClick()
        }
        .background(color = Color.Transparent)
        .padding(horizontal = hPadding, vertical = vPadding)
    BasicText(
        modifier = clickableModifier,
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = textSize, color = textColor)
    )
}

@Preview
@Composable
fun TextButtonPreview() = Box(
    modifier = Modifier
        .background(color = Color.White)
        .padding(20.dp)
) {
    TextButton(text = "mohamed") {}
}