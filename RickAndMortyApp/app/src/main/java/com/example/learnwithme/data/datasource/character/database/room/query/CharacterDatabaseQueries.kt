package com.example.learnwithme.data.datasource.character.database.room.query

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.learnwithme.data.datasource.character.database.room.dbo.CharacterEntity
import com.example.learnwithme.domain.entity.Character
import com.example.learnwithme.data.manager.database.BaseDao

@Dao
abstract class CharacterDao(roomDatabase: RoomDatabase) : BaseDao<CharacterEntity>("characters", roomDatabase) {
    @Query("SELECT * FROM characters WHERE isFavorite = 1")
    abstract fun getFavorites(): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrReplace(character: CharacterEntity)

    @Query("SELECT * FROM characters WHERE page = :page")
    abstract fun getCharacters(page: Int): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE name LIKE '%' || :text || '%'")
    abstract fun searchCharacters(text: String): List<CharacterEntity>

}