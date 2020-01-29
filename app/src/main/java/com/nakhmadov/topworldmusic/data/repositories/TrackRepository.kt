package com.nakhmadov.topworldmusic.data.repositories

import android.app.Application
import android.util.Log
import com.nakhmadov.topworldmusic.data.db.MusicDatabase
import com.nakhmadov.topworldmusic.data.db.models.Track
import com.nakhmadov.topworldmusic.data.remote.main.ApiInterface
import com.nakhmadov.topworldmusic.data.remote.main.response_models.chartList
import com.nakhmadov.topworldmusic.data.remote.main.response_models.tracklist
import com.nakhmadov.topworldmusic.presentation.models.HomeItems
import com.nakhmadov.topworldmusic.util.isConnectedInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrackRepository private constructor(
    private val database: MusicDatabase,
    private val network: ApiInterface,
    private val application: Application
) {

    suspend fun chartTracks(limit: Int): List<Track> {
        val result = mutableListOf<Track>()

        withContext(Dispatchers.IO) {

            try {

                if (isConnectedInternet(application)) {
                    database.trackDao.deleteChartTracks()
                    val chartTracksFromApi = network.fetchChartTracksAsync(limit = limit).await()

                    val chartList = chartTracksFromApi.chartList()
                    chartList?.let { database.trackDao.insertListOfChartItems(it) }

                    val tracks = chartTracksFromApi.tracklist()
                    tracks?.let { database.trackDao.insertListOfTracks(it) }

                    Log.d("myLogs", "CHART TRACKS: $chartTracksFromApi")

                }

                result.clear()
                val chartTracks = database.trackDao.chartTracks()
                result.addAll(chartTracks)
            } catch (e: Exception) {
                Log.d("myLogs", "ERROR{CHART TRACKS}: ${e.localizedMessage}")
                e.printStackTrace()
            }

        }


        return result
    }

    companion object {
        private var INSTANCE: TrackRepository? = null
        fun getRepository(
            database: MusicDatabase,
            network: ApiInterface,
            application: Application
        ): TrackRepository {
            if (INSTANCE == null) {
                INSTANCE = TrackRepository(database, network, application)
            }
            return INSTANCE as TrackRepository
        }
    }

}