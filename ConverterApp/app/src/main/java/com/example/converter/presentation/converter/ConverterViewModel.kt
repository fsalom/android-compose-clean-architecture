package com.example.converter.presentation.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converter.domain.entities.Coin
import com.example.converter.domain.usecases.coin.CoinUseCaseInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CoinUiState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList()
)

class ConverterViewModel(private val useCase: CoinUseCaseInterface
):  CoinViewModelInterface,
    ViewModel() {

    private lateinit var fromCoin: Coin
    private lateinit var toCoin: Coin

    private val _uiState = MutableStateFlow(CoinUiState(isLoading = true))
    override val uiState: StateFlow<CoinUiState> = _uiState.asStateFlow()

    override fun load() {
        viewModelScope.launch {
            try {
                val coins = useCase.getCoins()
                fromCoin = coins.first()
                toCoin = coins.first()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        coins = coins
                    )
                }
            } catch (e: Exception) {
                println("error: " + e.message)
            }
        }
    }

    override fun setFrom(coin: Coin) {
        fromCoin = coin
    }

    override fun setTo(coin: Coin) {
        toCoin = coin
    }

    override fun convert(quantity: Double) {
        TODO("Not yet implemented")
    }
}
