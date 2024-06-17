package com.mo.movie.core.navigation

sealed class Screen(val route: String) {
    //example of arguments
//    data class Detail(val id: Int) : Screen("detail/{id}") {
//        fun createRoute(id: Int): String = route.replace("{id}", id.toString())
//    }

    /**Setup and onboarding*/
    data object OnBoarding : Screen("onBoarding")
    data object Auth : Screen("auth")

    /**Bottom Navigation*/
    data object Home : Screen("home")
    data object Search : Screen("search")
    data object Movies : Screen("movies")
    data object TvShows : Screen("tvShows")
    data object More : Screen("more")
}