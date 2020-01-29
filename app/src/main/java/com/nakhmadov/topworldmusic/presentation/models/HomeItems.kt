package com.nakhmadov.topworldmusic.presentation.models

import com.nakhmadov.topworldmusic.data.db.models.*
import com.nakhmadov.topworldmusic.util.HomeListType

sealed class HomeItems {

    abstract val items: List<DatabaseModel>
    lateinit var title: String
    lateinit var listType: HomeListType

    data class GenresItem(val genres: List<Genre>) : HomeItems() {
        override val items: List<DatabaseModel> = genres.map { it }
    }

    data class AlbumsItem(val albums: List<Album>) : HomeItems() {
        override val items: List<DatabaseModel> = albums.map { it }
    }

    data class TracksItem(val tracks: List<Track>) : HomeItems() {
        override val items: List<DatabaseModel> = tracks.map { it }
    }

    data class PlaylistsItem(val playlists: List<Playlist>) : HomeItems() {
        override val items: List<DatabaseModel> = playlists.map { it }
    }

    data class ArtistsItem(val artists: List<Artist>) : HomeItems() {
        override val items: List<DatabaseModel> = artists.map { it }
    }
}