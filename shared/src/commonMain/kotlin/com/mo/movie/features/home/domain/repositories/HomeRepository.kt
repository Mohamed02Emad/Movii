package com.mo.movie.features.home.domain.repositories

import com.mo.movie.core.base.RequestState
import com.mo.movie.features.home.domain.models.responses.MoviesResponse
import com.mo.movie.features.home.domain.usecases.GetTrendingParams


interface HomeRepository {
    suspend fun getTrendingMovies(params: GetTrendingParams): RequestState<MoviesResponse>
}