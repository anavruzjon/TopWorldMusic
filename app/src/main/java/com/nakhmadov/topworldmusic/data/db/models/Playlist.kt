package com.nakhmadov.topworldmusic.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist")
data class Playlist(
    @PrimaryKey
    val playlistId: Long = 0,
    val title: String? = "",
    val description: String? = "",
    val durationInSeconds: Int? = 0,
    val numberOfTracks: Int? = 0,
    val lintToDeezer: String? = "",
    val pictureUrl: String? = "",
    val pictureSmallUrl: String? = "",
    val pictureMediumUrl: String? = "",
    val pictureBigUrl: String? = "",
    val tracklist: String? = "",
    val userId: Long? = 0,
    val userName: String? = ""
) : DatabaseModel