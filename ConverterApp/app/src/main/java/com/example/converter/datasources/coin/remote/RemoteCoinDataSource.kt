package com.example.converter.datasources.coin.remote

import com.example.converter.datasources.coin.remote.api.CoinCapApiInterface
import com.example.converter.datasources.coin.remote.dto.CoinDTO
import com.example.converter.manager.network.NetworkManagerInterface
import retrofit2.Retrofit

class RemoteCoinDataSource(
    private val network: NetworkManagerInterface,
    retrofit: Retrofit,
): RemoteCoinDataSourceInterface {

    private val coinsApi: CoinCapApiInterface = retrofit.create(CoinCapApiInterface::class.java)
    override suspend fun getCoins(): List<CoinDTO> {
        val response = network.load { coinsApi.getResponseCoins() }
        return response.results
    }


}