package com.mo.movie.features.home.presentation

import com.mo.movie.core.base.BaseState
import com.mo.movie.core.base.BaseViewModel
import com.mo.movie.features.home.domain.enums.TrendingFilter
import com.mo.movie.features.home.domain.models.Movie
import com.mo.movie.features.home.domain.usecases.GetTrendingParams
import com.mo.movie.features.home.domain.usecases.GetTrendingUseCase
import com.mo.movie.features.more.settings.domain.models.Languages
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class HomeViewModel : BaseViewModel() {
    private val getTrendingUseCase: GetTrendingUseCase by inject()
    private var _moviesState: MutableStateFlow<BaseState<List<Movie>>> = MutableStateFlow(BaseState.Initial(emptyList()))
    val moviesState: StateFlow<BaseState<List<Movie>>> = _moviesState
    private var _currentFilter: MutableStateFlow<TrendingFilter> = MutableStateFlow(TrendingFilter.DAY)
    val currentFilter: StateFlow<TrendingFilter> = _currentFilter
    private val _movies : ArrayList<Movie> = ArrayList(emptyList())
    val movies = _movies
    var job: Job? = null
    var currentPage = 1
    var totalPages: Int? = null
    fun getTrendingMovies(filter: TrendingFilter = currentFilter.value, language: Languages = Languages.en) {
        val isRequestOnGoing = _moviesState.value == BaseState.Loading && filter == currentFilter.value
        val isLastPage = totalPages !=null && currentPage > totalPages!!
        if (isLastPage || isRequestOnGoing) return
        _currentFilter.value = filter
        cancelRunningJob()
        job = viewModelScope.launch {
            if (totalPages == null)
                _moviesState.value = BaseState.Loading
            else
                _moviesState.value = BaseState.PagingLoading
            val response = getTrendingUseCase(
                GetTrendingParams(
                    page = currentPage,
                    filter = filter,
                    language = language
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

    fun clear(){
        currentPage = 1
        totalPages = null
        _movies.clear()
        _moviesState.value = BaseState.Initial(emptyList())
        cancelRunningJob()
    }
}