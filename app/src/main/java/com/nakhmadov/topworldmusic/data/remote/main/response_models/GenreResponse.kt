package com.nakhmadov.topworldmusic.data.remote.main.response_models

import com.nakhmadov.topworldmusic.data.db.models.Artist
import com.nakhmadov.topworldmusic.data.db.models.Genre
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreResponse(
    val id: Long = 0,
    val name: String? = "",
    val picture: String? = "",
    val picture_small: String? = "",
    val picture_medium: String? = "",
    val picture_big: String? = "",
    val picture_xl: String? = "",
    val type: String? = ""
)

fun GenreResponse.asDatabaseModel(): Genre {
    return Genre(
        genreId = id,
        name = name,
        pictureUrl = picture,
        pictureSmallUrl = picture_small,
        pictureMediumUrl = picture_medium,
        pictureBigUrl = picture_big
    )
}