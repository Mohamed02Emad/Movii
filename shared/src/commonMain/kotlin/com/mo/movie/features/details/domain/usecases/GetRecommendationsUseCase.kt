package com.mo.movie.features.details.domain.usecases

import com.mo.movie.core.base.RequestState
import com.mo.movie.features.details.domain.enums.MovieShowType
import com.mo.movie.features.details.domain.models.responses.VideosResponse
import com.mo.movie.features.details.domain.repositories.DetailsRepository
import com.mo.movie.features.home.domain.models.responses.MoviesResponse
import com.mo.movie.features.more.settings.domain.models.Languages
import org.koin.core.component.KoinComponent

class GetRecommendationsUseCase(private val repository: DetailsRepository) : KoinComponent {
    suspend operator fun invoke(params: GetRecommendationsParams): RequestState<MoviesResponse> {
        return repository.getRecommendations(params = params)
    }
}

data class GetRecommendationsParams(val id: Int, val type: MovieShowType , val language: Languages)


