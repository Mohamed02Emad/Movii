package com.mo.movie.features.more.settings.domain.useCases

import com.mo.movie.features.more.settings.domain.repositories.SettingsRepository
import org.koin.core.component.KoinComponent

class GetDarkModeUseCase(private val repository: SettingsRepository) : KoinComponent {
    suspend operator fun invoke() : Boolean {
        return repository.getDarkMode()
    }
}

