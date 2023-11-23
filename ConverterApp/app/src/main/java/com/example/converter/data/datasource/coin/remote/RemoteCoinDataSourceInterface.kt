package com.example.converter.data.datasource.coin.remote

import com.example.converter.data.datasource.coin.remote.coincap.dto.CoinDTO

interface RemoteCoinDataSourceInterface {
    suspend fun getCoins(): List<CoinDTO>
}