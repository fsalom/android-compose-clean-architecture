package com.example.converter.data.repositories.coin

import com.example.converter.data.datasource.coin.remote.RemoteCoinDataSourceInterface
import com.example.converter.data.datasource.coin.remote.coincap.dto.CoinDTO
import com.example.converter.domain.entities.Coin
import com.example.converter.domain.repositories.coin.CoinRepositoryInterface

class CoinRepository(private val remoteDataSource: RemoteCoinDataSourceInterface):
    CoinRepositoryInterface {
    override suspend fun getCoins(): List<Coin> {
        return remoteDataSource.getCoins().map { it.toDomain() }
    }
}

private fun CoinDTO.toDomain(): Coin {
    return Coin(id =id ?: "",
        symbol = symbol ?: "",
        name = name ?: "",
        priceUsd = priceUsd)
}
