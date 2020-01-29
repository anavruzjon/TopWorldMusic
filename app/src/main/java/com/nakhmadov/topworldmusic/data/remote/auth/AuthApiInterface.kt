package com.nakhmadov.topworldmusic.data.remote.auth

import com.nakhmadov.topworldmusic.BuildConfig
import com.nakhmadov.topworldmusic.data.remote.auth.response_models.AuthResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiInterface {

    @POST("oauth/access_token.php")
    fun fetchAccessTokenAsync(
        @Query("code") code: String,
        @Query("app_id") appId: String = BuildConfig.AppId,
        @Query("secret") secretKey: String = BuildConfig.AppSecretKey
    ): Deferred<String>
}