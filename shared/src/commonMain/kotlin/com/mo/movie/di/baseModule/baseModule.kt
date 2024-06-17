package com.mo.movie.di.baseModule

import co.touchlab.kermit.Logger.Companion.d
import com.mo.movie.core.SharedStates
import com.mo.movie.core.remote.KtorClient
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

@OptIn(InternalAPI::class)
val baseModule = module {
    single {
        HttpClient {
            expectSuccess = false
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15000
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        d(
                            tag = "HTTP_CLIENT",
                            messageString = message
                        )
                    }
                }
                level = LogLevel.ALL
            }
        }
    } withOptions {
        createdAtStart()
    }

    single { SharedStates() } withOptions { createdAtStart() }

    single<KtorClient> {KtorClient(httpClient = get() , globalStates = get() , preferences = get()  ) }


}