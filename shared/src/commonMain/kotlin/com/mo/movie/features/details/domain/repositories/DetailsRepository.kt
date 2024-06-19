package com.mo.movie.features.details.domain.repositories

import com.mo.movie.core.base.RequestState
import com.mo.movie.features.details.domain.models.responses.CastResponse
import com.mo.movie.features.details.domain.models.responses.MovieDetailsResponse
import com.mo.movie.features.details.domain.models.responses.VideosResponse
import com.mo.movie.features.details.domain.usecases.GetCastParams
import com.mo.movie.features.details.domain.usecases.GetMovieDetailParams
import com.mo.movie.features.details.domain.usecases.GetRecommendationsParams
import com.mo.movie.features.details.domain.usecases.GetVideosParams
import com.mo.movie.features.home.domain.models.responses.MoviesResponse


interface DetailsRepository {
    suspend fun getMovieDetails(params: GetMovieDetailParams): RequestState<MovieDetailsResponse>
    suspend fun getCast(params: GetCastParams): RequestState<CastResponse>
    suspend fun getVideos(params: GetVideosParams): RequestState<VideosResponse>
    suspend fun getRecommendations(params: GetRecommendationsParams): RequestState<MoviesResponse>

}