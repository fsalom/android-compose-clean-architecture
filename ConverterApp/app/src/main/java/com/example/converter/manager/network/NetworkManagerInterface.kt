package com.example.converter.manager.network

import retrofit2.Response

interface  NetworkManagerInterface {
    suspend fun <T> load(call: suspend () -> Response<T>): T
}