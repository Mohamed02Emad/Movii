package com.mo.movie.android.core.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mo.movie.android.theme.fontFamilyOverPass
import com.mo.movie.core.base.BaseState
import com.mo.movie.core.utils.logit

@Composable
fun <T> HandleRequestStateUi(
    state: BaseState<T>,
    modifier: Modifier = Modifier,
    child: @Composable () -> Unit,
) {
    logit(state.toString())
    when (state) {
        is BaseState.Error -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Text(
                    state.message,
                    fontFamily = fontFamilyOverPass,
                    style = TextStyle(color = MaterialTheme.colorScheme.onPrimary, fontSize = 18.sp)
                )
            }
        }

        is BaseState.Success, is BaseState.PagingLoading -> {
            Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.weight(1f)) {
                    child()
                }
                if (state is BaseState.PagingLoading)
                LoadingAnimation(margin = PaddingValues(bottom = 20.dp),indicatorSize = 26.dp)
            }
        }

        else -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                LoadingAnimation()
            }
        }
    }
}