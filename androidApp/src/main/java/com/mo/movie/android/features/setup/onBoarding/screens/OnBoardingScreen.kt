package com.mo.movie.android.features.setup.onBoarding.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mo.movie.android.IS_DARK_MODE
import com.mo.movie.android.R
import com.mo.movie.android.core.composables.Height
import com.mo.movie.android.core.navigation.replaceStartDestination
import com.mo.movie.android.features.setup.onBoarding.composables.LogoLayout
import com.mo.movie.android.features.setup.onBoarding.composables.OnBoardingButtonsLayout
import com.mo.movie.android.features.setup.onBoarding.composables.OnBoardingPage
import com.mo.movie.core.navigation.Screen
import com.mo.movie.features.more.settings.presentation.SettingsViewModel
import com.mo.movie.features.onBoarding.presentation.OnBoardingViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel,
    navController: NavHostController,
    settingsViewModel: SettingsViewModel,
) {

    val onBoardings = listOf<Pair<Int, Int>>(
        R.string.on_boarding1 to R.drawable.onboarding1,
        R.string.on_boarding2 to R.drawable.onboarding2,
        R.string.on_boarding3 to R.drawable.onboarding3,
    )
    //for fcm
//    val scope = rememberCoroutineScope()
//    scope.launch {
//        val token = getDeviceToken()
//        viewModel.setDeviceToken(token)
//    }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(14.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        val pagerState = rememberPagerState { onBoardings.size }
        Column {
            Height(height = 20.dp)
            Box(contentAlignment = Alignment.Center) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LogoLayout()
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(35.dp)
                            .clip(RoundedCornerShape(35.dp))
                            .clickable {
                                settingsViewModel.setDarkMode(IS_DARK_MODE.not())
                            }
                            .padding(2.dp),
                        imageVector = if (IS_DARK_MODE) Icons.Filled.DarkMode else Icons.Outlined.DarkMode,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            HorizontalPager(
                state = pagerState
            ) { index ->
                val onBoard = onBoardings[index]
                OnBoardingPage(
                    modifier = Modifier.fillMaxSize(),
                    image = onBoard.second,
                    text = LocalContext.current.getString(onBoard.first)
                )
            }
        }
        OnBoardingButtonsLayout(pagerState = pagerState, pagesCount = onBoardings.size) {
            viewModel.setOnBoardingFinished()
            navController.replaceStartDestination(Screen.Auth)
        }
    }
    }
}