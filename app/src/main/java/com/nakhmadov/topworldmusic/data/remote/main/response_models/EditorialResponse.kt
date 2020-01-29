package com.nakhmadov.topworldmusic.data.remote.main.response_models

data class EditorialResponse(
    val tracks: DataResponseTrack? = DataResponseTrack(),
    val albums: DataResponseAlbum? = DataResponseAlbum(),
    val artists: DataResponseArtist? = DataResponseArtist(),
    val playlists: DataResponsePlaylist? = DataResponsePlaylist()
)