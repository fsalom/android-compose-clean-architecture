package com.example.learnwithme.data.manager.network

import com.example.learnwithme.configuration.LOGGER_IDENTIFIER
import com.example.learnwithme.data.datasource.character.remote.rickandmorty.api.CharacterApiInterface
import com.example.learnwithme.helper.interceptor.LoggingInterceptor
import com.example.learnwithme.helper.logger.logcat.Logger
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException

class NetworkManager(): NetworkInterface {

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