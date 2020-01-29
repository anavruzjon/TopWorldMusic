package com.nakhmadov.topworldmusic.adapters.home_list

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nakhmadov.topworldmusic.data.db.models.*
import com.nakhmadov.topworldmusic.databinding.*
import com.nakhmadov.topworldmusic.listeners.*
import com.nakhmadov.topworldmusic.presentation.models.HomeItems
import com.nakhmadov.topworldmusic.util.HomeListType

class HomeListChildAdapter(
    private val homeListListener: HomeListClickListener,
    private val albumClickListener: AlbumClickListener,
    private val artistClickListener: ArtistClickListener,
    private val playlistClickListener: PlaylistClickListener,
    private val genreClickListener: GenreClickListener,
    private val trackClickListener: TrackClickListener
) :
    ListAdapter<HomeListChildItems, RecyclerView.ViewHolder>(HomeListChildItemDiffCallback()) {

    companion object {
        private const val ALBUM_VIEW_TYPE = 1
        private const val ARTIST_VIEW_TYPE = 2
        private const val TRACK_VIEW_TYPE = 3
        private const val PLAYLIST_VIEW_TYPE = 4
        private const val GENRE_VIEW_TYPE = 5
        private const val MORE_VIEW_TYPE = 6
    }

    private var listType: HomeListType? = null

    fun submitItem(item: HomeItems) {
        listType = item.listType
        val list = mutableListOf<HomeListChildItems>()
        var i = 0
        when (item) {
            is HomeItems.AlbumsItem -> {
                while (i < 6 && i < item.albums.size) {
                    list.add(HomeListChildItems.AlbumItem(item.albums[i]))
                    i++
                }
            }
            is HomeItems.ArtistsItem -> {
                while (i < 6 && i < item.artists.size) {
                    list.add(HomeListChildItems.ArtistItem(item.artists[i]))
                    i++
                }

            }
            is HomeItems.TracksItem -> {
                while (i < 6 && i < item.tracks.size) {
                    list.add(HomeListChildItems.TrackItem(item.tracks[i]))
                    i++
                }
            }
            is HomeItems.PlaylistsItem -> {
                while (i < 6 && i < item.playlists.size) {
                    list.add(HomeListChildItems.PlaylistItem(item.playlists[i]))
                    i++
                }
            }
            is HomeItems.GenresItem -> {
                while (i < 6 && i < item.genres.size) {
                    list.add(HomeListChildItems.GenreItem(item.genres[i]))
                    i++
                }
            }
        }
        list.add(HomeListChildItems.MoreItem)
        submitList(list)

    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HomeListChildItems.AlbumItem -> ALBUM_VIEW_TYPE
            is HomeListChildItems.ArtistItem -> ARTIST_VIEW_TYPE
            is HomeListChildItems.TrackItem -> TRACK_VIEW_TYPE
            is HomeListChildItems.PlaylistItem -> PLAYLIST_VIEW_TYPE
            is HomeListChildItems.GenreItem -> GENRE_VIEW_TYPE
            is HomeListChildItems.MoreItem -> MORE_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ALBUM_VIEW_TYPE -> AlbumViewHolder.from(parent)
            ARTIST_VIEW_TYPE -> ArtistViewHolder.from(parent)
            TRACK_VIEW_TYPE -> TrackViewHolder.from(parent)
            PLAYLIST_VIEW_TYPE -> PlaylistViewHolder.from(parent)
            GENRE_VIEW_TYPE -> GenreViewHolder.from(parent)
            MORE_VIEW_TYPE -> MoreViewHolder.from(parent)
            else -> throw IllegalArgumentException("Unknown ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is AlbumViewHolder -> holder.bind(
                (item as HomeListChildItems.AlbumItem).album,
                albumClickListener
            )
            is ArtistViewHolder -> holder.bind(
                (item as HomeListChildItems.ArtistItem).artist,
                artistClickListener
            )
            is TrackViewHolder -> holder.bind(
                (item as HomeListChildItems.TrackItem).track,
                trackClickListener
            )
            is PlaylistViewHolder -> holder.bind(
                (item as HomeListChildItems.PlaylistItem).playlist,
                playlistClickListener
            )
            is GenreViewHolder -> holder.bind(
                (item as HomeListChildItems.GenreItem).genre,
                genreClickListener
            )
            is MoreViewHolder -> {
                listType?.let { holder.bind(it, homeListListener) }
            }
        }
    }

    class AlbumViewHolder private constructor(private val binding: ItemHomeListChildAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Album, listener: AlbumClickListener) {
            binding.item = item
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AlbumViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemHomeListChildAlbumBinding.inflate(inflater, parent, false)
                return AlbumViewHolder(binding)
            }
        }

    }

    class ArtistViewHolder private constructor(private val binding: ItemHomeListChildArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Artist, listener: ArtistClickListener) {
            binding.item = item
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ArtistViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemHomeListChildArtistBinding.inflate(inflater, parent, false)
                return ArtistViewHolder(binding)
            }
        }

    }

    class TrackViewHolder private constructor(private val binding: ItemHomeListChildTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Track, listener: TrackClickListener) {
            binding.item = item
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TrackViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemHomeListChildTrackBinding.inflate(inflater, parent, false)
                return TrackViewHolder(binding)
            }
        }

    }

    class PlaylistViewHolder private constructor(private val binding: ItemHomeListChildPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Playlist, listener: PlaylistClickListener) {
            binding.item = item
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PlaylistViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemHomeListChildPlaylistBinding.inflate(inflater, parent, false)
                return PlaylistViewHolder(binding)
            }
        }

    }

    class GenreViewHolder private constructor(private val binding: ItemHomeListChildGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Genre, listener: GenreClickListener) {
            binding.item = item
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): GenreViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemHomeListChildGenreBinding.inflate(inflater, parent, false)
                return GenreViewHolder(binding)
            }
        }

    }

    class MoreViewHolder private constructor(private val binding: ItemHomeListChildMoreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listType: HomeListType, listener: HomeListClickListener) {
            binding.type = listType
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MoreViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemHomeListChildMoreBinding.inflate(inflater, parent, false)
                return MoreViewHolder(binding)
            }
        }
    }


}