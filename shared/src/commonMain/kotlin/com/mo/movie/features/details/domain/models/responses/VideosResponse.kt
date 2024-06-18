package com.mo.movie.features.details.domain.models.responses

import com.mo.movie.core.base.BaseResponse
import com.mo.movie.features.details.domain.models.Video
import kotlinx.serialization.Serializable

@Serializable
data class VideosResponse(
    var results: List<Video>
) : BaseResponse()