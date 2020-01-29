package com.nakhmadov.topworldmusic.presentation.presenters

import android.util.Log
import com.nakhmadov.topworldmusic.data.db.models.Track
import com.nakhmadov.topworldmusic.data.repositories.AlbumRepository
import com.nakhmadov.topworldmusic.presentation.views.AlbumDetailView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class AlbumDetailPresenter(
    private val albumRepository: AlbumRepository,
    private val albumId: Long
) : MvpPresenter<AlbumDetailView>() {

    private val presenterJob = Job()
    private val presenterScope = CoroutineScope(Dispatchers.Main + presenterJob)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Log.d("myLogs", "ALBUM ID: $albumId")

        presenterScope.launch {
            val album = albumRepository.getAlbum(albumId)
            album?.let { viewState.submitAlbum(album) }
        }

    }

    fun getAlbumTracks() {
        val albumTracks = mutableListOf<Track>()
        presenterScope.launch {
            albumTracks.clear()
            albumTracks.addAll(albumRepository.getAlbumTracks(albumId))
            viewState.submitAlbumTracks(albumTracks)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenterJob.cancel()
    }
}