package com.example.converter.presentation.converter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterView(viewModel: CoinViewModelInterface) {
    val uiState by viewModel.uiState.collectAsState()
    val pattern = remember { Regex("^\\d+\$") }

    if (uiState.isLoading) {
        viewModel.load()
    }

    Column {
        Row {
            Text("Origen")
            TextField(keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                value = "0.0",
                onValueChange = {
                    if (it.isEmpty() || it.matches(pattern)) {
                        viewModel.convert(1.0)
                    }
                },
                label = { Text("0.0") }
            )
        }

    }
}