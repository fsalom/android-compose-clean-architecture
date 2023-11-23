package com.example.converter.data.datasource.coin.remote.coincap.dto

import com.google.gson.annotations.SerializedName

data class CoinWrapperDTO(
    @SerializedName("data") val results : List<CoinDTO> = listOf()
)

data class CoinDTO(
    @SerializedName("id") var id: String? = "-",
    @SerializedName("symbol") var symbol: String? = "null",
    @SerializedName("name") var name: String? = "null",
    @SerializedName("priceUsd") var priceUsd: Double = 0.0
)