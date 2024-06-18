package com.mo.movie.features.details.domain.models.responses

import com.mo.movie.core.base.BaseResponse
import com.mo.movie.features.details.domain.models.Cast
import kotlinx.serialization.Serializable

@Serializable
data class CastResponse(
    var cast: List<Cast>,
) : BaseResponse()