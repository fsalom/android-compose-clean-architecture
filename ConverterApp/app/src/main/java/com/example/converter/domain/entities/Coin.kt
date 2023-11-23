package com.example.converter.domain.entities

import java.io.Serializable

data class Coin(
    val id: String = "",
    val symbol: String = "",
    val name: String = "",
    val priceUsd: Double = 0.0
): Serializable