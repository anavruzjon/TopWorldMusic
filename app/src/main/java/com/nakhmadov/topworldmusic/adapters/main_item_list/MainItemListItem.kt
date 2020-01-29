package com.nakhmadov.topworldmusic.adapters.main_item_list

import com.nakhmadov.topworldmusic.data.db.models.*

sealed class MainItemListItem {
    data class AlbumItem(val album: Album) : MainItemListItem()
    data class ArtistItem(val artist: Artist) : MainItemListItem()
    data class PlaylistItem(val playlist: Playlist) : MainItemListItem()
    data class GenreItem(val genre: Genre) : MainItemListItem()
    data class TrackItem(val track: Track) : MainItemListItem()
    data class AllItems(val title: String) : MainItemListItem()
}