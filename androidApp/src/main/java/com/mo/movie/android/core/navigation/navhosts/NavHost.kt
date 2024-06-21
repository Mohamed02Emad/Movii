package com.mo.movie.android.core.navigation.navhosts

import BottomNavigationBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mo.movie.SharedViewModel
import com.mo.movie.android.CURRENT_LANGUAGE
import com.mo.movie.android.core.navigation.fadeTransitionComposable
import com.mo.movie.android.core.navigation.pushReplace
import com.mo.movie.android.core.navigation.swipeTransitionComposable
import com.mo.movie.android.features.auth.presentation.pages.AuthScreen
import com.mo.movie.android.features.details.DetailsScreen
import com.mo.movie.android.features.home.presentation.HomeScreen
import com.mo.movie.android.features.more.presentation.MoreScreen
import com.mo.movie.android.features.movies.presentation.MoviesScreen
import com.mo.movie.android.features.setup.onBoarding.screens.OnBoardingScreen
import com.mo.movie.core.navigation.Screen
import com.mo.movie.features.auth.presentation.AuthViewModel
import com.mo.movie.features.details.presentaion.DetailsViewModel
import com.mo.movie.features.home.presentation.HomeViewModel
import com.mo.movie.features.more.settings.presentation.SettingsViewModel
import com.mo.movie.features.movies.presentation.MoviesViewModel
import com.mo.movie.features.onBoarding.presentation.OnBoardingViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun NavHost(
    startDestination: Screen?,
    settingsViewModel: SettingsViewModel,
    authViewModel: AuthViewModel,
    onSignInClicked: () -> Unit,
    onLogoutClicked: suspend () -> Unit,
    sharedViewModel: SharedViewModel,
    navController: NavHostController,
) {
    val scope = rememberCoroutineScope()
    val homeViewModel: HomeViewModel = getViewModel()
    val moviesViewModel: MoviesViewModel = getViewModel()
    Box {
        NavHost(
            modifier = Modifier
                .fillMaxSize(),
            navController = navController,
            startDestination = startDestination?.route ?: Screen.OnBoarding.route
        ) {

            fadeTransitionComposable(
                route = Screen.OnBoarding.route
            ) {
                val viewModel: OnBoardingViewModel = getViewModel()
                OnBoardingScreen(
                    viewModel = viewModel,
                    settingsViewModel = settingsViewModel,
                    navController = navController
                )
            }

            swipeTransitionComposable(
                route = Screen.Auth.route
            ) {
                AuthScreen(
                    viewModel = authViewModel,
                    onSignInClick = onSignInClicked,
                    navController = navController,
                    settingsViewModel = settingsViewModel,
                )
            }


            fadeTransitionComposable(
                route = Screen.Home.route
            ) {
                HomeScreen(
                    viewModel = homeViewModel,
                    navController = navController,
                )
            }
            fadeTransitionComposable(
                route = Screen.Movies.route
            ) {
                MoviesScreen(
                    viewModel = moviesViewModel ,
                    navController =navController
                )
            }
            fadeTransitionComposable(
                route = Screen.More.route
            ) {
                MoreScreen(
                    settingsViewModel = settingsViewModel,
                    navController = navController,
                    logoutClicked = {
                        scope.launch {
                            onLogoutClicked()
                            navController.pushReplace(Screen.Auth)
                        }
                    },
                    languageChanged = {
                        moviesViewModel.clear()
                        moviesViewModel.getMovies(language = CURRENT_LANGUAGE)
                        homeViewModel.clear()
                        homeViewModel.getTrendingMovies(language = CURRENT_LANGUAGE)
                    }
                )
            }

            swipeTransitionComposable(
                route = Screen.Detail.route,
            ) {
                val viewModel: DetailsViewModel = getViewModel()
                val id = it.arguments?.getString("id") ?: "-1"
                DetailsScreen(
                    viewModel = viewModel,
                    navController = navController,
                    id = id.toInt()
                )
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomNavigationBar(navController = navController , sharedViewModel = sharedViewModel)
        }
    }
}
