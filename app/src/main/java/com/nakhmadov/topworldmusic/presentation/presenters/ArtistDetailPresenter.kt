package com.nakhmadov.topworldmusic.presentation.presenters

import com.nakhmadov.topworldmusic.data.repositories.ArtistRepository
import com.nakhmadov.topworldmusic.presentation.views.ArtistDetailView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ArtistDetailPresenter(private val artistRepository: ArtistRepository) :
    MvpPresenter<ArtistDetailView>() {

}