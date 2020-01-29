package com.nakhmadov.topworldmusic.adapters.track_list

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nakhmadov.topworldmusic.adapters.home_list.HomeListChildAdapter
import com.nakhmadov.topworldmusic.data.db.models.Track
import com.nakhmadov.topworldmusic.databinding.ItemAlbumTrackBinding
import com.nakhmadov.topworldmusic.databinding.ItemPlaylistTrackBinding
import com.nakhmadov.topworldmusic.listeners.TrackClickListener

class TrackListAdapter(private val listener: TrackClickListener) :
    ListAdapter<TrackListItem, RecyclerView.ViewHolder>(TrackDiffItemCallback()) {

    companion object {
        const val ALBUM_VIEW_TYPE = 1
        const val PLAYLIST_VIEW_TYPE = 2
    }

    fun submitAlbumTracks(list: List<Track>) {
        val submitTracks = mutableListOf<TrackListItem>()
        list.forEach {
            submitTracks.add(TrackListItem.AlbumTrack(track = it))
        }
        submitList(submitTracks)
    }

    fun submitPlaylistTracks(list: List<Track>) {
        val submitTracks = mutableListOf<TrackListItem>()
        list.forEach {
            submitTracks.add(TrackListItem.PlaylistTrack(track = it))
        }
        submitList(submitTracks)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if (item is TrackListItem.AlbumTrack)
            return ALBUM_VIEW_TYPE
        return PLAYLIST_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ALBUM_VIEW_TYPE)
            return AlbumTrackViewHolder.from(parent)
        return PlaylistTrackViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is AlbumTrackViewHolder)
            holder.bind(position, (item as TrackListItem.AlbumTrack).track, listener)
        else
            (holder as PlaylistTrackViewHolder).bind((item as TrackListItem.PlaylistTrack).track, listener)
    }

    class AlbumTrackViewHolder private constructor(private val binding: ItemAlbumTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, item: Track, listener: TrackClickListener) {
            binding.position = (position + 1).toString()
            binding.track = item
            binding.listener = listener
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): AlbumTrackViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemAlbumTrackBinding.inflate(inflater, parent, false)
                return AlbumTrackViewHolder(binding)
            }
        }
    }

    class PlaylistTrackViewHolder private constructor(private val binding: ItemPlaylistTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Track, listener: TrackClickListener) {
            binding.track = item
            binding.listener = listener
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): PlaylistTrackViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemPlaylistTrackBinding.inflate(inflater, parent, false)
                return PlaylistTrackViewHolder(binding)
            }
        }
    }


}