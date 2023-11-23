package com.example.learnwithme.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnwithme.domain.usecase.character.CharacterUseCaseInterface
import com.example.learnwithme.domain.entity.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface ListCharactersViewModelInterface {
    fun load()
    fun searchThis(text: String)
    fun filterWith(text: String)
    fun setFavorite(character: Character)
    val uiState: StateFlow<CharactersUiState>
}

data class CharactersUiState(
    var items: List<Character> = mutableListOf(),
    val isLoading: Boolean = false,
    val error: String = ""
)

class ListCharactersViewModel(private val useCase: CharacterUseCaseInterface):
    ListCharactersViewModelInterface, ViewModel() {
    private var page = 1
    private var hasNextPage = true
    private var searchText: String = ""

    private var originalItems: List<Character> = mutableListOf()
    private var originalHasNextPage: Boolean = true
    private var originalPage = 1

    private val _uiState = MutableStateFlow(CharactersUiState(isLoading = true))
    override val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    override fun load() {
        viewModelScope.launch {
            try {
                if (hasNextPage) {
                    val result: Pair<Boolean, List<Character>> = if (searchText.isNotEmpty()) {
                        useCase.getNextPageAndCharactersWith(searchText, page)
                    } else {
                        useCase.getNextPageAndCharacters(page)
                    }
                    hasNextPage = result.first
                    page += if (hasNextPage) 1 else 0
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            items = it.items + result.second,
                            error = ""
                            )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.localizedMessage
                    )
                }
            }
        }
    }

    private fun saveOriginalInformation() {
        originalItems = uiState.value.items
        originalPage = page
        originalHasNextPage = hasNextPage
        page = 1
        hasNextPage = true
    }
    private fun restoreOriginalInformation() {
        _uiState.update {
            it.copy(
                isLoading = false,
                items = originalItems
            )
        }
        page = originalPage
        hasNextPage = originalHasNextPage
        originalItems = emptyList()
        originalPage = 1
    }

    override fun searchThis(text: String) {
        searchText = text
        if(searchText.isNotEmpty() && originalPage == 1 && originalItems.isEmpty()) {
            saveOriginalInformation()
        }
        if(searchText.isEmpty()){
            restoreOriginalInformation()
        }
        page = 1
        hasNextPage = true
        _uiState.update {
            it.copy(
                isLoading = false,
                items = emptyList(),
            )
        }
        load()
    }

    override fun filterWith(text: String) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    items = originalItems.filter { it.name.contains(text, ignoreCase = true) }
                )
            }
        }
    }

    override fun setFavorite(character: Character) {
        viewModelScope.launch {
            useCase.favOrUnFav(character)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    items = useCase.setFavorites(it.items),
                )
            }
        }
    }
}