package com.example.converter.domain.repositories.coin

import com.example.converter.domain.entities.Coin

interface CoinRepositoryInterface {
    suspend fun getCoins(): List<Coin>
}