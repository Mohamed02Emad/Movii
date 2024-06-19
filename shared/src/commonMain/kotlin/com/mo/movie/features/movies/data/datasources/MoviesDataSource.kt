package com.mo.movie.features.movies.data.datasources

import com.mo.movie.core.base.RequestState
import com.mo.movie.core.remote.BASE_URL
import com.mo.movie.core.remote.KtorClient
import com.mo.movie.core.remote.TRENDING_MOVIES_API
import com.mo.movie.features.home.data.datasources.HomeDataSource
import com.mo.movie.features.home.domain.models.responses.MoviesResponse
import com.mo.movie.features.home.domain.usecases.GetTrendingParams
import com.mo.movie.features.movies.domain.usecases.GetMoviesParams
import io.ktor.util.InternalAPI
import org.koin.core.component.KoinComponent

interface MoviesDataSource {
    suspend fun getMovies(params: GetMoviesParams): RequestState<MoviesResponse>
}

@InternalAPI
class MoviesDataSourceImpl(
    private val httpClient: KtorClient,
) : MoviesDataSource, KoinComponent {
    override suspend fun getMovies(params: GetMoviesParams): RequestState<MoviesResponse> {
        return try {
            val url =
                "${params.type.name.lowercase()}/${params.sort.apiName}?language=${params.language.name}&page=${params.page}"
            val response = httpClient.get(
                url = url,
            )
            httpClient.handleRequestState<MoviesResponse>(response)
        } catch (e: Exception) {
            RequestState.Error(message = e.message.toString())
        }
    }
}