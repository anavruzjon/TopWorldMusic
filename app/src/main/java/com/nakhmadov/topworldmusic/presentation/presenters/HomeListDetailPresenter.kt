package com.nakhmadov.topworldmusic.presentation.presenters

import com.nakhmadov.topworldmusic.data.db.models.DatabaseModel
import com.nakhmadov.topworldmusic.data.repositories.*
import com.nakhmadov.topworldmusic.presentation.views.HomeListDetailView
import com.nakhmadov.topworldmusic.util.HomeListType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class HomeListDetailPresenter(
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository,
    private val genreRepository: GenreRepository,
    private val playlistRepository: PlaylistRepository,
    private val trackRepository: TrackRepository,
    private val listType: HomeListType
) : MvpPresenter<HomeListDetailView>() {

    private val presenterJob = Job()
    private val presenterScope = CoroutineScope(Dispatchers.Main + presenterJob)
    private val items = mutableListOf<DatabaseModel>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        presenterScope.launch {
            items.clear()
            items.addAll(
                when (listType) {
                    HomeListType.EDITORIAL_RELEASE -> { albumRepository.editorialReleases(100) }
                    HomeListType.EDITORIAL_PLAYLIST -> {playlistRepository.editorialPlaylists(100)}
                    HomeListType.CHART_ALBUM -> { albumRepository.chartAlbums(100) }
                    HomeListType.CHART_TRACK -> { trackRepository.chartTracks(100) }
                    HomeListType.EDITORIAL_ARTIST -> { artistRepository.editorialArtists(100) }
                    HomeListType.GENRE -> { genreRepository.genresList() }
                })
            viewState.submitItems(items)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        presenterJob.cancel()
    }

}