package com.mo.movie.features.home.domain.usecases

import com.mo.movie.core.base.RequestState
import com.mo.movie.features.home.domain.enums.TrendingFilter
import com.mo.movie.features.home.domain.models.responses.MoviesResponse
import com.mo.movie.features.home.domain.repositories.HomeRepository
import com.mo.movie.features.more.settings.domain.models.Languages
import org.koin.core.component.KoinComponent

class GetTrendingUseCase(private val repository: HomeRepository): KoinComponent {
    suspend operator fun invoke(params : GetTrendingParams): RequestState<MoviesResponse> {
        return repository.getTrendingMovies(params = params)
    }
}

data class GetTrendingParams(val page : Int, val language : Languages, val filter : TrendingFilter)


