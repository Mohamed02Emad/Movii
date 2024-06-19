package com.mo.movie.core.utils

import com.mo.movie.di.baseModule.baseModule
import com.mo.movie.di.detailsModule.detailsModule
import com.mo.movie.di.homeModule.homeModule
import com.mo.movie.di.moviesModule.moviesModule
import com.mo.movie.di.platformModule.platformModule
import com.mo.movie.di.settingsModule.settingsModule
import com.mo.movie.di.setupModules.onBoardingModule
import com.mo.movie.di.viewModelsModule.viewModelsModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(nativeModule: Module? = null, appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        val modulesList : ArrayList<Module> = ArrayList()
        nativeModule?.let {
            modulesList.add(it)
        }
        modulesList.add(baseModule)
        modulesList.add(platformModule)
        modulesList.add(moviesModule)
        modulesList.add(homeModule)
        modulesList.add(detailsModule)
        modulesList.add(settingsModule)
        modulesList.add(viewModelsModule)
        modulesList.add(onBoardingModule)
        modules(modulesList)
    }
}