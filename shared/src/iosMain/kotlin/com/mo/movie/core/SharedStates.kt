package com.mo.movie.core

import com.mo.movie.SharedViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class SharedStates : KoinComponent {
    //todo fix this
    private  val sharedViewModel: SharedViewModel by inject()
    actual fun getSharedViewModel(): SharedViewModel = sharedViewModel

}