package com.mo.movie.features.home.data.repositories

import com.mo.movie.core.base.RequestState
import com.mo.movie.core.local.prefrences.AppPreferences
import com.mo.movie.features.home.data.datasources.HomeDataSource
import com.mo.movie.features.home.domain.models.responses.MoviesResponse
import com.mo.movie.features.home.domain.repositories.HomeRepository
import com.mo.movie.features.home.domain.usecases.GetTrendingParams
import org.koin.core.component.KoinComponent

class HomeRepositoryImpl(
    val local: AppPreferences,
    val remote: HomeDataSource,
) : HomeRepository, KoinComponent {

    private suspend fun getToken(): String? = local.getString(key = AppPreferences.TOKEN)
    override suspend fun getTrendingMovies(params: GetTrendingParams): RequestState<MoviesResponse> {
        return remote.getTrendingMovies(params = params)
    }
}