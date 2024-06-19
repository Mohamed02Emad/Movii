package com.mo.movie.features.details.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    var name: String,
    @SerialName("profile_path")
    var profilePath: String? = null,
)