package com.example.learnwithme.data.datasource.character.remote

import com.example.learnwithme.domain.entity.Pagination
import com.example.learnwithme.domain.entity.Character

interface CharacterRemoteDataSourceInterface {
    suspend fun getPagination(page: Int): Pagination
    suspend fun getPaginationFor(text: String, page: Int): Pagination
    suspend fun getCharacterWith(id: Int): Character?
}
