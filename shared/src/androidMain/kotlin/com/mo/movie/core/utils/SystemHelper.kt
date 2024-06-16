package com.mo.movie.core.utils

import java.util.Locale

actual val currentLang:String?
    get() = Locale.getDefault().language

actual val currentCountry:String?
    get() = Locale.getDefault().country