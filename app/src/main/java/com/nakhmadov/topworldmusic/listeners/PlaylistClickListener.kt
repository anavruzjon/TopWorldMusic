package com.nakhmadov.topworldmusic.listeners

import com.nakhmadov.topworldmusic.data.db.models.Playlist

class PlaylistClickListener(val listener: (playlistId: Long) -> Unit) {
    fun onClick(playlist: Playlist) = listener(playlist.playlistId)
}