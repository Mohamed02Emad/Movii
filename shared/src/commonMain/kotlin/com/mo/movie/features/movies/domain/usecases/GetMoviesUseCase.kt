package com.mo.movie.features.movies.domain.usecases

import com.mo.movie.core.base.RequestState
import com.mo.movie.features.details.domain.enums.MovieShowType
import com.mo.movie.features.home.domain.enums.TrendingFilter
import com.mo.movie.features.home.domain.models.responses.MoviesResponse
import com.mo.movie.features.home.domain.repositories.HomeRepository
import com.mo.movie.features.more.settings.domain.models.Languages
import com.mo.movie.features.movies.domain.enums.SortType
import com.mo.movie.features.movies.domain.repositories.MoviesRepository
import org.koin.core.component.KoinComponent

class GetMoviesUseCase(private val repository: MoviesRepository) : KoinComponent {
    suspend operator fun invoke(params: GetMoviesParams): RequestState<MoviesResponse> {
        return repository.getMovies(params = params)
    }
}

data class GetMoviesParams(val page: Int, val language: Languages, val sort: SortType , val type : MovieShowType)


