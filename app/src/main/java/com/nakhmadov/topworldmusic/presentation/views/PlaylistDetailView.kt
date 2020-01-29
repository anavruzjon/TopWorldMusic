package com.nakhmadov.topworldmusic.presentation.views

import com.nakhmadov.topworldmusic.data.db.models.Album
import com.nakhmadov.topworldmusic.data.db.models.Playlist
import com.nakhmadov.topworldmusic.data.db.models.Track
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface PlaylistDetailView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun submitPlaylist(playlist: Playlist?)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun submitPlaylistTracks(tracks: List<Track>)

}