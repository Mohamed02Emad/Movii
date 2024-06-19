package com.mo.movie.features.details.presentaion

import com.mo.movie.core.base.BaseState
import com.mo.movie.core.base.BaseViewModel
import com.mo.movie.features.details.domain.enums.MovieShowType
import com.mo.movie.features.details.domain.models.Video
import com.mo.movie.features.details.domain.models.responses.CastResponse
import com.mo.movie.features.details.domain.models.responses.MovieDetailsResponse
import com.mo.movie.features.details.domain.usecases.GetCastParams
import com.mo.movie.features.details.domain.usecases.GetCastUseCase
import com.mo.movie.features.details.domain.usecases.GetMovieDetailParams
import com.mo.movie.features.details.domain.usecases.GetMovieDetailsUseCase
import com.mo.movie.features.details.domain.usecases.GetRecommendationsParams
import com.mo.movie.features.details.domain.usecases.GetRecommendationsUseCase
import com.mo.movie.features.details.domain.usecases.GetVideosParams
import com.mo.movie.features.details.domain.usecases.GetVideosUseCase
import com.mo.movie.features.home.domain.models.Movie
import com.mo.movie.features.more.settings.domain.models.Languages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class DetailsViewModel : BaseViewModel() {

    /** use cases */
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase by inject()
    private val getCastUseCase: GetCastUseCase by inject()
    private val getVideosUseCase: GetVideosUseCase by inject()
    private val getRecommendationsUseCase: GetRecommendationsUseCase by inject()

    /** request states */
    private var _movieState: MutableStateFlow<BaseState<MovieDetailsResponse>> = MutableStateFlow(BaseState.Initial)
    val movieState: StateFlow<BaseState<MovieDetailsResponse>> = _movieState

    private var _castState: MutableStateFlow<BaseState<CastResponse>> = MutableStateFlow(BaseState.Initial)
    val castState: StateFlow<BaseState<CastResponse>> = _castState

    private var _videosState: MutableStateFlow<BaseState<List<Video>?>> = MutableStateFlow(BaseState.Initial)
    val videosState: StateFlow<BaseState<List<Video>?>> = _videosState

    private var _recommendationsState: MutableStateFlow<BaseState<List<Movie>?>> =
        MutableStateFlow(BaseState.Initial)
    val recommendationsState: StateFlow<BaseState<List<Movie>?>> = _recommendationsState

    /** data states */
    private var _movieDetails: MutableStateFlow<MovieDetailsResponse?> = MutableStateFlow(null)
    val movieDetails : StateFlow<MovieDetailsResponse?> = _movieDetails

    private var _cast: MutableStateFlow<CastResponse?> = MutableStateFlow(null)
    val cast : StateFlow<CastResponse?> = _cast

    private var _videos: MutableStateFlow<List<Video>?> = MutableStateFlow(null)
    val videos : StateFlow<List<Video>?> = _videos

    private var _recommendations: MutableStateFlow<List<Movie>?> = MutableStateFlow(null)
    val recommendations: StateFlow<List<Movie>?> = _recommendations

    /** requests */
    fun getTrendingMovies(id: Int, language: Languages = Languages.en) {
        val isRequestOnGoing = _movieState.value == BaseState.Loading
        if (isRequestOnGoing) return
        viewModelScope.launch {
            _movieState.value = BaseState.Loading
            val response = getMovieDetailsUseCase(
                GetMovieDetailParams(id = id, language = language)
            )
            handleResponse(
                responseState = response,
                onSuccess = {
                    _movieDetails.value = it
                    _movieState.value = BaseState.Success(it)
                },
                onError = {
                    _movieState.value = BaseState.Error(it)
                }
            )
        }
    }
    fun getCast(id: Int, type : MovieShowType = MovieShowType.MOVIE) {
        val isRequestOnGoing = _castState.value == BaseState.Loading
        if (isRequestOnGoing) return
        viewModelScope.launch {
            _castState.value = BaseState.Loading
            val response = getCastUseCase(
                GetCastParams(id = id, type = type)
            )
            handleResponse(
                responseState = response,
                onSuccess = {
                    _cast.value = it
                    _castState.value = BaseState.Success(it)
                },
                onError = {
                    _castState.value = BaseState.Error(it)
                }
            )
        }
    }
    fun getVideos(id: Int, type : MovieShowType = MovieShowType.MOVIE) {
        val isRequestOnGoing = _videosState.value == BaseState.Loading
        if (isRequestOnGoing) return
        viewModelScope.launch {
            _videosState.value = BaseState.Loading
            val response = getVideosUseCase(
                GetVideosParams(id = id, type = type)
            )
            handleResponse(
                responseState = response,
                onSuccess = {
                    val result = it.results.filter{ it.site == "YouTube"}.sortedBy { it.type == "Trailer" }
                    _videos.value = result
                    _videosState.value = BaseState.Success(result)
                },
                onError = {
                    _videosState.value = BaseState.Error(it)
                }
            )
        }
    }

    fun getRecommendations(
        id: Int,
        language: Languages,
        type: MovieShowType = MovieShowType.MOVIE,
    ) {
        val isRequestOnGoing = _recommendationsState.value == BaseState.Loading
        if (isRequestOnGoing) return
        viewModelScope.launch {
            _recommendationsState.value = BaseState.Loading
            val response = getRecommendationsUseCase(
                GetRecommendationsParams(id = id, type = type, language = language)
            )
            handleResponse(
                responseState = response,
                onSuccess = {
                    _recommendations.value = it.results
                    _recommendationsState.value = BaseState.Success(it.results)
                },
                onError = {
                    _recommendationsState.value = BaseState.Error(it)
                }
            )
        }
    }

    /** methods */
    fun clear() {
        _movieDetails.value = null
        _movieState.value = BaseState.Initial
        _cast.value = null
        _castState.value = BaseState.Initial
        _videos.value = null
        _videosState.value = BaseState.Initial
        _recommendations.value = null
        _recommendationsState.value = BaseState.Initial
    }
}