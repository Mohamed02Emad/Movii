package com.mo.movie.android.core.utils.extentions

import androidx.compose.foundation.lazy.grid.LazyGridState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun LazyGridState.scrollToFirst(scope : CoroutineScope){
    scope.launch {
        val isScrolling = this@scrollToFirst.isScrollInProgress
        if(isScrolling) return@launch
        this@scrollToFirst.animateScrollToItem(index = 0)
    }
}