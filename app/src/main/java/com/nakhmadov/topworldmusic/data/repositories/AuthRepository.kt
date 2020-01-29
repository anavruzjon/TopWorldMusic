package com.nakhmadov.topworldmusic.data.repositories

import android.util.Log
import com.nakhmadov.topworldmusic.data.db.MusicDatabase
import com.nakhmadov.topworldmusic.data.db.models.AuthToken
import com.nakhmadov.topworldmusic.data.remote.auth.AuthApiInterface
import com.nakhmadov.topworldmusic.data.remote.auth.AuthApiService
import com.nakhmadov.topworldmusic.data.remote.auth.response_models.asDatabaseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthRepository private constructor(
    private val database: MusicDatabase,
    private val network: AuthApiInterface
) {

    suspend fun getToken(): String? {
        var tokenString: String? = null
        withContext(Dispatchers.IO) {
            tokenString = database.authTokenDao.token()?.token
        }
        return tokenString
    }

    suspend fun getTokenNetwork(code: String) {
        try {

            withContext(Dispatchers.IO) {
                val response = network.fetchAccessTokenAsync(code).await()
                val token = AuthToken(token = response.split("=")[1].split("&")[0])
                Log.d("myLogs", "RESPONSE $response TOKEN $token")
                database.authTokenDao.insertToken(token)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("myLogs", "ERROR: ${e.localizedMessage}")
        }
    }


    companion object {
        private var INSTANCE: AuthRepository? = null
        fun getRepository(database: MusicDatabase, network: AuthApiInterface): AuthRepository {
            if (INSTANCE == null) {
                INSTANCE = AuthRepository(database, network)
            }
            return INSTANCE as AuthRepository
        }
    }

}