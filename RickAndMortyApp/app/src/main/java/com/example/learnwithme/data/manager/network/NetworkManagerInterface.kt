package com.example.learnwithme.data.manager.network

import retrofit2.Response

interface  NetworkInterface {
    suspend fun <T> load(call: suspend () -> Response<T>): T
}