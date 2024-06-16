package com.mo.movie.features.more.settings.domain.useCases

import com.mo.movie.features.more.settings.domain.repositories.SettingsRepository
import org.koin.core.component.KoinComponent

class GetLanguageUseCase(private val repository: SettingsRepository) : KoinComponent {
    suspend operator fun invoke(): String {
        return repository.getLanguage()
    }
}

