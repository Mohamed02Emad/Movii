package com.mo.movie.features.auth.presentation

data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
)