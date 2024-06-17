package com.mo.movie.core.utils

fun logit(message: String?,tag: String = "Mohamed") {
    co.touchlab.kermit.Logger.d(
        tag = tag,
        messageString = message.toString(),
    )
}