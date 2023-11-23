package com.example.converter.data.datasource.coin.remote.coincap.api

import com.example.converter.data.datasource.coin.remote.coincap.dto.CoinWrapperDTO
import retrofit2.Response
import retrofit2.http.GET

interface CoinCapApiInterface {
    @GET("assets")
    suspend fun getResponseCoins(): Response<CoinWrapperDTO>
}