package com.mo.movie.features.movies.domain.enums

enum class SortType {
    POPULAR,TOP_RATED,NOW_PLAYING,UPCOMING;

    val displayName: String
        get() = this.name.lowercase().replace("_"," ")
    val apiName: String
        get() = this.name.lowercase()
}