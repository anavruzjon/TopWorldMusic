package com.nakhmadov.topworldmusic.di

import com.nakhmadov.topworldmusic.data.db.getDatabase
import com.nakhmadov.topworldmusic.data.remote.auth.AuthApiService
import com.nakhmadov.topworldmusic.data.remote.main.ApiService
import com.nakhmadov.topworldmusic.data.repositories.*
import com.nakhmadov.topworldmusic.presentation.presenters.*
import com.nakhmadov.topworldmusic.util.HomeListType
import com.nakhmadov.topworldmusic.util.isConnectedInternet
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

    single { androidApplication() }
    single { getDatabase(androidApplication()) }
    single { AuthApiService.retrofitApi }
    single { ApiService.retrofitApi }
    single { AuthRepository.getRepository(get(), get()) }
    single { AlbumRepository.getRepository(get(), get(), androidApplication()) }
    single { ArtistRepository.getRepository(get(), get(), androidApplication()) }
    single { TrackRepository.getRepository(get(), get(), androidApplication()) }
    single { PlaylistRepository.getRepository(get(), get(), androidApplication()) }
    single { GenreRepository.getRepository(get(), get(), androidApplication()) }
    factory { AuthPresenter(get()) }
    factory { HomePresenter(get(), get(), get(), get(), get()) }
    factory { ArtistDetailPresenter(get()) }
    factory { (listType: HomeListType) ->
        HomeListDetailPresenter(
            get(),
            get(),
            get(),
            get(),
            get(),
            listType
        )
    }
    factory { (albumId: Long) -> AlbumDetailPresenter(get(), albumId) }
    factory { (playlistId: Long) -> PlaylistDetailPresenter(get(), playlistId) }
    factory { (genreId: Long) -> GenreDetailPresenter(get(), genreId) }

}