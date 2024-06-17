package com.mo.movie.di.viewModelsModule

import com.mo.movie.SharedViewModel
import com.mo.movie.features.auth.presentation.AuthViewModel
import com.mo.movie.features.more.settings.presentation.SettingsViewModel
import com.mo.movie.features.onBoarding.presentation.OnBoardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


actual val viewModelsModule: Module = module {

    viewModel {
        OnBoardingViewModel()
    }
    viewModel {
        SharedViewModel()
    }
    viewModel {
        SettingsViewModel()
    }
    viewModel {
        AuthViewModel()
    }
}
