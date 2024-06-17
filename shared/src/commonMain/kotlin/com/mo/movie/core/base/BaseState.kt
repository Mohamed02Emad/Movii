package com.mo.movie.core.base


sealed class BaseState<out Data> {
    data class Initial<out Data>(val data: Data) : BaseState<Data>()
    object Loading : BaseState<Nothing>()
    object PagingLoading : BaseState<Nothing>()
    data class Error(val message: String) : BaseState<Nothing>()
    data class Success<out Data>(val data: Data) : BaseState<Data>()
//    data class PagingLoading<out Data>(val data: Data) : BaseState<Data>()
}


