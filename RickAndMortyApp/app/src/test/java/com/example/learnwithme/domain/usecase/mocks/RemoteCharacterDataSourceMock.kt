package com.example.learnwithme.domain.usecase.mocks

import com.example.learnwithme.data.datasource.character.remote.CharacterRemoteDataSourceInterface
import com.example.learnwithme.domain.entity.Character
import com.example.learnwithme.domain.entity.Pagination

class RemoteCharacterDataSourceMock: CharacterRemoteDataSourceInterface {
    override suspend fun getPagination(page: Int): Pagination {
        return Pagination(
            hasNextPage = true,
            characters = emptyList()
        )
    }

    override suspend fun getPaginationFor(text: String, page: Int): Pagination {
        return Pagination(
            hasNextPage = true,
            characters = emptyList()
        )
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
}