package com.mo.movie.core

import com.mo.movie.SharedViewModel
import org.koin.core.component.KoinComponent

actual class SharedStates : KoinComponent {
    private var sharedViewModel: SharedViewModel? = null
    actual fun getSharedViewModel(): SharedViewModel {
        if (sharedViewModel == null) {
            sharedViewModel = SharedViewModel()
        }
        return sharedViewModel!!
    }

}