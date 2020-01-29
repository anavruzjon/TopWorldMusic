package com.nakhmadov.topworldmusic.listeners

import com.nakhmadov.topworldmusic.data.db.models.Artist

class ArtistClickListener(val listener: (artistId: Long) -> Unit) {
    fun onClick(artist: Artist) = listener(artist.artistId)
}