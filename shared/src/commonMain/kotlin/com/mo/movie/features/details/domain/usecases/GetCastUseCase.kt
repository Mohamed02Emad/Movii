package com.mo.movie.features.details.domain.usecases

import com.mo.movie.core.base.RequestState
import com.mo.movie.features.details.domain.enums.MovieShowType
import com.mo.movie.features.details.domain.models.responses.CastResponse
import com.mo.movie.features.details.domain.models.responses.MovieDetailsResponse
import com.mo.movie.features.details.domain.repositories.DetailsRepository
import com.mo.movie.features.more.settings.domain.models.Languages
import org.koin.core.component.KoinComponent

class GetCastUseCase(private val repository: DetailsRepository): KoinComponent {
    suspend operator fun invoke(params : GetCastParams): RequestState<CastResponse> {
        return repository.getCast(params = params)
    }
}

data class GetCastParams(val id : Int, val type : MovieShowType)


