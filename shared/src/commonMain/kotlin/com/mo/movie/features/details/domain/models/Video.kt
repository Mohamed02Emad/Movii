package com.mo.movie.features.details.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Video(
    var key: String,
    var name: String,
    var site: String,
    var type: String
)