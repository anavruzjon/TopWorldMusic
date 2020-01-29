package com.nakhmadov.topworldmusic.data.remote.main

import com.nakhmadov.topworldmusic.data.remote.main.response_models.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("chart/{genreId}/tracks")
    fun fetchChartTracksAsync(@Path("genreId") genreId: Long = 0, @Query("limit") limit: Int = 100): Deferred<DataResponseTrack>

    @GET("chart/{genreId}/albums")
    fun fetchChartAlbumsAsync(@Path("genreId") genreId: Long = 0, @Query("limit") limit: Int = 100): Deferred<DataResponseAlbum>

    @GET("chart/{genreId}/artists")
    fun fetchChartArtistsAsync(@Path("genreId") genreId: Long = 0, @Query("limit") limit: Int = 100): Deferred<DataResponseArtist>

    @GET("editorial/{genreId}/charts")
    fun fetchEditorialListAsync(@Path("genreId") genreId: Long = 0, @Query("limit") limit: Int = 100): Deferred<EditorialResponse>

    @GET("editorial/{genreId}/releases")
    fun fetchEditorialReleasesAsync(@Path("genreId") genreId: Long = 0, @Query("limit") limit: Int = 100): Deferred<DataResponseAlbum>

    @GET("genre")
    fun fetchGenresAsync(): Deferred<DataResponseGenre>

    @GET("album/{albumId}")
    fun fetchAlbumByIdAsync(@Path("albumId") albumId: Long): Deferred<AlbumResponse>

    @GET("playlist/{playlistId}")
    fun fetchPlaylistByIdAsync(@Path("playlistId") playlistId: Long): Deferred<PlaylistResponse>

    @GET("chart/{genreId}/playlists")
    fun fetchChartPlaylistsAsync(@Path("genreId") genreId: Long = 0, @Query("limit") limit: Int = 100): Deferred<DataResponsePlaylist>
}