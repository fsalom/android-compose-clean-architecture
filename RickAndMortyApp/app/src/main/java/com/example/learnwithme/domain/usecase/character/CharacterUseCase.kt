package com.example.learnwithme.domain.usecase.character

import com.example.learnwithme.data.repository.character.CharacterRepositoryInterface
import com.example.learnwithme.domain.entity.Character

class CharacterUseCase(val repository: CharacterRepositoryInterface): CharacterUseCaseInterface {
    override suspend fun getNextPageAndCharacters(
        page: Int
    ): Pair<Boolean, List<Character>> {
        var pagination = repository.getPagination(page)
        return Pair(pagination.hasNextPage, setFavorites(pagination.characters))
    }

    override suspend fun getNextPageAndCharactersWith(
        text: String,
        page: Int
    ): Pair<Boolean, List<Character>> {
        var pagination = repository.getPaginationFor(text, page)
        return Pair(pagination.hasNextPage, setFavorites(pagination.characters))
    }

    override suspend fun getCharacterWith(id: Int): Character? {
        return repository.getCharacterWith(id)
    }

    override suspend fun favOrUnFav(character: Character) {
        repository.favOrUnFav(character)
    }

    override suspend fun getFavoriteCharacters(): List<Character> {
        return repository.getFavoriteCharacters()
    }

    override suspend fun setFavorites(characters: List<Character>): List<Character> {
        val favoriteCharacters = this.getFavoriteCharacters()
        val favoritesIds = favoriteCharacters.map { it.id }
        characters.forEach {
            if (it.id in favoritesIds) {
                it.isFavorite = true
            }
        }
        return characters
    }

}