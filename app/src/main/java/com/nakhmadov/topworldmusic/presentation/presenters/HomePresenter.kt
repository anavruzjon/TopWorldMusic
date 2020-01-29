package com.nakhmadov.topworldmusic.presentation.presenters

import com.nakhmadov.topworldmusic.data.db.models.*
import com.nakhmadov.topworldmusic.data.repositories.*
import com.nakhmadov.topworldmusic.presentation.models.HomeItems
import com.nakhmadov.topworldmusic.presentation.views.HomeView
import com.nakhmadov.topworldmusic.util.HomeListType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class HomePresenter(
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository,
    private val genreRepository: GenreRepository,
    private val playlistRepository: PlaylistRepository,
    private val trackRepository: TrackRepository
) : MvpPresenter<HomeView>() {

    private val presenterJob = Job()
    private val presenterScope = CoroutineScope(Dispatchers.Main + presenterJob)
    private val homeContent = mutableListOf<HomeItems>()
    private val chartTracks = mutableListOf<Track>()
    private val chartAlbums = mutableListOf<Album>()
    private val editorialPlaylists = mutableListOf<Playlist>()
    private val editorialArtists = mutableListOf<Artist>()
    private val editorialReleases = mutableListOf<Album>()
    private val genres = mutableListOf<Genre>()


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        presenterScope.launch {
            viewState.startLoading()
            chartTracks.clear()
            chartTracks.addAll(trackRepository.chartTracks(limit = 10))
            chartAlbums.clear()
            chartAlbums.addAll(albumRepository.chartAlbums(limit = 10))
            editorialPlaylists.clear()
            editorialPlaylists.addAll(playlistRepository.editorialPlaylists(limit = 10))
            editorialArtists.clear()
            editorialArtists.addAll(artistRepository.editorialArtists(limit = 10))
            editorialReleases.clear()
            editorialReleases.addAll(albumRepository.editorialReleases(limit = 10))
            genres.clear()
            genres.addAll(genreRepository.genresList())

            pickAllItems()
            viewState.submitListToAdapter(homeContent)
            viewState.stopLoading()
        }


    }

    private fun pickAllItems() {
        homeContent.clear()
        homeContent.add(HomeItems.GenresItem(genres).apply {
            title = "Музыка по жанрам"
            listType = HomeListType.GENRE
        })
        homeContent.add(HomeItems.ArtistsItem(editorialArtists).apply {
            title = "Рекомендуемые исполнители"
            listType = HomeListType.EDITORIAL_ARTIST
        })
        homeContent.add(HomeItems.TracksItem(chartTracks).apply {
            title = "Лучшие треки"
            listType = HomeListType.CHART_TRACK
        })
        homeContent.add(HomeItems.AlbumsItem(chartAlbums).apply {
            title = "Топ альбомы"
            listType = HomeListType.CHART_ALBUM
        })
        homeContent.add(HomeItems.PlaylistsItem(editorialPlaylists).apply {
            title = "Рекомендуемые плейлисты"
            listType = HomeListType.EDITORIAL_PLAYLIST
        })
        homeContent.add(HomeItems.AlbumsItem(editorialReleases).apply {
            title = "Рекомендуемые новинки"
            listType = HomeListType.EDITORIAL_RELEASE
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        presenterJob.cancel()
    }
}