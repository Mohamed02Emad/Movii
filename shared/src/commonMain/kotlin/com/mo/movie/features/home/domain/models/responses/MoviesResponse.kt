package com.mo.movie.features.home.domain.models.responses

import com.mo.movie.core.base.BaseResponse
import com.mo.movie.features.home.domain.models.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    var page: Int,
    var results: List<Movie>,
    @SerialName("total_pages")
    var totalPages: Int,
    @SerialName("total_results")
    var totalResults: Int
) : BaseResponse()