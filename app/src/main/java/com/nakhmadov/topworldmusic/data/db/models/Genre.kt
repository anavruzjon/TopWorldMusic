package com.nakhmadov.topworldmusic.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class Genre(
    @PrimaryKey
    val genreId: Long = 0,
    val name: String? = "",
    val pictureUrl: String? = "",
    val pictureSmallUrl: String? = "",
    val pictureMediumUrl: String? = "",
    val pictureBigUrl: String? = ""
) : DatabaseModel