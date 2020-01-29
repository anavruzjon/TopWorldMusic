package com.nakhmadov.topworldmusic.presentation.presenters

import android.util.Log
import com.nakhmadov.topworldmusic.data.db.models.Track
import com.nakhmadov.topworldmusic.data.repositories.PlaylistRepository
import com.nakhmadov.topworldmusic.presentation.views.PlaylistDetailView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class PlaylistDetailPresenter(
    private val playlistRepository: PlaylistRepository,
    private val playlistId: Long
) :
    MvpPresenter<PlaylistDetailView>() {

    private val presenterJob = Job()
    private val presenterScope = CoroutineScope(Dispatchers.Main + presenterJob)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Log.d("myLogs", "PLAYLIST ID: $playlistId")

        presenterScope.launch {
            val playlist = playlistRepository.getPlaylist(playlistId)
            Log.d("myLogs", "GETTED PLAYLIST $playlist")
            playlist?.let { viewState.submitPlaylist(it) }
        }

    }

    fun getPlaylistTracks() {
        val playlistTracks = mutableListOf<Track>()
        presenterScope.launch {
            playlistTracks.clear()
            playlistTracks.addAll(playlistRepository.getPlaylistTracks(playlistId))
            viewState.submitPlaylistTracks(playlistTracks)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenterJob.cancel()
    }

}