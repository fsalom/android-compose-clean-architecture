package com.example.converter.datasources.coin.remote.dto

import com.google.gson.annotations.SerializedName

data class CoinWrapperDTO(
    @SerializedName("data") val results : List<CoinDTO> = listOf()
)

data class CoinDTO(
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("symbol") var symbol: String? = "null",
    @SerializedName("name") var name: String? = "null",
    @SerializedName("priceUsd") var priceUsd: Double = 0.0
)