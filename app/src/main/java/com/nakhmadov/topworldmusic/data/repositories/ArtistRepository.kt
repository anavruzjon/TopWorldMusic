package com.nakhmadov.topworldmusic.data.repositories

import android.app.Application
import android.util.Log
import com.nakhmadov.topworldmusic.data.db.MusicDatabase
import com.nakhmadov.topworldmusic.data.db.models.Artist
import com.nakhmadov.topworldmusic.data.remote.main.ApiInterface
import com.nakhmadov.topworldmusic.data.remote.main.response_models.artists
import com.nakhmadov.topworldmusic.data.remote.main.response_models.chartList
import com.nakhmadov.topworldmusic.data.remote.main.response_models.editorialList
import com.nakhmadov.topworldmusic.util.isConnectedInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArtistRepository private constructor(
    private val database: MusicDatabase,
    private val network: ApiInterface,
    private val application: Application
) {

    suspend fun chartArtists(limit: Int): List<Artist> {
        val result = mutableListOf<Artist>()

        withContext(Dispatchers.IO) {

            try {

                if (isConnectedInternet(application)) {
                    database.artistDao.deleteChartArtists()
                    val chartArtistsApi = network.fetchChartArtistsAsync(limit = limit).await()

                    val chartList = chartArtistsApi.chartList()
                    chartList?.let { database.artistDao.insertListOfChartItems(it) }

                    val artists = chartArtistsApi.artists()
                    artists?.let { database.artistDao.insertListOfArtists(it) }

                    Log.d("myLogs", "CHART ARTISTS: $chartArtistsApi")
                }

                result.clear()
                val chartArtists = database.artistDao.chartArtists()
                result.addAll(chartArtists)

            } catch (e: Exception) {
                Log.d("myLogs", "ERROR{CHART ARTISTS}: ${e.localizedMessage}")
                e.printStackTrace()
            }

        }

        return result
    }

    suspend fun editorialArtists(limit: Int): List<Artist> {
        val result = mutableListOf<Artist>()

        withContext(Dispatchers.IO) {

            try {

                if (isConnectedInternet(application)) {
                    database.artistDao.deleteEditorialArtists()
                    val editorialArtistsApi = network.fetchEditorialListAsync(limit = limit).await().artists

                    val editorialList = editorialArtistsApi?.editorialList()
                    editorialList?.let { database.artistDao.insertListOfEditorialItems(it) }

                    val artists = editorialArtistsApi?.artists()
                    artists?.let { database.artistDao.insertListOfArtists(it) }

                    Log.d("myLogs", "EDITORIAL ARTISTS: $editorialArtistsApi")
                }

                result.clear()
                val editorialArtists = database.artistDao.editorialArtists()
                result.addAll(editorialArtists)

            } catch (e: Exception) {
                Log.d("myLogs", "ERROR{CHART ARTISTS}: ${e.localizedMessage}")
                e.printStackTrace()
            }

        }

        return result
    }


    companion object {
        private var INSTANCE: ArtistRepository? = null
        fun getRepository(
            database: MusicDatabase,
            network: ApiInterface,
            application: Application
        ): ArtistRepository {
            if (INSTANCE == null) {
                INSTANCE = ArtistRepository(database, network, application)
            }
            return INSTANCE as ArtistRepository
        }
    }

}