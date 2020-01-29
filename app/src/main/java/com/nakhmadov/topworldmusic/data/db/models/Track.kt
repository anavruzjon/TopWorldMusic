package com.nakhmadov.topworldmusic.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track")
data class Track(
    @PrimaryKey
    val trackId: Long = 0,
    val title: String? = "",
    val shortTitle: String? = "",
    val linkToDeezer: String? = "",
    val durationSeconds: Int? = 0,
    val previewMp3: String? = "",
    var albumId: Long? = 0,
    val artistId: Long? = 0,
    val releaseDate: String? = "",
    var albumImage: String? = "",
    val artistName: String? = ""
) : DatabaseModel