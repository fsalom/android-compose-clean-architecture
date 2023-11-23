package com.example.learnwithme.data.datasource.character.remote.mock

import com.example.learnwithme.data.datasource.character.remote.CharacterRemoteDataSourceInterface
import com.example.learnwithme.domain.entity.Character
import com.example.learnwithme.domain.entity.Pagination

class MockCharacterDataSource(
): CharacterRemoteDataSourceInterface {
    val character = Character(
        id = 1,
        name = "Personaje de prueba",
        status = "Estado",
        species = "Especie",
        image = "https://qph.cf2.quoracdn.net/main-qimg-e43af1ea0978af7da031068531f8967b-lq")
    val character2 = Character(
        id = 2,
        name = "Test",
        status = "Estado",
        species = "Especie",
        image = "https://qph.cf2.quoracdn.net/main-qimg-e43af1ea0978af7da031068531f8967b-lq")
    override suspend fun getPagination(page: Int): Pagination {
        val characters = listOf<Character>(
            character, character2
        )

        return Pagination(hasNextPage = false, characters = characters)
    }

    override suspend fun getPaginationFor(text: String, page: Int): Pagination {
        val characters = listOf<Character>(
            character, character2
        )

        return Pagination(hasNextPage = false, characters = characters)
    }

    override suspend fun getCharacterWith(id: Int): Character? {
        return character
    }
}