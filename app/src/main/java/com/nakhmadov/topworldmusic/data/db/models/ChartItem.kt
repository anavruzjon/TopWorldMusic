package com.nakhmadov.topworldmusic.data.db.models

import androidx.room.Entity

@Entity(tableName = "chart_item", primaryKeys = ["itemId", "itemType"])
data class ChartItem(
    val itemId: Long = 0,
    val itemType: String = "" // Track, Album, Artist, Playlist
)