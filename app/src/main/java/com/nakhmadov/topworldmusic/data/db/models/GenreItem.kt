package com.nakhmadov.topworldmusic.data.db.models

import androidx.room.Entity

@Entity(tableName = "genre_item", primaryKeys = ["itemId", "genreId"])
data class GenreItem(
    val itemId: Long = 0,
    val genreId: Long = 0,
    val itemType: String? = ""  // "album", "track", "artist"
)