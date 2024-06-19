package com.mo.movie.di.moviesModule

import com.mo.movie.features.home.data.datasources.HomeDataSource
import com.mo.movie.features.home.data.datasources.HomeDataSourceImpl
import com.mo.movie.features.home.data.repositories.HomeRepositoryImpl
import com.mo.movie.features.home.domain.repositories.HomeRepository
import com.mo.movie.features.home.domain.usecases.GetTrendingUseCase
import com.mo.movie.features.movies.data.datasources.MoviesDataSource
import com.mo.movie.features.movies.data.datasources.MoviesDataSourceImpl
import com.mo.movie.features.movies.data.repositories.MoviesRepositoryImpl
import com.mo.movie.features.movies.domain.repositories.MoviesRepository
import com.mo.movie.features.movies.domain.usecases.GetMoviesUseCase
import io.ktor.util.InternalAPI
import org.koin.dsl.module

@OptIn(InternalAPI::class)
val moviesModule = module {
    /** data source **/
    single<MoviesDataSource> { MoviesDataSourceImpl(get()) }
    /** repositories **/
    single<MoviesRepository> { MoviesRepositoryImpl(get(), get()) }
    /** use cases **/
    single { GetMoviesUseCase(get()) }
}