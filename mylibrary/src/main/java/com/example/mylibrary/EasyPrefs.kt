package com.example.mylibrary


import android.content.Context
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

val Context.dataStore by preferencesDataStore(name = "EasyPrefs")

// Extension functions for Context

class EasyPrefs(val context: Context) {

    fun <T> save(pair: Pair<String, T>) = runBlocking {
        context.dataStore.edit { prefs ->
            when (val value = pair.second) {
                is String -> prefs[stringPreferencesKey(pair.first)] = value
                is Int -> prefs[intPreferencesKey(pair.first)] = value
                is Boolean -> prefs[booleanPreferencesKey(pair.first)] = value
                is Float -> prefs[floatPreferencesKey(pair.first)] = value
                is Long -> prefs[longPreferencesKey(pair.first)] = value
                else -> throw IllegalArgumentException("Type not supported")
            }
        }
    }


    fun <T> get(key: String, defaultValue: T): T = runBlocking {
        val prefs = context.dataStore.data.first()
        return@runBlocking when (defaultValue) {
            is String -> prefs[stringPreferencesKey(key)] ?: defaultValue
            is Int -> prefs[intPreferencesKey(key)] ?: defaultValue
            is Boolean -> prefs[booleanPreferencesKey(key)] ?: defaultValue
            is Float -> prefs[floatPreferencesKey(key)] ?: defaultValue
            is Long -> prefs[longPreferencesKey(key)] ?: defaultValue
            else -> throw IllegalArgumentException("Type not supported")
        } as T
    }

    fun delete(key: String) = runBlocking {
        context.dataStore.edit { it.remove(stringPreferencesKey(key)) }
    }


    inline fun <reified T> saveObject(key: String, obj: T) = runBlocking{
        context.dataStore.edit { prefs ->
            prefs[stringPreferencesKey(key)] = Gson().toJson(obj)
        }
    }

    inline fun <reified T> getObject(key: String): T? = runBlocking {
        val json = context.dataStore.data.first()[stringPreferencesKey(key)]?: return@runBlocking null
        return@runBlocking Gson().fromJson(json,T::class.java)
    }


}


