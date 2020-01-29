package com.nakhmadov.topworldmusic.listeners

import com.nakhmadov.topworldmusic.data.db.models.Track

class TrackClickListener(val listener: (trackId: Long) -> Unit) {
    fun onClick(track: Track) = listener(track.trackId)
}