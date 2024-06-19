package com.mo.movie.android.core.navigation.navhosts

import BottomNavigationBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mo.movie.SharedViewModel
import com.mo.movie.android.core.navigation.fadeTransitionComposable
import com.mo.movie.android.core.navigation.pushReplace
import com.mo.movie.android.core.navigation.swipeTransitionComposable
import com.mo.movie.android.features.auth.presentation.pages.AuthScreen
import com.mo.movie.android.features.details.DetailsScreen
import com.mo.movie.android.features.home.presentation.HomeScreen
import com.mo.movie.android.features.more.presentation.MoreScreen
import com.mo.movie.android.features.movies.presentation.MoviesScreen
import com.mo.movie.android.features.search.presentation.SearchScreen
import com.mo.movie.android.features.setup.onBoarding.screens.OnBoardingScreen
import com.mo.movie.android.features.tvShows.presentation.TvShowsScreen
import com.mo.movie.core.navigation.Screen
import com.mo.movie.features.auth.presentation.AuthViewModel
import com.mo.movie.features.details.presentaion.DetailsViewModel
import com.mo.movie.features.home.presentation.HomeViewModel
import com.mo.movie.features.more.settings.presentation.SettingsViewModel
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
) {
//    val isNavBarVisibile = sharedViewModel.isNavBarVisible.collectAsState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    Box {
//        val padding = if(isNavBarVisibile.value) 44.dp else 0.dp
        NavHost(
            modifier = Modifier
//                .padding(bottom = padding)
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
                val viewModel: HomeViewModel = getViewModel()
                HomeScreen(
                    viewModel = viewModel,
                    navController = navController,
                )
            }
            fadeTransitionComposable(
                route = Screen.Search.route
            ) {
                SearchScreen(
                )
            }
            fadeTransitionComposable(
                route = Screen.Movies.route
            ) {
                MoviesScreen(
                )
            }
            fadeTransitionComposable(
                route = Screen.TvShows.route
            ) {
                TvShowsScreen(
                )
            }
            fadeTransitionComposable(
                route = Screen.More.route
            ) {
                MoreScreen(
                    authViewModel = authViewModel,
                    settingsViewModel = settingsViewModel,
                    logoutClicked = {
                        scope.launch {
                            onLogoutClicked()
                            navController.pushReplace(Screen.Auth)
                        }
                    },
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
