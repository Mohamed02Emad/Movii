package com.mo.movie.android.core.composables.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.android.play.integrity.internal.c
import com.mo.movie.android.core.composables.Width
import com.mo.movie.android.theme.backgroundLight

@Composable
fun ButtonWithIcon(
    title: String,
    iconSize: Dp = 24.dp,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    padding: PaddingValues = PaddingValues(horizontal = 20.dp),
    textColor : Color = backgroundLight,
    buttonColor : Color = MaterialTheme.colorScheme.primary,
    isBordered : Boolean =false,
    iconTint : Color? = null,
    painter: Painter?= null,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .clip(shape)
            .background(if (isBordered) Color.Transparent else buttonColor)
            .border(
                width = 1.dp,
                color = buttonColor,
                shape = shape
            )
        ,
        colors = ButtonDefaults.buttonColors(containerColor = if (isBordered) Color.Transparent else buttonColor),
        shape = shape,
        contentPadding = padding,
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = TextStyle(
                    color = textColor,
                    fontWeight = FontWeight.Medium
                )
            )
            Width(width = 8.dp)
            painter?.let {
                Image(
                    painter = painter,
                    contentDescription = "icon",
                    modifier = Modifier
                        .size(iconSize)
                        .clip(
                            RoundedCornerShape(35.dp)
                        ),
                    colorFilter = if (iconTint == null) null else ColorFilter.tint(color = iconTint)
                )
            }
        }
    }
}