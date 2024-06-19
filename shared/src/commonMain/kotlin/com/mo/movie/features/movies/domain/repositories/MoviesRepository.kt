package com.mo.movie.features.movies.domain.repositories

import com.mo.movie.core.base.RequestState
import com.mo.movie.features.home.domain.models.responses.MoviesResponse
import com.mo.movie.features.home.domain.usecases.GetTrendingParams
import com.mo.movie.features.movies.domain.usecases.GetMoviesParams


interface MoviesRepository {
    suspend fun getMovies(params: GetMoviesParams): RequestState<MoviesResponse>
}