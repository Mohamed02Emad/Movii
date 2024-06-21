package com.mo.movie.features.more.settings.domain.models

enum class Languages {
    ar,en,system;

    val displayName: String
        get() = when(this){
            ar -> "العربية"
            en -> "English"
            system -> "System/النظام"
        }
}