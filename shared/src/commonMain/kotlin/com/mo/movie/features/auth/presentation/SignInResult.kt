package com.mo.movie.features.auth.presentation

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

