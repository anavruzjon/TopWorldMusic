package com.nakhmadov.topworldmusic.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artist")
data class Artist(
    @PrimaryKey
    val artistId: Long = 0,
    val name: String? = "",
    val linkToDeezer: String? = "",
    val pictureUrl: String? = "",
    val pictureSmallUrl: String? = "",
    val pictureMediumUrl: String? = "",
    val pictureBigUrl: String? = "",
    val numberOfAlbums: Int? = 0,
    val numberOfFans: Long? = 0,
    val trackListUrl: String? = ""
) : DatabaseModel