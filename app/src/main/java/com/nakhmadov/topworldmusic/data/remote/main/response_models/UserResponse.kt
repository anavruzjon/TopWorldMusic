package com.nakhmadov.topworldmusic.data.remote.main.response_models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class UserResponse(
    val id: Long = 0,
    val name: String? = "",
    val lastname: String? = "",
    val firstname: String? = "",
    val email: String? = "",
    val picture: String? = "",
    val picture_small: String? = "",
    val picture_medium: String? = "",
    val picture_big: String? = "",
    val picture_xl: String? = "",
    val country: String? = "",
    val lang: String? = ""
)