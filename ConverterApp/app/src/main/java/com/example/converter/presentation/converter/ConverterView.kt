package com.example.converter.presentation.converter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.converter.domain.entities.Coin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterView(viewModel: CoinViewModelInterface) {
    val uiState by viewModel.uiState.collectAsState()
    val pattern = remember { Regex("^\\d+\$") }
    val fromValue by remember { mutableStateOf(TextFieldValue("")) }

    if (uiState.isLoading) {
        viewModel.load()
        CircularProgressIndicator(
            color = Color.Black,
            strokeWidth = 2.dp,
            modifier = Modifier.fillMaxSize()
        )
    }

    if (uiState.coins.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row {

                    Dropdown(coins = uiState.coins, selected = {
                        viewModel.setFrom(it)
                    } )

                    TextField(keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        value = fromValue,
                        onValueChange = {
                            if (it.text.isEmpty() || it.text.matches(pattern)) {
                                viewModel.convert(it.text.toDouble())
                            }
                        },
                        label = { Text("Coin") }
                    )


            }
        }
    }
}

@Composable
fun Dropdown(coins: List<Coin>, selected: (Coin) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    Box() {
        Text(coins[selectedIndex].name,modifier = Modifier
            .clickable(onClick = { expanded = true })
            .background(
                Color.Gray
            ))
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.Red
                )
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