package com.example.learnwithme.domain.usecase.mocks

import com.example.learnwithme.data.datasource.character.database.CharacterDatabaseDataSourceInterface
import com.example.learnwithme.domain.entity.Character
import com.example.learnwithme.domain.entity.Pagination

class LocalCharacterDataSourceMock: CharacterDatabaseDataSourceInterface {
    override suspend fun getFavorites(): List<Character> {
        return emptyList()
    }

    override suspend fun getPagination(page: Int): Pagination {
        return Pagination(
            hasNextPage = true,
            characters = emptyList()
        )
    }

    override suspend fun search(text: String): List<Character> {
        return emptyList()
    }

    override suspend fun getCharacterWith(id: Int): Character? {
        return Character(
            id = 0,
            name = "test",
            status = "Alive",
            species = "",
            image = "",
            isFavorite = false)
    }

    override suspend fun save(characters: List<Character>, page: Int) {
        print("save")
    }

    override suspend fun favOrUnFav(character: Character) {
        print("favOrUnfav")
    }
}