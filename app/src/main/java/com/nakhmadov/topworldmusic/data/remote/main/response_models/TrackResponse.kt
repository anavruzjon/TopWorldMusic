package com.nakhmadov.topworldmusic.data.remote.main.response_models

import com.nakhmadov.topworldmusic.data.db.models.Track

data class TrackResponse(
    val id: Long = 0,
    val readable: Boolean? = true,
    val title: String? = "",
    val title_short: String? = "",
    val link: String? = "",
    val duration: Int? = 0,
    val track_position: Int? = 0,
    val release_date: String? = "",
    val preview: String? = "",
    val artist: ArtistResponse? = ArtistResponse(),
    val album: AlbumResponse? = AlbumResponse(),
    val type: String? = ""
)

fun TrackResponse.asDatabaseModel(): Track {

    return Track(
        trackId = id,
        title = title,
        shortTitle = title_short,
        linkToDeezer = link,
        durationSeconds = duration,
        previewMp3 = preview,
        albumId = album?.id,
        artistId = artist?.id,
        artistName = artist?.name,
        releaseDate = release_date,
        albumImage = album?.cover_big
    )
}