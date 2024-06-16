package com.mo.movie

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform