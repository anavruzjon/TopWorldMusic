package com.nakhmadov.topworldmusic.data.repositories

import android.app.Application
import android.util.Log
import com.nakhmadov.topworldmusic.data.db.MusicDatabase
import com.nakhmadov.topworldmusic.data.db.models.Album
import com.nakhmadov.topworldmusic.data.db.models.Genre
import com.nakhmadov.topworldmusic.data.db.models.GenreItem
import com.nakhmadov.topworldmusic.data.db.models.Track
import com.nakhmadov.topworldmusic.data.remote.main.ApiInterface
import com.nakhmadov.topworldmusic.data.remote.main.response_models.*
import com.nakhmadov.topworldmusic.util.isConnectedInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumRepository private constructor(
    private val database: MusicDatabase,
    private val network: ApiInterface,
    private val application: Application
) {

    suspend fun chartAlbums(limit: Int): List<Album> {
        val result = mutableListOf<Album>()

        withContext(Dispatchers.IO) {

            try {

                if (isConnectedInternet(application)) {
                    database.albumDao.deleteChartAlbums()
                    val chartAlbumsApi = network.fetchChartAlbumsAsync(limit = limit).await()

                    val chartList = chartAlbumsApi.chartList()
                    chartList?.let { database.albumDao.insertListOfChartItems(it) }

                    val albums = chartAlbumsApi.albums()
                    albums?.let { database.albumDao.insertListOfAlbums(it) }

                    Log.d("myLogs", "CHART ALBUMS: $chartAlbumsApi")
                }

                result.clear()
                val chartAlbums = database.albumDao.chartAlbums()
                result.addAll(chartAlbums)


            } catch (e: Exception) {
                Log.d("myLogs", "ERROR{CHART ALBUMS}: ${e.localizedMessage}")
                e.printStackTrace()
            }

        }


        return result
    }

    suspend fun editorialReleases(limit: Int): List<Album> {
        val result = mutableListOf<Album>()

        withContext(Dispatchers.IO) {

            try {

                if (isConnectedInternet(application)) {
                    database.albumDao.deleteChartAlbums()
                    val editorialReleaseAlbumsApi =
                        network.fetchEditorialReleasesAsync(limit = limit).await()

                    val editorialReleaseList = editorialReleaseAlbumsApi.editorialReleaseList()
                    editorialReleaseList?.let { database.albumDao.insertListOfEditorialItems(it) }

                    val albums = editorialReleaseAlbumsApi.albums()
                    albums?.let { database.albumDao.insertListOfAlbums(it) }

                    Log.d("myLogs", "EDITORIAL RELEASE ALBUMS: $editorialReleaseAlbumsApi")
                }

                result.clear()
                val editorialReleaseList = database.albumDao.editorialReleaseAlbums()
                result.addAll(editorialReleaseList)


            } catch (e: Exception) {
                Log.d("myLogs", "ERROR{CHART RELEASE ALBUMS}: ${e.localizedMessage}")
                e.printStackTrace()
            }

        }


        return result
    }

    suspend fun getAlbumTracks(albumId: Long): List<Track> {
        val tracks = mutableListOf<Track>()

        withContext(Dispatchers.IO) {
            try {

                tracks.clear()
                tracks.addAll(database.trackDao.albumTracks(albumId))
            } catch (e: Exception) {
                Log.d("myLogs", "ERROR{ALBUM TRACKS}: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
        return tracks
    }

    suspend fun getAlbum(albumId: Long): Album? {
        var album: Album? = null
        withContext(Dispatchers.IO) {
            try {

                if (isConnectedInternet(application)) {
                    val albumApi = network.fetchAlbumByIdAsync(albumId).await()
                    album = albumApi.asDatabaseModel()
                    saveAlbumTracklist(albumApi.tracklist())
                    saveAlbumGenres(albumApi.genreList())
                    saveAlbumGenreItems(albumApi.albumGenreItems())
                    album?.let { database.albumDao.insertAlbum(it) }
                    Log.d("myLogs", "ALBUM: $album")
                }

                album = database.albumDao.getAlbumById(albumId)

            } catch (e: Exception) {
                Log.d("myLogs", "ERROR{ALBUM}: ${e.localizedMessage}")
                e.printStackTrace()
            }

        }
        return album
    }

    private fun saveAlbumGenreItems(list: List<GenreItem>) {
        Log.d("myLogs", "AlbumGenreItems: $list")
        database.genreDao().insertListOfGenreItems(list)
    }

    private fun saveAlbumTracklist(tracks: List<Track>?) {
        Log.d("myLogs", "AlbumTracks: $tracks")
        tracks?.let { database.trackDao.insertListOfTracks(it) }
    }

    private fun saveAlbumGenres(genres: List<Genre>?) {
        Log.d("myLogs", "AlbumGenres: $genres")
        genres?.let { database.genreDao().genresToInsert(it) }
    }

    companion object {
        private var INSTANCE: AlbumRepository? = null
        fun getRepository(
            database: MusicDatabase,
            network: ApiInterface,
            application: Application
        ): AlbumRepository {
            if (INSTANCE == null) {
                INSTANCE = AlbumRepository(database, network, application)
            }
            return INSTANCE as AlbumRepository
        }
    }

}