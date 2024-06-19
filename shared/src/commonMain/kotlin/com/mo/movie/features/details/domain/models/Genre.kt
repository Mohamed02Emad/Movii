package com.mo.movie.features.details.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    var id: Int,
    var name: String
)