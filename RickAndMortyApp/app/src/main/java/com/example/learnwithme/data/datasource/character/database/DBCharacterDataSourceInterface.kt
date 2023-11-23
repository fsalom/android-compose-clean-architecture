package com.example.learnwithme.data.datasource.character.database

import com.example.learnwithme.domain.entity.Character
import com.example.learnwithme.domain.entity.Pagination

interface CharacterDatabaseDataSourceInterface {
    suspend fun getFavorites(): List<Character>
    suspend fun getPagination(page: Int): Pagination
    suspend fun search(text: String): List<Character>
    suspend fun getCharacterWith(id: Int): Character?
    suspend fun save(characters: List<Character>, page: Int)
    suspend fun favOrUnFav(character: Character)
}
