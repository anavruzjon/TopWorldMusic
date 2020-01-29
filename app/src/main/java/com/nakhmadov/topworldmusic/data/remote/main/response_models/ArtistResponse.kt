package com.nakhmadov.topworldmusic.data.remote.main.response_models

import com.nakhmadov.topworldmusic.data.db.models.Artist

data class ArtistResponse(
    val id: Long = 0,
    val name: String? = "",
    val link: String? = "",
    val picture: String? = "",
    val picture_small: String? = "",
    val picture_medium: String? = "",
    val picture_big: String? = "",
    val picture_xl: String? = "",
    val nb_album: Int? = 0,
    val nb_fan: Long? = 0,
    val tracklist: String? = "",
    val type: String? = ""
)

fun ArtistResponse.asDatabaseModel(): Artist {
    return Artist(
        artistId = id,
        name = name,
        linkToDeezer = link,
        pictureUrl = picture,
        pictureSmallUrl = picture_small,
        pictureMediumUrl = picture_medium,
        pictureBigUrl = picture_big,
        numberOfAlbums = nb_album,
        numberOfFans = nb_fan,
        trackListUrl = tracklist
    )
}