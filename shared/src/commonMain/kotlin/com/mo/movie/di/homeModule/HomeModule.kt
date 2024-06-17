package com.mo.movie.di.homeModule

import com.mo.movie.features.home.data.datasources.HomeDataSource
import com.mo.movie.features.home.data.datasources.HomeDataSourceImpl
import com.mo.movie.features.home.data.repositories.HomeRepositoryImpl
import com.mo.movie.features.home.domain.repositories.HomeRepository
import com.mo.movie.features.home.domain.usecases.GetTrendingUseCase
import io.ktor.util.InternalAPI
import org.koin.dsl.module

@OptIn(InternalAPI::class)
val homeModule = module {
    /** data source **/
    single<HomeDataSource> { HomeDataSourceImpl(get()) }
    /** repositories **/
    single<HomeRepository> { HomeRepositoryImpl(get(), get()) }
    /** use cases **/
    single { GetTrendingUseCase(get()) }
}