package com.nakhmadov.topworldmusic.data.repositories

import android.app.Application
import android.util.Log
import com.nakhmadov.topworldmusic.data.db.MusicDatabase
import com.nakhmadov.topworldmusic.data.db.models.Playlist
import com.nakhmadov.topworldmusic.data.db.models.PlaylistItem
import com.nakhmadov.topworldmusic.data.db.models.Track
import com.nakhmadov.topworldmusic.data.remote.main.ApiInterface
import com.nakhmadov.topworldmusic.data.remote.main.response_models.*
import com.nakhmadov.topworldmusic.util.isConnectedInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlaylistRepository private constructor(
    private val database: MusicDatabase,
    private val network: ApiInterface,
    private val application: Application
) {

    suspend fun editorialPlaylists(limit: Int): List<Playlist> {
        val result = mutableListOf<Playlist>()

        withContext(Dispatchers.IO) {

            try {

                if (isConnectedInternet(application)) {
                    database.playlistDao.deleteEditorialPlaylists()
                    val editorialPlaylistsApi =
                        network.fetchEditorialListAsync(limit = limit).await().playlists

                    val editorialList = editorialPlaylistsApi?.editorialList()
                    editorialList?.let { database.playlistDao.insertListOfEditorialItems(it) }

                    val playlists = editorialPlaylistsApi?.playlists()
                    playlists?.let { database.playlistDao.insertListOfPlaylists(it) }

                    Log.d("myLogs", "EDITORIAL PLAYLISTS: $editorialPlaylistsApi")
                }

                result.clear()
                val editorialPlaylists = database.playlistDao.editorialPlaylists()
                result.addAll(editorialPlaylists)


            } catch (e: Exception) {
                Log.d("myLogs", "ERROR{CHART PLAYLISTS}: ${e.localizedMessage}")
                e.printStackTrace()
            }

        }

        return result
    }

    suspend fun getPlaylist(playlistId: Long): Playlist? {

        var playlist: Playlist? = null
        withContext(Dispatchers.IO) {
            try {

                if (isConnectedInternet(application)) {
                    val playlistApi = network.fetchPlaylistByIdAsync(playlistId).await()
                    playlist = playlistApi.asDatabaseModel()
                    savePlaylistTracks(playlistApi.tracksList())
                    savePlaylistItems(playlistApi.playlistItemTracks())
                    playlist?.let { database.playlistDao.insertPlaylist(it)}
                    Log.d("myLogs", "PLAYLIST: $playlist")
                }

                playlist = database.playlistDao.getPlaylistById(playlistId)

            } catch (e: Exception) {
                Log.d("myLogs", "ERROR{PLAYLIST}: ${e.localizedMessage}")
                e.printStackTrace()
            }

        }
        return playlist

    }

    suspend fun getPlaylistTracks(playlistId: Long): List<Track> {
        val tracks = mutableListOf<Track>()

        withContext(Dispatchers.IO) {
            try {

                tracks.clear()
                tracks.addAll(database.trackDao.playlistTracks(playlistId))
            } catch (e: Exception) {
                Log.d("myLogs", "ERROR{PLAYLIST TRACKS}: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
        return tracks
    }

    private fun savePlaylistTracks(list: List<Track>?) {
        Log.d("myLogs", "SAVEPLAYLISTTRACKS: $list")
        list?.let { database.trackDao.insertListOfTracks(it) }
    }

    private fun savePlaylistItems(list: List<PlaylistItem>) {
        Log.d("myLogs", "SAVEPlaylistItem")
        database.playlistDao.insertPlaylistItems(list)
    }


    companion object {
        private var INSTANCE: PlaylistRepository? = null
        fun getRepository(
            database: MusicDatabase,
            network: ApiInterface,
            application: Application
        ): PlaylistRepository {
            if (INSTANCE == null) {
                INSTANCE = PlaylistRepository(database, network, application)
            }
            return INSTANCE as PlaylistRepository
        }
    }

}