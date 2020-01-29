package com.nakhmadov.topworldmusic.data.remote.main.response_models

import com.nakhmadov.topworldmusic.data.db.models.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataResponseTrack(
    val data: List<TrackResponse>? = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class DataResponseAlbum(
    val data: List<AlbumResponse>? = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class DataResponseArtist(
    val data: List<ArtistResponse>? = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class DataResponseGenre(
    val data: List<GenreResponse>? = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class DataResponsePlaylist(
    val data: List<PlaylistResponse>? = mutableListOf()
)

fun DataResponseTrack.tracklist(): List<Track>? {
    return this.data?.map { it.asDatabaseModel() }
}

fun DataResponseTrack.chartList(): List<ChartItem>? {
    return this.data?.map { ChartItem(itemId = it.id, itemType = "track") }
}

fun DataResponseTrack.editorialList(): List<EditorialItem>? {
    return this.data?.map { EditorialItem(itemId = it.id, itemType = "track") }
}

fun DataResponseAlbum.albums(): List<Album>? {
    return this.data?.map { it.asDatabaseModel() }
}

fun DataResponseAlbum.chartList(): List<ChartItem>? {
    return this.data?.map { ChartItem(itemId = it.id, itemType = "album") }
}

fun DataResponseAlbum.editorialList(): List<EditorialItem>? {
    return this.data?.map { EditorialItem(itemId = it.id, itemType = "album") }
}

fun DataResponseAlbum.editorialReleaseList(): List<EditorialItem>? {
    return this.data?.map { EditorialItem(itemId = it.id, itemType = "album_release") }
}

fun DataResponseArtist.artists(): List<Artist>? {
    return this.data?.map { it.asDatabaseModel() }
}

fun DataResponseArtist.chartList(): List<ChartItem>? {
    return this.data?.map { ChartItem(itemId = it.id, itemType = "artist") }
}

fun DataResponseArtist.editorialList(): List<EditorialItem>? {
    return this.data?.map { EditorialItem(itemId = it.id, itemType = "artist") }
}

fun DataResponsePlaylist.playlists(): List<Playlist>? {
    return this.data?.map { it.asDatabaseModel() }
}

fun DataResponsePlaylist.chartList(): List<ChartItem>? {
    return this.data?.map { ChartItem(itemId = it.id, itemType = "playlist") }
}

fun DataResponsePlaylist.editorialList(): List<EditorialItem>? {
    return this.data?.map { EditorialItem(itemId = it.id, itemType = "playlist") }
}

fun DataResponseGenre.genres(): List<Genre>? {
    return this.data?.map { it.asDatabaseModel() }
}