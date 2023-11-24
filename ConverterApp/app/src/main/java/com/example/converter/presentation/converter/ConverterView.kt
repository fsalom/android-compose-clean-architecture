package com.example.converter.presentation.converter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.converter.domain.entities.Coin
import com.example.converter.helper.extensions.toStringWithFormatNumber
import com.example.converter.presentation.components.CustomProgressIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterView(viewModel: CoinViewModelInterface) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.padding(20.dp)) {
        if (uiState.isLoading) {
            viewModel.load()
            CustomProgressIndicator()
        }

        if (uiState.coins.isNotEmpty()) {
            Column {
                Box(modifier = Modifier.padding(10.dp)) {
                    Row {
                        Dropdown(
                            coins = uiState.coins,
                            selected = { viewModel.setFrom(it) }
                        )
                        TextField(
                            value = uiState.quantityFrom.toStringWithFormatNumber(),
                            onValueChange = {
                                if (it.isNotEmpty()) {
                                    viewModel.quantityFromChangedTo(it.toDouble())
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal
                            ),
                            label = { Text("$ " + uiState.coinFrom.priceUsd.toStringWithFormatNumber() + "$") }
                        )
                    }
                }
                Box(modifier = Modifier.padding(10.dp)) {
                    Row {
                        Dropdown(
                            coins = uiState.coins,
                            selected = { viewModel.setTo(it) }
                        )
                        TextField(
                            value = uiState.quantityTo.toStringWithFormatNumber(),
                            onValueChange = {
                                if (it.isNotEmpty()) {
                                    viewModel.quantityToChangedTo(it.toDouble())
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal
                            ),
                            label = { Text("$ " + uiState.coinTo.priceUsd.toStringWithFormatNumber()) }
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun Dropdown(coins: List<Coin>, selected: (Coin) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    Box() {
        Text(coins[selectedIndex].name,
            modifier = Modifier
                .padding(10.dp)
                .width(120.dp)
                .clickable(onClick = {
                    expanded = true
                })
            )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.padding(10.dp)
        ) {
            coins.forEachIndexed { index, coin ->
                DropdownMenuItem(text = { Text(text = coin.name) },
                    onClick = {
                        selectedIndex = index
                        expanded = false
                        selected(coin)
                    })

            }
        }
    }
}