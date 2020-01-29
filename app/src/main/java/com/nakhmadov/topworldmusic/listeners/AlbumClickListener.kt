package com.nakhmadov.topworldmusic.listeners

import com.nakhmadov.topworldmusic.data.db.models.Album

class AlbumClickListener(val listener: (albumId: Long) -> Unit) {
    fun onClick(album: Album) = listener(album.albumId)
}