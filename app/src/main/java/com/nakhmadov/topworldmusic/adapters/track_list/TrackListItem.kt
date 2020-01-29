package com.nakhmadov.topworldmusic.adapters.track_list

import com.nakhmadov.topworldmusic.data.db.models.Track

sealed class TrackListItem {
    data class AlbumTrack(val track: Track) : TrackListItem()
    data class PlaylistTrack(val track: Track) : TrackListItem()
}