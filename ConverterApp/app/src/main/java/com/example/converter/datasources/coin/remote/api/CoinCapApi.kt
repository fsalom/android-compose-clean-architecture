package com.example.converter.datasources.coin.remote.api

import com.example.converter.datasources.coin.remote.dto.CoinWrapperDTO
import retrofit2.Response
import retrofit2.http.GET

interface CoinCapApiInterface {
    @GET("assets")
    suspend fun getResponseCoins(): Response<CoinWrapperDTO>
}