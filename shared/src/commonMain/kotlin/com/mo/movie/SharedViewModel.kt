package com.mo.movie

import com.mo.movie.core.base.BaseViewModel
import com.mo.movie.core.navigation.Screen
import com.mo.movie.features.onBoarding.domain.useCases.GetOnBoardingFinishedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SharedViewModel : BaseViewModel(), KoinComponent {

//    private val getGuestLogInUseCase: GetGuestLogInUseCase by inject()
    private val getOnBoardingFinishedUseCase: GetOnBoardingFinishedUseCase by inject()
    private var _startDestination = MutableStateFlow<Screen?>(null)
    val startDestination: StateFlow<Screen?> = _startDestination
    private var _isGuest = MutableStateFlow(false)
    val isGuest: StateFlow<Boolean> = _isGuest


//    init {
//        runBlocking {
//            _isGuest.value = getGuestLogInUseCase()
//        }
//    }

//    fun getIsGuest(): Boolean {
//        return isGuest.value
//    }

    suspend fun getStartDestination(isLoggedIn: Boolean = false) {
        val isOnBoardingFinished = getOnBoardingFinishedUseCase()
        if (isOnBoardingFinished.not()) {
            _startDestination.value = Screen.OnBoarding
        } else if (isLoggedIn.not() && isGuest.value.not()) {
            _startDestination.value = Screen.Auth
        } else {
            _startDestination.value = Screen.Home
        }
    }

}