package com.mo.movie.features.details.domain.usecases

import com.mo.movie.core.base.RequestState
import com.mo.movie.features.details.domain.enums.MovieShowType
import com.mo.movie.features.details.domain.models.responses.VideosResponse
import com.mo.movie.features.details.domain.repositories.DetailsRepository
import org.koin.core.component.KoinComponent

class GetVideosUseCase(private val repository: DetailsRepository) : KoinComponent {
    suspend operator fun invoke(params: GetVideosParams): RequestState<VideosResponse> {
        return repository.getVideos(params = params)
    }
}

data class GetVideosParams(val id: Int, val type: MovieShowType)


