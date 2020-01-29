package com.nakhmadov.topworldmusic.adapters.home_list

import com.nakhmadov.topworldmusic.data.db.models.*

sealed class HomeListChildItems {
    data class AlbumItem(val album: Album) : HomeListChildItems()
    data class ArtistItem(val artist: Artist) : HomeListChildItems()
    data class GenreItem(val genre: Genre) : HomeListChildItems()
    data class PlaylistItem(val playlist: Playlist) : HomeListChildItems()
    data class TrackItem(val track: Track) : HomeListChildItems()
    object MoreItem : HomeListChildItems()
}