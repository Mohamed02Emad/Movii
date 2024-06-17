package com.mo.movie.core.base

import kotlinx.serialization.Serializable

@Serializable
open class BaseResponse(
    val message: String? = null,
    val errors : Errors? = null
)

