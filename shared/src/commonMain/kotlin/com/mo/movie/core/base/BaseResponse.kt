package com.mo.movie.core.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class BaseResponse(
    @SerialName("result")
    val result: Boolean? = null,
    val message: String? = null,
    val errors : Errors? = null
)

