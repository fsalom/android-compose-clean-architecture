package com.example.converter.presentation.converter

import kotlinx.coroutines.flow.StateFlow

interface CoinViewModelInterface {
    val uiState: StateFlow<CoinUiState>
    fun load()
}