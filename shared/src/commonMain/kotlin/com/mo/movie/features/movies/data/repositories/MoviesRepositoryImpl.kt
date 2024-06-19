package com.mo.movie.features.movies.data.repositories

import com.mo.movie.core.base.RequestState
import com.mo.movie.core.local.prefrences.AppPreferences
import com.mo.movie.features.home.data.datasources.HomeDataSource
import com.mo.movie.features.home.domain.models.responses.MoviesResponse
import com.mo.movie.features.home.domain.repositories.HomeRepository
import com.mo.movie.features.home.domain.usecases.GetTrendingParams
import com.mo.movie.features.movies.data.datasources.MoviesDataSource
import com.mo.movie.features.movies.domain.repositories.MoviesRepository
import com.mo.movie.features.movies.domain.usecases.GetMoviesParams
import org.koin.core.component.KoinComponent

class MoviesRepositoryImpl(
    val local: AppPreferences,
    val remote: MoviesDataSource,
) : MoviesRepository, KoinComponent {

    override suspend fun getMovies(params: GetMoviesParams): RequestState<MoviesResponse> {
        return remote.getMovies(params = params)
    }
}