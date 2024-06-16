package com.mo.movie.features.onBoarding.domain.useCases

import com.mo.movie.features.onBoarding.domain.repositories.OnBoardingRespository
import org.koin.core.component.KoinComponent

class GetOnBoardingFinishedUseCase(private val repository: OnBoardingRespository) : KoinComponent {
    suspend operator fun invoke(): Boolean {
        return repository.getIsOnBoardingFinished()
    }

}