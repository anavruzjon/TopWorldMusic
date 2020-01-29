package com.nakhmadov.topworldmusic.data.db.models

import androidx.room.Entity

@Entity(tableName = "playlist_item", primaryKeys = ["playlistId", "trackId"])
data class PlaylistItem (
    val playlistId: Long = 0,
    val trackId: Long = 0
)