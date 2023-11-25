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
    val coins: List<Coin> = emptyList(),
    val coinFrom: Coin = Coin(),
    val quantityFrom: Double = 0.0,
    val coinTo: Coin = Coin(),
    val quantityTo: Double = 0.0,
    val error: String = ""
)

class ConverterViewModel(
    private val useCase: CoinUseCaseInterface
):  CoinViewModelInterface,
    ViewModel() {

    private val _uiState = MutableStateFlow(CoinUiState(isLoading = true))
    override val uiState: StateFlow<CoinUiState> = _uiState.asStateFlow()


    override fun load() {
        viewModelScope.launch {
            try {
                val coins = useCase.getCoins()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        coinFrom = coins.first(),
                        coinTo = coins.first(),
                        coins = coins,
                        error = ""
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = "Se ha producido un error"
                    )
                }
            }
        }
    }

    override fun setFrom(coin: Coin) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    coins = it.coins,
                    coinFrom = coin,
                    quantityFrom =  useCase.convert(coin, it.coinTo, it.quantityTo),
                    quantityTo = it.quantityTo
                )
            }
        }
    }

    override fun setTo(coin: Coin) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    coins = it.coins,
                    quantityFrom =  it.quantityFrom,
                    coinTo = coin,
                    quantityTo = useCase.convert(coin, it.coinFrom, it.quantityFrom)
                )
            }
        }
    }

    override fun quantityFromChangedTo(quantity: Double) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    coins = it.coins,
                    quantityFrom = quantity,
                    quantityTo = useCase.convert(it.coinFrom, it.coinTo, quantity),
                )
            }
        }
    }

    override fun quantityToChangedTo(quantity: Double) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    coins = it.coins,
                    quantityFrom = useCase.convert(it.coinTo, it.coinFrom, quantity),
                    quantityTo = quantity
                )
            }
        }
    }

    override fun swap() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    coins = it.coins,
                    coinFrom = it.coinTo,
                    quantityFrom = it.quantityFrom,
                    coinTo = it.coinFrom,
                    quantityTo = useCase.convert(it.coinFrom, it.coinTo, it.quantityFrom)
                )
            }
        }
    }
}
