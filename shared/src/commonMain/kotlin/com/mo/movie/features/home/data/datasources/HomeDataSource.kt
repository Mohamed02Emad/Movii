package com.mo.movie.features.home.data.datasources

import com.mo.movie.core.base.RequestState
import com.mo.movie.core.remote.KtorClient
import com.mo.movie.core.remote.TRENDING_MOVIES_API
import com.mo.movie.features.home.domain.models.responses.MoviesResponse
import com.mo.movie.features.home.domain.usecases.GetTrendingParams
import io.ktor.util.InternalAPI
import org.koin.core.component.KoinComponent

interface HomeDataSource {
    suspend fun getTrendingMovies(params: GetTrendingParams): RequestState<MoviesResponse>
}

@InternalAPI
class HomeDataSourceImpl(
    private val httpClient: KtorClient,
) : HomeDataSource, KoinComponent {
    override suspend fun getTrendingMovies(params: GetTrendingParams): RequestState<MoviesResponse> {
        return try {
            val url =
                "${TRENDING_MOVIES_API}${params.filter.name.lowercase()}?language=${params.language.name}&page=${params.page}"
            val response = httpClient.get(
                url = url,
            )
            httpClient.handleRequestState<MoviesResponse>(response)
        } catch (e: Exception) {
            RequestState.Error(message = e.message.toString())
        }
    }
}