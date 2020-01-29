package com.nakhmadov.topworldmusic.adapters.track_list

import androidx.recyclerview.widget.DiffUtil
import com.nakhmadov.topworldmusic.data.db.models.Track

class TrackDiffItemCallback : DiffUtil.ItemCallback<TrackListItem>() {
    override fun areItemsTheSame(oldItem: TrackListItem, newItem: TrackListItem): Boolean {
        if (oldItem is TrackListItem.AlbumTrack && newItem !is TrackListItem.AlbumTrack)
            return false
        if (oldItem is TrackListItem.PlaylistTrack && newItem !is TrackListItem.PlaylistTrack)
            return false
        if (oldItem is TrackListItem.AlbumTrack)
            return oldItem.track.trackId == (newItem as TrackListItem.AlbumTrack).track.trackId
        if (oldItem is TrackListItem.PlaylistTrack)
            return oldItem.track.trackId == (newItem as TrackListItem.PlaylistTrack).track.trackId
        return true

    }

    override fun areContentsTheSame(oldItem: TrackListItem, newItem: TrackListItem): Boolean {
        if (oldItem is TrackListItem.AlbumTrack && newItem !is TrackListItem.AlbumTrack)
            return false
        if (oldItem is TrackListItem.PlaylistTrack && newItem !is TrackListItem.PlaylistTrack)
            return false
        if (oldItem is TrackListItem.AlbumTrack)
            return oldItem.track == (newItem as TrackListItem.AlbumTrack).track
        if (oldItem is TrackListItem.PlaylistTrack)
            return oldItem.track == (newItem as TrackListItem.PlaylistTrack).track
        return true
    }

}