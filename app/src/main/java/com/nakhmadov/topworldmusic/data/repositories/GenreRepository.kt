package com.nakhmadov.topworldmusic.data.repositories

import android.app.Application
import android.util.Log
import com.nakhmadov.topworldmusic.data.db.MusicDatabase
import com.nakhmadov.topworldmusic.data.db.models.*
import com.nakhmadov.topworldmusic.data.remote.main.ApiInterface
import com.nakhmadov.topworldmusic.data.remote.main.response_models.albums
import com.nakhmadov.topworldmusic.data.remote.main.response_models.artists
import com.nakhmadov.topworldmusic.data.remote.main.response_models.editorialList
import com.nakhmadov.topworldmusic.data.remote.main.response_models.genres
import com.nakhmadov.topworldmusic.util.isConnectedInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenreRepository private constructor(
    private val database: MusicDatabase,
    private val network: ApiInterface,
    private val application: Application
) {

    suspend fun genresList(): List<Genre> {
        val result = mutableListOf<Genre>()

        withContext(Dispatchers.IO) {

            try {

                if (isConnectedInternet(application)) {

                    val genreCount = database.genreDao().count()

                    if (genreCount == 0) {
                        val genresResponseApi = network.fetchGenresAsync().await()
                        val genresApi = genresResponseApi.genres()
                        genresApi?.let { database.genreDao().insertListOfGenres(genresApi) }
                        Log.d("myLogs", "GENRES: $genresResponseApi")
                    }


                }

                result.clear()
                val genres = database.genreDao().genres()
                result.addAll(genres)

            } catch (e: Exception) {
                Log.d("myLogs", "ERROR{GENRES}: ${e.localizedMessage}")
                e.printStackTrace()
            }

        }

        return result
    }

    suspend fun getGenre(genreId: Long): Genre? {

        var genre: Genre? = null
        withContext(Dispatchers.IO) {
            genre = database.genreDao().genreById(genreId)
        }
        return genre
    }

    suspend fun genreAlbums(genreId: Long): List<Album> {
        val albums = mutableListOf<Album>()

        withContext(Dispatchers.IO) {
            try {

                if (isConnectedInternet(application)) {
                    val genreAlbumsApi =
                        network.fetchChartAlbumsAsync(genreId = genreId).await().albums()
                    genreAlbumsApi?.let {
                        val genreItems = mutableListOf<GenreItem>()
                        it.forEach { album ->
                            genreItems.add(GenreItem(album.albumId, genreId, "album"))
                        }
                        database.albumDao.insertListOfAlbums(it)
                        database.genreDao().insertListOfGenreItems(genreItems)
                    }

                    Log.d("myLogs", "GENRE ALBUMS: $genreAlbumsApi")
                }

                albums.addAll(database.albumDao.genreAlbums(genreId))

            } catch (e: Exception) {
                Log.d("myLogs", "ERROR{GENRE ALBUMS}: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }

        return albums
    }

    companion object {
        private var INSTANCE: GenreRepository? = null
        fun getRepository(
            database: MusicDatabase,
            network: ApiInterface,
            application: Application
        ): GenreRepository {
            if (INSTANCE == null) {
                INSTANCE = GenreRepository(database, network, application)
            }
            return INSTANCE as GenreRepository
        }
    }

}