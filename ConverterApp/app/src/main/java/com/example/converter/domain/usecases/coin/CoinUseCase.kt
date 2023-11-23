package com.example.converter.domain.usecases.coin

import com.example.converter.data.repositories.coin.CoinRepositoryInterface
import com.example.converter.domain.entities.Coin

class CoinUseCase(private val repository: CoinRepositoryInterface): CoinUseCaseInterface {
    override suspend fun getCoins(): List<Coin> {
        return repository.getCoins()
    }

}