package com.example.converter.helper.interceptor

import com.example.converter.helper.logger.LoggerInterface
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class LoggingInterceptor(private val logger: LoggerInterface): Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val t1 = System.nanoTime()
        val response: Response = chain.proceed(request)
        val t2 = System.nanoTime()
        val ms = (t2 - t1) / 1e6

        logger.log(response = response, request= request, duration = ms)
        return response
    }
}