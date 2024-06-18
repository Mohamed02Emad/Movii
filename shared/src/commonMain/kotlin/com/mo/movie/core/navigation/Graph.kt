package com.mo.movie.core.navigation

sealed class Screen(internal var _route: String) {
    val route: String
        get() = _route
    /**Setup and onboarding*/
    data object OnBoarding : Screen("onBoarding")
    data object Auth : Screen("auth")

    /**Bottom Navigation*/
    data object Home : Screen("home")
    data object Search : Screen("search")
    data object Movies : Screen("movies")
    data object TvShows : Screen("tvShows")
    data object More : Screen("more")

    /**other*/
    data object Detail : Screen("detail/{id}") {
        fun updateRoute(id: Int) {
            _route = "detail/{id}"
            _route = _route.replace("{id}", id.toString())
        }
    }
}