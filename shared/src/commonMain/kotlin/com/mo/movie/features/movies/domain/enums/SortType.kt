package com.mo.movie.features.movies.domain.enums

import com.mo.movie.features.more.settings.domain.models.Languages

enum class SortType {
    POPULAR,TOP_RATED,NOW_PLAYING,UPCOMING;

    val displayName: String
        get() = this.name.lowercase().replace("_"," ")
    val apiName: String
        get() = this.name.lowercase()

}