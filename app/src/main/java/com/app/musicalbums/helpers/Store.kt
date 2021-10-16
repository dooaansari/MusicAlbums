package com.app.musicalbums.helpers

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.app.musicalbums.constants.Constants
import com.app.musicalbums.constants.StoreConstants

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(Constants.STORE_NAME)

class Store(private val context: Context) {

    fun test(){
        Log.i("tag","this is me")
    }

    suspend fun <T> writeToStore(key: String, value: T){
            context.dataStore.edit { store ->
                store[StoreConstants.KEY_LOGIN_TOKEN] = key
        }
    }
}