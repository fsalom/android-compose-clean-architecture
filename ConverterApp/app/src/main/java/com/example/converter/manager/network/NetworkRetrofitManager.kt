package com.example.converter.manager.network

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class NetworkRetrofitManager(): NetworkManagerInterface {

    override suspend fun <T> load(call: suspend () -> Response<T>): T {
        try {
            val response = call()
            response.body()?.let { body ->
                if (response.isSuccessful) {
                    return body
                } else {
                    throw response.code().toHttpError()
                }
            } ?: throw RetrofitError.EmptyBody

        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val body = e.response()?.errorBody().toString()
                    throw RetrofitError.HttpException(body)
                }
                is SocketTimeoutException -> throw RetrofitError.Timeout("Timeout Error")
                is IOException -> throw RetrofitError.Network("Thread Error")
                else -> throw RetrofitError.Unknown("Unknown Error")
            }
        }
    }

    fun Int.toHttpError() =
        when (this) {
            204 -> HttpCodeError.ServerNoContent
            400 -> HttpCodeError.BadRequest
            401 -> HttpCodeError.Unauthorized
            403 -> HttpCodeError.Forbidden
            404 -> HttpCodeError.NotFound
            406 -> HttpCodeError.NotAcceptable
            408 -> HttpCodeError.Timeout
            409 -> HttpCodeError.ServerConflict
            else -> HttpCodeError.InternalServerError
        }
}

sealed class RetrofitError : Exception() {
    object EmptyBody : RetrofitError()
    class HttpException(val description: String) : RetrofitError()
    class Network(val description: String) : RetrofitError()
    class Timeout(val description: String) : RetrofitError()
    class Unknown(val description: String) : RetrofitError()
}

sealed class HttpCodeError : Exception() {
    object ServerNoContent : HttpCodeError() //204
    object BadRequest : HttpCodeError() //400
    object Unauthorized : HttpCodeError() //401
    object Forbidden : HttpCodeError()  //403
    object NotFound : HttpCodeError() //404
    object NotAcceptable : HttpCodeError() //406
    object Timeout : HttpCodeError() //408
    object ServerConflict : HttpCodeError() //409
    object InternalServerError : HttpCodeError() //500
}
