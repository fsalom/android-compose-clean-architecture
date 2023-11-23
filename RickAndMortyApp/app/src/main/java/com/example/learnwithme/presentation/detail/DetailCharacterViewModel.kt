package com.example.learnwithme.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnwithme.domain.entity.Character
import com.example.learnwithme.domain.usecase.character.CharacterUseCaseInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface DetailCharactersViewModelInterface {
    val uiState: StateFlow<CharacterUiState>
    fun load()
}

data class CharacterUiState(
    val isLoading: Boolean = false,
    val character: Character? = null
)

class DetailCharactersViewModel(private val id: Int,
                                private val useCase: CharacterUseCaseInterface
):
    DetailCharactersViewModelInterface,
    ViewModel() {

    private val _uiState = MutableStateFlow(CharacterUiState(isLoading = true))
    override val uiState: StateFlow<CharacterUiState> = _uiState.asStateFlow()

    override fun load() {
        viewModelScope.launch {
            val character = useCase.getCharacterWith(id)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    character = character
                )
            }
        }
    }
}