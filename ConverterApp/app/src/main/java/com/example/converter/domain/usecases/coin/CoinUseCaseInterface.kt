package com.example.converter.domain.usecases.coin

import com.example.converter.domain.entities.Coin

interface CoinUseCaseInterface {
    suspend fun getCoins(): List<Coin>
    suspend fun convert(fromCoin: Coin, toCoin: Coin, quantity: Double): Double
}