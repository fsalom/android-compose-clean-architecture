package com.example.converter.data.repositories.coin

import com.example.converter.data.datasource.coin.remote.RemoteCoinDataSourceInterface
import com.example.converter.data.datasource.coin.remote.coincap.dto.CoinDTO
import com.example.converter.domain.entities.Coin

class CoinRepository(private val remoteDataSource: RemoteCoinDataSourceInterface): CoinRepositoryInterface {
    override suspend fun getCoins(): List<Coin> {
        return remoteDataSource.getCoins().map { it.toDomain() }
    }
}

fun CoinDTO.toDomain(): Coin {
    return Coin(id = id ?: 0,
        symbol = symbol ?: "",
        name = name ?: "",
        priceUsd = priceUsd)
}
