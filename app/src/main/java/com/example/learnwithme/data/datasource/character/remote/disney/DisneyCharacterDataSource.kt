package com.example.learnwithme.data.datasource.character.remote.disney

import com.example.learnwithme.data.datasource.character.remote.CharacterRemoteDataSourceInterface
import com.example.learnwithme.data.datasource.character.remote.disney.api.DisneyApiInterFace

import com.example.learnwithme.data.datasource.character.remote.disney.dto.toDomain
import com.example.learnwithme.domain.entity.Character
import com.example.learnwithme.domain.entity.Pagination
import com.example.learnwithme.data.manager.network.NetworkManager

class RemoteDisneyCharactersDataSource(
    private val disneyApi: DisneyApiInterFace,
    private val network: NetworkManager
): CharacterRemoteDataSourceInterface {

    override suspend fun getPagination(page: Int): Pagination {
        val response = network.load { disneyApi.getCharacters(page) }
        return response.toDomain()
    }

    override suspend fun getPaginationFor(text: String, page: Int): Pagination {
        val response = network.load { disneyApi.getCharactersFor(text, page) }
        return response.toDomain()
    }

    override suspend fun getCharacterWith(id: Int): Character? {
        val response = network.load { disneyApi.getCharacter(id) }
        val data = response.data ?: return null
        return data.toDomain()
    }
}