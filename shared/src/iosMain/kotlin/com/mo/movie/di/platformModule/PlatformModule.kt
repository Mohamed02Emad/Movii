package com.mo.movie.di.platformModule

import com.mo.movie.core.local.database.Database
import com.mo.movie.core.local.database.DatabaseDriverFactory
import com.mo.movie.core.local.prefrences.AppPreferences
import com.mo.movie.core.local.prefrences.dataStoreFileName
import com.mo.movie.core.local.prefrences.getDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val platformModule = module {
    single {
        getDataStore {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            requireNotNull(documentDirectory).path + "/$dataStoreFileName"
        }
    }

    single { AppPreferences(get()) }

    single {
        Database(
            databaseDriverFactory = get()
        )
    }
    single {
        DatabaseDriverFactory()
    }
}