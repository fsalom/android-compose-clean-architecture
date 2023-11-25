package com.example.converter.presentation.converter

import com.example.converter.domain.entities.Coin
import kotlinx.coroutines.flow.StateFlow

interface CoinViewModelInterface {
    val uiState: StateFlow<CoinUiState>
    fun load()
    fun setFrom(coin: Coin)
    fun setTo(coin: Coin)
    fun quantityFromChangedTo(quantity: Double)
    fun quantityToChangedTo(quantity: Double)
    fun swap()
}