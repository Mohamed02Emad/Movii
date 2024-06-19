package com.mo.movie.features.home.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    var id: Int,
    @SerialName("adult")
    var isForAdult: Boolean = false ,
    @SerialName("backdrop_path")
    var backdropPath: String?,
    @SerialName("genre_ids")
    var genreIds: List<Int>? = null,
    @SerialName("original_language")
    var originalLanguage: String? ="en",
    @SerialName("original_title")
    var originalTitle: String? = null,
    var overview: String,
    var popularity: Double,
    @SerialName("poster_path")
    var posterPath: String,
    @SerialName("release_date")
    var releaseDate: String? = null,
    var title: String,
    var video: Boolean? = false,
    @SerialName("vote_average")
    var voteAverage: Double,
    @SerialName("vote_count")
    var voteCount: Int
)