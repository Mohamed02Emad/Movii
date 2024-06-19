package com.mo.movie.android.features.home.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mo.movie.android.R
import com.mo.movie.android.core.composables.Width
import com.mo.movie.android.core.composables.text.AppText
import com.mo.movie.android.theme.fontFamilyOverPass
import com.mo.movie.features.home.domain.enums.TrendingFilter

@Composable
fun HomeFilterButton(
    selectedFilter: TrendingFilter,
    onFilterClicked: (TrendingFilter) -> Unit,
) {
    val context = LocalContext.current
    val shape = RoundedCornerShape(6.dp)
    Row(
        modifier = Modifier
            .padding(2.dp)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.primary , shape = shape)
            .padding(4.dp)
    ) {
        val firstSelectionColor =
            if (selectedFilter == TrendingFilter.DAY) MaterialTheme.colorScheme.primary else Color.Transparent
        Text(
            modifier = Modifier
                .clip(shape= shape)
                .background(color = firstSelectionColor)
                .padding(4.dp)
                .clickable {
                    if (selectedFilter == TrendingFilter.DAY) return@clickable
                    onFilterClicked(TrendingFilter.DAY)
                },
            text = context.getString(R.string.today),
            fontFamily = fontFamilyOverPass,
            style = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onPrimary)
        )
        Width(width = 4.dp)
        val secondSelectionColor = if (selectedFilter == TrendingFilter.WEEK) MaterialTheme.colorScheme.primary else Color.Transparent

        AppText(
            modifier = Modifier
                .clip(shape= shape)
                .background(color = secondSelectionColor)
                .padding(4.dp)
                .clickable {
                    if (selectedFilter == TrendingFilter.WEEK) return@clickable
                    onFilterClicked(TrendingFilter.WEEK)
                },
            text = context.getString(R.string.weekly),
        )
    }
}