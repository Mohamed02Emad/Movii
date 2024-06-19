package com.mo.movie.features.movies.presentation

import com.mo.movie.core.base.BaseState
import com.mo.movie.core.base.BaseViewModel
import com.mo.movie.features.details.domain.enums.MovieShowType
import com.mo.movie.features.home.domain.models.Movie
import com.mo.movie.features.more.settings.domain.models.Languages
import com.mo.movie.features.movies.domain.enums.SortType
import com.mo.movie.features.movies.domain.usecases.GetMoviesParams
import com.mo.movie.features.movies.domain.usecases.GetMoviesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class MoviesViewModel : BaseViewModel() {
    private val getMoviesUseCase: GetMoviesUseCase by inject()

    private var _moviesState: MutableStateFlow<BaseState<List<Movie>>> =
        MutableStateFlow(BaseState.Initial)
    val moviesState: StateFlow<BaseState<List<Movie>>> = _moviesState
    private var _currentSort: MutableStateFlow<SortType> = MutableStateFlow(SortType.POPULAR)
    val currentSort: StateFlow<SortType> = _currentSort
    private val _movies: ArrayList<Movie> = ArrayList(emptyList())
    val movies = _movies
    var job: Job? = null
    var currentPage = 1
    var totalPages: Int? = null
    fun getMovies(
        sort: SortType = currentSort.value,
        language: Languages = Languages.en,
        type: MovieShowType = MovieShowType.MOVIE,
    ) {
        val isRequestOnGoing = _moviesState.value == BaseState.Loading && sort == currentSort.value
        val isLastPage = totalPages != null && currentPage > totalPages!!
        if (isLastPage || isRequestOnGoing) return
        _currentSort.value = sort
        cancelRunningJob()
        job = viewModelScope.launch {
            if (totalPages == null)
                _moviesState.value = BaseState.Loading
            else
                _moviesState.value = BaseState.PagingLoading
            val response = getMoviesUseCase(
                GetMoviesParams(
                    page = currentPage,
                    sort = sort,
                    language = language,
                    type = type,
                )
            )
            if (this.isActive.not()) return@launch
            handleResponse(
                responseState = response,
                onSuccess = {
                    _movies.addAll(it.results)
                    _moviesState.value = BaseState.Success(_movies)
                    currentPage++
                    totalPages = it.totalPages
                },
                onError = {
                    _moviesState.value = BaseState.Error(it)
                }
            )
        }
    }

    private fun cancelRunningJob() {
        job?.let {
            if (it.isActive) {
                it.cancel()
            }
        }
    }

    fun clear() {
        currentPage = 1
        totalPages = null
        _movies.clear()
        _moviesState.value = BaseState.Initial
        cancelRunningJob()
    }
}