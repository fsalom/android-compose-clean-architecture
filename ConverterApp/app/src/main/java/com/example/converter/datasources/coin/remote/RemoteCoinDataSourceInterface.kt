package com.example.converter.datasources.coin.remote

import com.example.converter.datasources.coin.remote.dto.CoinDTO

interface RemoteCoinDataSourceInterface {
    suspend fun getCoins(): List<CoinDTO>
}