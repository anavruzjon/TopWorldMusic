package com.nakhmadov.topworldmusic.data.remote.auth.response_models

import com.nakhmadov.topworldmusic.data.db.models.AuthToken
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(val access_token: String)

fun AuthResponse.asDatabaseModel(): AuthToken {
    return AuthToken(token = this.access_token)
}