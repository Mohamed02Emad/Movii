package com.mo.movie.features.onBoarding.domain.repositories

interface OnBoardingRespository{
    suspend fun setIsOnBoardingFinished(isOnBoardingFinished: Boolean)
    suspend fun getIsOnBoardingFinished():Boolean
}