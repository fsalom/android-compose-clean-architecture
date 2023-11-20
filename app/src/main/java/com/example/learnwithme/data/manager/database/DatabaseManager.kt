package com.example.learnwithme.data.manager.database

import androidx.annotation.NonNull
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ComputableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.InvalidationTracker
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.RawQuery
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Update
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.learnwithme.data.datasource.character.database.room.query.CharacterDao

abstract class BaseDao<T : BaseEntity> (
    private val tableName: String,
    private val roomDatabase: RoomDatabase)  {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(entities: List<T>): LongArray

    @Update
    abstract fun update(entity: T)

    @Update
    abstract fun update(entities: List<T>)

    @Delete
    abstract fun delete(entity: T)

    @Delete
    abstract fun delete(entities: List<T>)

    @RawQuery
    protected abstract fun deleteAll(query: SupportSQLiteQuery): Int

    @RawQuery
    protected abstract fun getAll(query: SupportSQLiteQuery): List<T>

    fun deleteAll() {
        val query = SimpleSQLiteQuery("DELETE FROM $tableName")
        deleteAll(query)
    }

    @RawQuery
    protected abstract fun getEntitySync(query: SupportSQLiteQuery): List<T>?

    fun getEntitySync(id: Int): T? {
        return getEntitySync(listOf(id))?.firstOrNull()
    }

    fun getEntitySync(ids: List<Int>): List<T>? {
        val result = StringBuilder()
        for (index in ids.indices) {
            if (index != 0) {
                result.append(",")
            }
            result.append("'").append(ids[index]).append("'")
        }
        val query = SimpleSQLiteQuery("SELECT * FROM $tableName WHERE id IN ($result);")
        return getEntitySync(query)
    }

    fun getEntity(id: Int): LiveData<T> {
        val resultLiveData = MediatorLiveData<T>()
        resultLiveData.addSource(getEntity(listOf(id))) { obj ->
            resultLiveData.postValue(obj?.firstOrNull())
        }
        return resultLiveData
    }


    fun getAll(): List<T> {
        val query = SimpleSQLiteQuery("SELECT * FROM $tableName")
        return getAll(query)
    }

    fun getEntity(ids: List<Int>): LiveData<List<T>?> {

        return object : ComputableLiveData<List<T>>() {

            private var observer: InvalidationTracker.Observer? = null

            override fun compute(): List<T> {
                if (observer == null) {
                    observer = object : InvalidationTracker.Observer(tableName) {

                        override fun onInvalidated(tables: Set<String>) = invalidate()
                    }
                    roomDatabase.invalidationTracker.addWeakObserver(observer as InvalidationTracker.Observer)
                }
                return getEntitySync(ids) ?: emptyList()
            }
        }.liveData
    }
}

abstract class BaseEntity {
    abstract val id: Int
}
