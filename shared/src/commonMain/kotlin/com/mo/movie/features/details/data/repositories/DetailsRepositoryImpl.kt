package com.mo.movie.features.details.data.repositories

import com.mo.movie.core.base.RequestState
import com.mo.movie.core.local.prefrences.AppPreferences
import com.mo.movie.features.details.data.datasources.DetailsDataSource
import com.mo.movie.features.details.domain.models.responses.CastResponse
import com.mo.movie.features.details.domain.models.responses.MovieDetailsResponse
import com.mo.movie.features.details.domain.models.responses.VideosResponse
import com.mo.movie.features.details.domain.repositories.DetailsRepository
import com.mo.movie.features.details.domain.usecases.GetCastParams
import com.mo.movie.features.details.domain.usecases.GetMovieDetailParams
import com.mo.movie.features.details.domain.usecases.GetRecommendationsParams
import com.mo.movie.features.details.domain.usecases.GetVideosParams
import com.mo.movie.features.home.domain.models.responses.MoviesResponse
import org.koin.core.component.KoinComponent

class DetailsRepositoryImpl(
    val local: AppPreferences,
    val remote: DetailsDataSource,
) : DetailsRepository, KoinComponent {

    override suspend fun getMovieDetails(params: GetMovieDetailParams): RequestState<MovieDetailsResponse> {
        return remote.getMovieDetails(params = params)
    }

    override suspend fun getCast(params: GetCastParams): RequestState<CastResponse> {
        return remote.getCast(params = params)
    }

    override suspend fun getVideos(params: GetVideosParams): RequestState<VideosResponse> {
        return remote.getVideos(params = params)
    }

    override suspend fun getRecommendations(params: GetRecommendationsParams): RequestState<MoviesResponse> {
        return remote.getRecommendations(params = params)
    }
}