package com.nakhmadov.topworldmusic.listeners

import com.nakhmadov.topworldmusic.data.db.models.Album
import com.nakhmadov.topworldmusic.data.db.models.Genre

class GenreClickListener(val listener: (genreId: Long) -> Unit) {
    fun onClick(genre: Genre) = listener(genre.genreId)
}