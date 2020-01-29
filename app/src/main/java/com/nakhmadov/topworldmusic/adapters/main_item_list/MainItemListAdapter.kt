package com.nakhmadov.topworldmusic.adapters.main_item_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nakhmadov.topworldmusic.data.db.models.*
import com.nakhmadov.topworldmusic.databinding.*
import com.nakhmadov.topworldmusic.listeners.*

class MainItemListAdapter(
    private val albumClickListener: AlbumClickListener,
    private val artistClickListener: ArtistClickListener,
    private val genreClickListener: GenreClickListener,
    private val playlistClickListener: PlaylistClickListener,
    private val trackClickListener: TrackClickListener
) : ListAdapter<MainItemListItem, RecyclerView.ViewHolder>(MainItemListItemDiffCallback()) {

    companion object {
        private const val ALBUM_VIEW_TYPE = 1
        private const val ARTIST_VIEW_TYPE = 2
        private const val TRACK_VIEW_TYPE = 3
        private const val PLAYLIST_VIEW_TYPE = 4
        private const val GENRE_VIEW_TYPE = 5
        private const val ALL_VIEW_TYPE = 6
    }

    fun submitAlbums(list: List<Album>) {
        val items = list.map { MainItemListItem.AlbumItem(it) }
        submitList(items)
    }

    fun submitArtists(list: List<Artist>) {
        val items = list.map { MainItemListItem.ArtistItem(it) }
        submitList(items)
    }

    fun submitTracks(list: List<Track>) {
        val items = list.map { MainItemListItem.TrackItem(it) }
        submitList(items)
    }

    fun submitPlaylists(list: List<Playlist>) {
        val items = list.map { MainItemListItem.PlaylistItem(it) }
        submitList(items)
    }

    fun submitGenres(list: List<Genre>) {
        val items = list.map { MainItemListItem.GenreItem(it) }
        submitList(items)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MainItemListItem.AlbumItem -> ALBUM_VIEW_TYPE
            is MainItemListItem.ArtistItem -> ARTIST_VIEW_TYPE
            is MainItemListItem.TrackItem -> TRACK_VIEW_TYPE
            is MainItemListItem.PlaylistItem -> PLAYLIST_VIEW_TYPE
            is MainItemListItem.GenreItem -> GENRE_VIEW_TYPE
            is MainItemListItem.AllItems -> ALL_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ALBUM_VIEW_TYPE -> AlbumViewHolder.from(parent)
            ARTIST_VIEW_TYPE -> ArtistViewHolder.from(parent)
            TRACK_VIEW_TYPE -> TrackViewHolder.from(parent)
            PLAYLIST_VIEW_TYPE -> PlaylistViewHolder.from(parent)
            GENRE_VIEW_TYPE -> GenreViewHolder.from(parent)
            ALL_VIEW_TYPE -> AllViewHolder.from(parent)
            else -> throw IllegalArgumentException("Unknown ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlbumViewHolder -> holder.bind((getItem(position) as MainItemListItem.AlbumItem).album, albumClickListener)
            is ArtistViewHolder -> holder.bind((getItem(position) as MainItemListItem.ArtistItem).artist, artistClickListener)
            is TrackViewHolder -> holder.bind((getItem(position) as MainItemListItem.TrackItem).track, trackClickListener)
            is PlaylistViewHolder -> holder.bind((getItem(position) as MainItemListItem.PlaylistItem).playlist, playlistClickListener)
            is GenreViewHolder -> holder.bind((getItem(position) as MainItemListItem.GenreItem).genre, genreClickListener)
            is AllViewHolder -> holder.bind((getItem(position) as MainItemListItem.AllItems).title)
        }
    }


    class AlbumViewHolder private constructor(private val binding: ItemMainItemListAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album, listener: AlbumClickListener) {
            binding.album = album
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AlbumViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemMainItemListAlbumBinding.inflate(inflater, parent, false)
                return AlbumViewHolder(binding)
            }
        }

    }

    class ArtistViewHolder private constructor(private val binding: ItemMainItemListArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(artist: Artist, listener: ArtistClickListener) {
            binding.artist = artist
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ArtistViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemMainItemListArtistBinding.inflate(inflater, parent, false)
                return ArtistViewHolder(binding)
            }
        }
    }

    class TrackViewHolder private constructor(private val binding: ItemMainItemListTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(track: Track, listener: TrackClickListener) {
            binding.track = track
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TrackViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemMainItemListTrackBinding.inflate(inflater, parent, false)
                return TrackViewHolder(binding)
            }
        }
    }

    class PlaylistViewHolder private constructor(private val binding: ItemMainItemListPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(playlist: Playlist, listener: PlaylistClickListener) {
            binding.playlist = playlist
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PlaylistViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemMainItemListPlaylistBinding.inflate(inflater, parent, false)
                return PlaylistViewHolder(binding)
            }
        }
    }

    class GenreViewHolder private constructor(private val binding: ItemMainItemListGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genre, listener: GenreClickListener) {
            binding.genre = genre
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): GenreViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemMainItemListGenreBinding.inflate(inflater, parent, false)
                return GenreViewHolder(binding)
            }
        }
    }

    class AllViewHolder private constructor(private val binding: ItemMainItemListAllBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) {
            binding.title = title
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AllViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemMainItemListAllBinding.inflate(inflater, parent, false)
                return AllViewHolder(binding)
            }
        }
    }


}