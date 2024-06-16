package com.mo.movie.di.setupModules

import com.mo.movie.features.onBoarding.data.dataSource.OnBoardingDataSource
import com.mo.movie.features.onBoarding.data.dataSource.OnBoardingDataSourceImpl
import com.mo.movie.features.onBoarding.data.repository.OnBoardingRepositoryImpl
import com.mo.movie.features.onBoarding.domain.repositories.OnBoardingRespository
import com.mo.movie.features.onBoarding.domain.useCases.GetOnBoardingFinishedUseCase
import com.mo.movie.features.onBoarding.domain.useCases.SetOnBoardingFinishedUseCase
import org.koin.dsl.module

val onBoardingModule = module {
    /**Data Sources**/
    single<OnBoardingDataSource> { OnBoardingDataSourceImpl(get()) }
    /**Repositories**/
    single<OnBoardingRespository> { OnBoardingRepositoryImpl(get()) }
    /**Use Case**/
    single { GetOnBoardingFinishedUseCase(get()) }
    single { SetOnBoardingFinishedUseCase(get()) }

}