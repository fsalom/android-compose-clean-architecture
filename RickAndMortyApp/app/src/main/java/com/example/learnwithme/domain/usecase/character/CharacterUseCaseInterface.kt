package com.example.learnwithme.domain.usecase.character

import com.example.learnwithme.domain.entity.Character

interface CharacterUseCaseInterface {
    suspend fun getNextPageAndCharacters(page: Int): Pair<Boolean, List<Character>>
    suspend fun getNextPageAndCharactersWith(text: String, page: Int): Pair<Boolean, List<Character>>
    suspend fun getCharacterWith(id: Int): Character?
    suspend fun favOrUnFav(character: Character)
    suspend fun getFavoriteCharacters(): List<Character>
    suspend fun setFavorites(characters: List<Character> ): List<Character>
}