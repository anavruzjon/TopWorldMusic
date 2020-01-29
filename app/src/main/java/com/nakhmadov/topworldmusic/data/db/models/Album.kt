package com.nakhmadov.topworldmusic.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
data class Album(
    @PrimaryKey
    val albumId: Long = 0,
    val title: String? = "",
    val linkToDeezer: String? = "",
    val coverUrl: String? = "",
    val coverSmall: String? = "",
    val coverMedium: String? = "",
    val coverBig: String? = "",
    val labelName: String? = "",
    val numberOfTracks: Int? = 0,
    val trackListUrl: String? = "",
    val releaseDate: String? = "",
    val artistName: String? = "",
    val artistId: Long? = 0,
    val durationInSeconds: Int? = 0
) : DatabaseModel