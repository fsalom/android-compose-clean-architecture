package com.example.learnwithme.data.repository.character

import com.example.learnwithme.data.datasource.character.database.CharacterDatabaseDataSourceInterface
import com.example.learnwithme.data.datasource.character.remote.CharacterRemoteDataSourceInterface
import com.example.learnwithme.domain.entity.Pagination
import com.example.learnwithme.domain.entity.Character

class CharacterRepository(
    private val remoteDataSource: CharacterRemoteDataSourceInterface,
    private val databaseDatasource: CharacterDatabaseDataSourceInterface
): CharacterRepositoryInterface {

    private val millisecondsToUpdate: Long = 3000
    override suspend fun getPagination(page: Int): Pagination {
        val localPagination = databaseDatasource.getPagination(page)
        val localCharactersIsEmpty = localPagination.characters.isEmpty()
        val firstCharacter = if(localCharactersIsEmpty) null else localPagination.characters.first()
        val shouldBeUpdated = if(firstCharacter == null) false else shouldBeUpdated(firstCharacter.creationDate)
        return if (localCharactersIsEmpty || shouldBeUpdated) {
            val remotePagination = remoteDataSource.getPagination(page)
            remotePagination.characters = setFavorites(remotePagination.characters)
            databaseDatasource.save(remotePagination.characters, page = page)
            remotePagination
        } else {
            localPagination
        }
    }

    override suspend fun getPaginationFor(text: String, page: Int): Pagination {
        return remoteDataSource.getPaginationFor(text, page)
    }

    override suspend fun getCharacterWith(id: Int): Character? {
        return databaseDatasource.getCharacterWith(id) ?: remoteDataSource.getCharacterWith(id)
    }

    override suspend fun favOrUnFav(character: Character) {
        databaseDatasource.favOrUnFav(character)
    }

    override suspend fun getFavoriteCharacters(): List<Character> {
        return databaseDatasource.getFavorites()
    }

    private fun shouldBeUpdated(creationTime: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        if (currentTime - creationTime > millisecondsToUpdate) {
            return true
        }
        return false
    }

    private suspend fun setFavorites(characters: List<Character>): List<Character> {
        val favoriteIds = getFavoriteCharacters().map { it.id }
        val favoriteCharacters: List<Character> = characters
        favoriteCharacters.forEach {
            if (it.id in favoriteIds) {
                it.isFavorite = true
            }
        }
        return favoriteCharacters
    }
}