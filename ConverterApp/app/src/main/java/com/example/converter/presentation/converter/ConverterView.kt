package com.example.converter.presentation.converter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun ConverterView(viewModel: CoinViewModelInterface) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        viewModel.load()
    }
}