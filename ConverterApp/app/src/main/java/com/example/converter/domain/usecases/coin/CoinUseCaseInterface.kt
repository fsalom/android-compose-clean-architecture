package com.example.converter.domain.usecases.coin

import com.example.converter.domain.entities.Coin

interface CoinUseCaseInterface {
    suspend fun getCoins(): List<Coin>
}