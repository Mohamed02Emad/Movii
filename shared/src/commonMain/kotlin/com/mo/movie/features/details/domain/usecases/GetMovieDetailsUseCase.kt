package com.mo.movie.features.details.domain.usecases

import com.mo.movie.core.base.RequestState
import com.mo.movie.features.details.domain.models.responses.MovieDetailsResponse
import com.mo.movie.features.details.domain.repositories.DetailsRepository
import com.mo.movie.features.more.settings.domain.models.Languages
import org.koin.core.component.KoinComponent

class GetMovieDetailsUseCase(private val repository: DetailsRepository): KoinComponent {
    suspend operator fun invoke(params : GetMovieDetailParams): RequestState<MovieDetailsResponse> {
        return repository.getMovieDetails(params = params)
    }
}

data class GetMovieDetailParams(val id : Int, val language : Languages)


