package com.nakhmadov.topworldmusic.presentation.presenters

import android.util.Log
import com.nakhmadov.topworldmusic.data.repositories.GenreRepository
import com.nakhmadov.topworldmusic.presentation.views.GenreDetailView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class GenreDetailPresenter(
    private val genreRepository: GenreRepository,
    private val genreId: Long
) : MvpPresenter<GenreDetailView>() {

    private val presenterJob = Job()
    private val presenterScope = CoroutineScope(Dispatchers.Main + presenterJob)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Log.d("myLogs", "GENRE ID: $genreId")

        presenterScope.launch {
            val albums = genreRepository.genreAlbums(genreId)
            Log.d("myLogs", "GETTED GENRE $albums")
            viewState.submitAlbums(albums)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        presenterJob.cancel()
    }

}