package com.mo.movie.core

import com.mo.movie.SharedViewModel
import org.koin.core.component.KoinComponent

expect class SharedStates() : KoinComponent {
    fun getSharedViewModel(): SharedViewModel

}