package com.nakhmadov.topworldmusic.presentation.views

import com.nakhmadov.topworldmusic.data.db.models.Album
import com.nakhmadov.topworldmusic.data.db.models.Track
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface AlbumDetailView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun submitAlbum(album: Album)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun submitAlbumTracks(tracks: List<Track>)
}