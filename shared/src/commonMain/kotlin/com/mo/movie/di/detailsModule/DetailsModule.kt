package com.mo.movie.di.detailsModule

import com.mo.movie.features.details.data.datasources.DetailsDataSource
import com.mo.movie.features.details.data.datasources.DetailsDataSourceImpl
import com.mo.movie.features.details.data.repositories.DetailsRepositoryImpl
import com.mo.movie.features.details.domain.repositories.DetailsRepository
import com.mo.movie.features.details.domain.usecases.GetCastUseCase
import com.mo.movie.features.details.domain.usecases.GetMovieDetailsUseCase
import com.mo.movie.features.details.domain.usecases.GetVideosUseCase
import com.mo.movie.features.home.data.repositories.HomeRepositoryImpl
import com.mo.movie.features.home.domain.repositories.HomeRepository
import com.mo.movie.features.home.data.datasources.HomeDataSourceImpl
import com.mo.movie.features.home.domain.usecases.GetTrendingUseCase
import io.ktor.util.InternalAPI
import org.koin.dsl.module

@OptIn(InternalAPI::class)
val detailsModule = module {
    /** data source **/
    single<DetailsDataSource> { DetailsDataSourceImpl(get()) }
    /** repositories **/
    single<DetailsRepository> { DetailsRepositoryImpl(get(), get()) }
    /** use cases **/
    single { GetMovieDetailsUseCase(get()) }
    single { GetCastUseCase(get()) }
    single { GetVideosUseCase(get()) }
}