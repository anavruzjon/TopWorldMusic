package com.nakhmadov.topworldmusic.presentation.views

import com.nakhmadov.topworldmusic.data.db.models.Album
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface GenreDetailView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun submitAlbums(albums: List<Album>)

}