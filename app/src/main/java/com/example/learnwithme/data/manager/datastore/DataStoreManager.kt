package com.example.learnwithme.data.manager.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONObject

val Context.userSettingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

class DataStoreManager(val context: Context) {

    suspend fun <T> save(`object`: T, key: String) {
        val jsonString = GsonBuilder().create().toJson(`object`)
        val wrappedKey = stringPreferencesKey(key)
        context.userSettingsDataStore.edit {
            it[wrappedKey] = jsonString
        }
    }

    suspend inline fun <reified T> retrieve(key: String): T? {
        val wrappedKey = stringPreferencesKey(key)
        var datastore = context.userSettingsDataStore.data.first()

        if (datastore[wrappedKey] != null ) {
            var newobject = GsonBuilder().create().fromJson(datastore[wrappedKey], T::class.java)
            return newobject
        }
        return null
    }
}