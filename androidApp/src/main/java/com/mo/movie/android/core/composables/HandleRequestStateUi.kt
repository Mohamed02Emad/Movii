package com.mo.movie.android.core.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mo.movie.android.R
import com.mo.movie.android.core.composables.buttons.ButtonWithIcon
import com.mo.movie.android.theme.fontFamilyOverPass
import com.mo.movie.core.base.BaseState

@Composable
fun <T> HandleRequestStateUi(
    state: BaseState<T>,
    modifier: Modifier = Modifier,
    onTryAgain : (()-> Unit)? = null,
    child: @Composable () -> Unit,
) {
    val context = LocalContext.current
    when (state) {
        is BaseState.Error -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Column {
                    Text(
                        state.message,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        fontFamily = fontFamilyOverPass,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 18.sp
                        )
                    )
                    onTryAgain?.let {
                        CenterHorizontal {
                            ButtonWithIcon(
                                title = context.getString(R.string.try_again),
                                painter = painterResource(
                                    id = R.drawable.try_again
                                ),
                                iconTint = MaterialTheme.colorScheme.onPrimary
                            ) {
                                it()
                            }
                        }
                    }
                }
            }
        }

        is BaseState.Success, is BaseState.PagingLoading -> {
            Box(modifier = modifier) {
                    child()
                if (state is BaseState.PagingLoading) {
                    LoadingAnimation(modifier=Modifier.padding(bottom = 8.dp).align(Alignment.BottomCenter) ,margin = PaddingValues(bottom = 20.dp), indicatorSize = 26.dp)
                }
            }
        }

        else -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                LoadingAnimation()
            }
        }
    }
}