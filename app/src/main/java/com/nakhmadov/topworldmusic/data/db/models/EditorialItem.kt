package com.nakhmadov.topworldmusic.data.db.models

import androidx.room.Entity

@Entity(tableName = "editorial_item", primaryKeys = ["itemId", "itemType"])
data class EditorialItem(
    val itemId: Long = 0,
    val itemType: String = "" // Track, Album, Artist, Playlist, AlbumRelease
)