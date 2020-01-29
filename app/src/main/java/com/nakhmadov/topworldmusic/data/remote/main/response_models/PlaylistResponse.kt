package com.nakhmadov.topworldmusic.data.remote.main.response_models

import android.accounts.AuthenticatorDescription
import com.nakhmadov.topworldmusic.data.db.models.Playlist
import com.nakhmadov.topworldmusic.data.db.models.PlaylistItem
import com.nakhmadov.topworldmusic.data.db.models.Track
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class PlaylistResponse(
    val id: Long = 0,
    val title: String? = "",
    val description: String? = "",
    val duration: Int? = 0,
    val nb_tracks: Int? = 0,
    val link: String? = "",
    val picture: String? = "",
    val picture_small: String? = "",
    val picture_medium: String? = "",
    val picture_big: String? = "",
    val picture_xl: String? = "",
    val tracklist: String? = "",
    val creation_date: String? = "",
    val creator: UserResponse = UserResponse(),
    val type: String? = "",
    val tracks: DataResponseTrack? = DataResponseTrack()
)

fun PlaylistResponse.asDatabaseModel(): Playlist {
    return Playlist(
        playlistId = id,
        title = title,
        description = description,
        durationInSeconds = duration,
        numberOfTracks = nb_tracks,
        lintToDeezer = link,
        pictureUrl = picture,
        pictureSmallUrl = picture_small,
        pictureMediumUrl = picture_medium,
        pictureBigUrl = picture_big,
        tracklist = tracklist,
        userName = creator.name,
        userId = creator.id
    )
}


fun PlaylistResponse.tracksList(): List<Track>? {
    return tracks?.data?.map { it.asDatabaseModel() }
}

fun PlaylistResponse.playlistItemTracks(): List<PlaylistItem> {
    val result = mutableListOf<PlaylistItem>()
    tracks?.data?.forEach {
        result.add(PlaylistItem(playlistId = this.id, trackId = it.id))
    }
    return result
}