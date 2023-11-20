package com.example.learnwithme.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.learnwithme.data.datasource.character.database.room.dbo.CharacterEntity
import com.example.learnwithme.data.datasource.character.database.room.query.CharacterDao

@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            "app")
            .build()
    }
}