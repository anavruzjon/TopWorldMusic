package com.nakhmadov.topworldmusic.data.remote.main.response_models

import com.nakhmadov.topworldmusic.data.db.models.*
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class AlbumResponse(
    val id: Long = 0,
    val title: String? = "",
    val link: String? = "",
    val cover: String? = "",
    val cover_small: String? = "",
    val cover_medium: String? = "",
    val cover_big: String? = "",
    val cover_xl: String? = "",
    val genre_id: Int? = 0,
    val genres: DataResponseGenre? = DataResponseGenre(),
    val label: String? = "",
    val nb_tracks: Int? = 0,
    val duration: Int? = 0,
    val release_date: String? = "",
    val tracklist: String? = "",
    val artist: ArtistResponse? = ArtistResponse(),
    val type: String? = "",
    val tracks: DataResponseTrack? = DataResponseTrack()
)

fun AlbumResponse.asDatabaseModel(): Album {
    return Album(
        albumId = id,
        title = title,
        linkToDeezer = link,
        coverUrl = cover,
        coverSmall = cover_small,
        coverMedium = cover_medium,
        coverBig = cover_big,
        labelName = label,
        numberOfTracks = nb_tracks,
        trackListUrl = tracklist,
        releaseDate = release_date,
        artistId = artist?.id,
        artistName = artist?.name,
        durationInSeconds = duration
    )
}

fun AlbumResponse.genreList(): List<Genre>? {
    return genres?.data?.map { it.asDatabaseModel() }
}

fun AlbumResponse.albumGenreItems(): List<GenreItem> {
    val genreItemList = mutableListOf<GenreItem>()
    this.genres?.data?.forEach {
        val genreItem = GenreItem(itemId = this.id, genreId = it.id, itemType = "album")
        genreItemList.add(genreItem)
    }
    return genreItemList
}

fun AlbumResponse.artist(): Artist? {
    return artist?.asDatabaseModel()
}

fun AlbumResponse.tracklist(): List<Track>? {
    return tracks?.data?.map {
        val track = it.asDatabaseModel()
        track.albumId = this.id
        track.albumImage = this.cover
        track
    }
}
