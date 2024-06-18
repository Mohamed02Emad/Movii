package com.mo.movie.core.remote

import com.mo.movie.core.SharedStates
import com.mo.movie.core.base.BaseResponse
import com.mo.movie.core.base.RequestState
import com.mo.movie.core.local.prefrences.AppPreferences
import com.mo.movie.core.utils.logit
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.FormPart
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.withCharset
import io.ktor.util.InternalAPI
import io.ktor.utils.io.charsets.Charsets
import org.koin.core.component.KoinComponent

const val token: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2MDQ5MjJmZTA3MThkNjViYWRmMTA2MTdjOGFhOGQzNCIsInN1YiI6IjY2NmViNzE4Mzg1NGMwYjc2NzdkYTYwOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.SHutPX2YN31LN_q3wV8DHBKmw_cjHGdFLraXhQp35MQ"
@InternalAPI
class KtorClient(
    val httpClient: HttpClient,
    val globalStates: SharedStates,
    val preferences: AppPreferences,
) : KoinComponent {
    suspend inline fun get(
        url: String,
//                           token: String? = null
    ): HttpResponse {
        return httpClient.get("$BASE_URL$url") {
            headers {
                append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                append(HttpHeaders.Authorization, token)
            }
        }
    }

    suspend inline fun post(
        url: String,
//        token: String? = null,
        body: Any? = null,
    ): HttpResponse {
        return httpClient.post("$BASE_URL$url") {
            headers {
                    append(HttpHeaders.Authorization, token)
            }
            contentType(ContentType.Application.Json)
            body?.let {
                setBody(body)
            }
        }
    }


    suspend inline fun postWithMultipart(
        url: String,
//        token: String? = null,
        body: Map<String, String>,
        multipartList: Map<String , ByteArray>
    ): HttpResponse {
        return httpClient.post("$BASE_URL$url") {
            headers {
//                token?.let { append(HttpHeaders.Authorization, "Bearer $it") }
                append(HttpHeaders.Authorization, token)
            }
            contentType(ContentType.MultiPart.FormData.withCharset(Charsets.UTF_8))
            setBody(
                MultiPartFormDataContent(
                    formData {
                        body.entries.forEach {
                            this.append(FormPart(it.key, it.value))
                        }
                        multipartList.forEach {
                            append(it.key, it.value, Headers.build {
                                append(HttpHeaders.ContentType, "image/png/jpg/jpeg")
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "form-data; name=${it.key}; filename=${it.key}.png"
                                )
                            })
                        }
                    }
                )
            )
        }
    }


    suspend inline fun put(
        url: String,
//        token: String? = null,
        body: Any? = null,
    ): HttpResponse {
        return httpClient.put("$BASE_URL$url") {
            headers {
                append(HttpHeaders.Accept, ContentType.Application.Json.toString())
//                token?.let {
//                    append(HttpHeaders.Authorization, "Bearer $it")
//                }
                append(HttpHeaders.Authorization, token)
            }
            body?.let {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }
    }

    suspend inline fun putMultipart(
        url: String,
//        token: String? = null,
        body: Map<String, String>,
        multipartList: Map<String , ByteArray>
    ): HttpResponse {
        return httpClient.put("$BASE_URL$url") {
            headers {
//                token?.let { append(HttpHeaders.Authorization, "Bearer $it") }
                append(HttpHeaders.Authorization, token)
            }
            contentType(ContentType.MultiPart.FormData.withCharset(Charsets.UTF_8))
            setBody(
                MultiPartFormDataContent(
                    formData {
                        body.entries.forEach {
                            this.append(FormPart(it.key, it.value))
                        }
                        multipartList.forEach {
                            append(it.key, it.value, Headers.build {
                                append(HttpHeaders.ContentType, "image/png/jpg/jpeg")
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "form-data; name=${it.key}; filename=${it.key}.png"
                                )
                            })
                        }
                    }
                )
            )
        }

    }


    suspend inline fun delete(url: String,
//                              token: String? = null
    ): HttpResponse {
        return httpClient.delete("$BASE_URL$url") {
            headers {
                append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                append(HttpHeaders.Authorization, token)
//                token?.let {
//                    append(HttpHeaders.Authorization, "Bearer $it")
//                }
            }
        }
    }

    suspend inline fun <reified Response : BaseResponse> handleRequestState(
        response: HttpResponse,
    ): RequestState<Response> {
        return try {
            when (response.status.value) {
                200 -> RequestState.Success(data = response.body<Response>(), statusCode = 200)
                403,
                401,
                -> {
                    preferences.clear()
                    globalStates.getSharedViewModel().getStartDestination()
                    RequestState.NotAuthorized(
                        message = response.body<Response>().message.toString(),
                        statusCode = response.status.value,
                        errors = response.body<Response>().errors
                    )
                }

                500 -> RequestState.Error(
                    message = "Server error",
                    statusCode = 500,
                )

                else -> RequestState.Error(
                    message = response.body<Response>().message.toString(),
                    statusCode = response.status.value,
                    errors = response.body<Response>().errors
                )
            }
        } catch (e: Exception) {
            logit(e.message)
            RequestState.Error(
                message = "Server error",
                statusCode = 500,
            )
        }
    }

}