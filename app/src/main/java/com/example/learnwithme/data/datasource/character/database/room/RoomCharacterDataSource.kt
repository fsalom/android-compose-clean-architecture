package com.example.learnwithme.data.datasource.character.database.room

import com.example.learnwithme.data.datasource.character.database.CharacterDatabaseDataSourceInterface
import com.example.learnwithme.data.datasource.character.database.room.dbo.CharacterEntity
import com.example.learnwithme.data.datasource.character.database.room.dbo.toDomain
import com.example.learnwithme.data.datasource.character.database.room.query.CharacterDao
import com.example.learnwithme.domain.entity.Character
import com.example.learnwithme.domain.entity.Pagination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomCharacterDataSource(
    val dao: CharacterDao
): CharacterDatabaseDataSourceInterface {

    val dispatcher = Dispatchers.IO
    override suspend fun getFavorites(): List<Character> {
        return withContext(dispatcher) {
            val charactersEntity = dao.getFavorites()
            charactersEntity.map { it.toDomain() }
        }
    }

    override suspend fun getPagination(page: Int): Pagination {
        return withContext(dispatcher) {
            val charactersEntity = dao.getCharacters(page)
            Pagination(
                characters = charactersEntity.map { it.toDomain() },
                hasNextPage = true
            )
        }
    }

    override suspend fun search(text: String): List<Character> {
        return withContext(dispatcher) {
            val charactersEntity = dao.searchCharacters(text)
            charactersEntity.map { it.toDomain() }
        }
    }

    override suspend fun getCharacterWith(id: Int): Character? {
        return withContext(dispatcher) {
            val character = dao.getEntitySync(id)
            if (character != null) {
                character.toDomain()
            }
            null
        }
    }

    override suspend fun save(characters: List<Character>, page: Int) {
        return withContext(dispatcher) {
            for (character in characters) {
                dao.insertOrReplace(CharacterEntity.create(character = character, page = page))
            }
        }
    }

    override suspend fun favOrUnFav(character: Character) {
        return withContext(dispatcher) {
            val entity = dao.getEntitySync(character.id)
            character.isFavorite = !character.isFavorite
            val newCharacterEntity = CharacterEntity.create(character, entity?.page ?: 0)
            dao.insertOrReplace(newCharacterEntity)
        }
    }
}