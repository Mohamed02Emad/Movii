package com.mo.movie.features.details.data.datasources

import com.mo.movie.core.base.RequestState
import com.mo.movie.core.remote.KtorClient
import com.mo.movie.features.details.domain.models.responses.CastResponse
import com.mo.movie.features.details.domain.models.responses.MovieDetailsResponse
import com.mo.movie.features.details.domain.models.responses.VideosResponse
import com.mo.movie.features.details.domain.usecases.GetCastParams
import com.mo.movie.features.details.domain.usecases.GetMovieDetailParams
import com.mo.movie.features.details.domain.usecases.GetRecommendationsParams
import com.mo.movie.features.details.domain.usecases.GetVideosParams
import com.mo.movie.features.home.domain.models.responses.MoviesResponse
import com.mo.movie.features.home.domain.usecases.GetTrendingParams
import io.ktor.util.InternalAPI
import org.koin.core.component.KoinComponent

interface DetailsDataSource {
    suspend fun getMovieDetails(params: GetMovieDetailParams): RequestState<MovieDetailsResponse>
    suspend fun getCast(params: GetCastParams): RequestState<CastResponse>
    suspend fun getVideos(params: GetVideosParams): RequestState<VideosResponse>
    suspend fun getRecommendations(params: GetRecommendationsParams): RequestState<MoviesResponse>
}

@InternalAPI
class DetailsDataSourceImpl(
    private val httpClient: KtorClient,
) : DetailsDataSource, KoinComponent {
    override suspend fun getMovieDetails(params: GetMovieDetailParams): RequestState<MovieDetailsResponse> {
        return try {
            val url =
                "movie/${params.id}?language=${params.language.name}"
            val response = httpClient.get(
                url = url,
            )
            httpClient.handleRequestState<MovieDetailsResponse>(response)
        } catch (e: Exception) {
            RequestState.Error(message = e.message.toString())
        }
    }
    override suspend fun getCast(params: GetCastParams): RequestState<CastResponse> {
        return try {
            val url =
                "${params.type.name.lowercase()}/${params.id}/credits"
            val response = httpClient.get(
                url = url,
            )
            httpClient.handleRequestState<CastResponse>(response)
        } catch (e: Exception) {
            RequestState.Error(message = e.message.toString())
        }
    }
    override suspend fun getVideos(params: GetVideosParams): RequestState<VideosResponse> {
        return try {
            val url =
                "${params.type.name.lowercase()}/${params.id}/videos"
            val response = httpClient.get(
                url = url,
            )
            httpClient.handleRequestState<VideosResponse>(response)
        } catch (e: Exception) {
            RequestState.Error(message = e.message.toString())
        }
    }

    override suspend fun getRecommendations(params: GetRecommendationsParams): RequestState<MoviesResponse> {
        return try {
            val url =
                "${params.type.name.lowercase()}/${params.id}/recommendations?page=1&language=${params.language.name}"
            val response = httpClient.get(
                url = url,
            )
            httpClient.handleRequestState<MoviesResponse>(response)
        } catch (e: Exception) {
            RequestState.Error(message = e.message.toString())
        }
    }
}