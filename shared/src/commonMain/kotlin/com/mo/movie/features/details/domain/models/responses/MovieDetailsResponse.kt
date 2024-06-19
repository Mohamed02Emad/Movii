package com.mo.movie.features.details.domain.models.responses

import com.mo.movie.core.base.BaseResponse
import com.mo.movie.features.details.domain.models.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
    var id: Int,
    @SerialName("origin_country")
    var originCountry: List<String>,
    var overview: String,
    @SerialName("poster_path")
    var poster: String,
    @SerialName("backdrop_path")
    var background: String,
    @SerialName("release_date")
    var releaseDate: String,
    @SerialName("tagline")
    var tagLine: String,
    var title: String,
    @SerialName("video")
    var hasVideo: Boolean,
    @SerialName("vote_average")
    var voteAverage: Double,
    var genres: List<Genre>,

    ): BaseResponse()