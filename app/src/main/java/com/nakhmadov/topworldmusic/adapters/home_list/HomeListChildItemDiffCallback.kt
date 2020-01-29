package com.nakhmadov.topworldmusic.adapters.home_list

import androidx.recyclerview.widget.DiffUtil
import com.nakhmadov.topworldmusic.data.db.models.DatabaseModel

class HomeListChildItemDiffCallback : DiffUtil.ItemCallback<HomeListChildItems>() {
    override fun areItemsTheSame(
        oldItem: HomeListChildItems,
        newItem: HomeListChildItems
    ): Boolean {
        if (oldItem is HomeListChildItems.ArtistItem && newItem !is HomeListChildItems.ArtistItem)
            return false
        if (oldItem is HomeListChildItems.PlaylistItem && newItem !is HomeListChildItems.PlaylistItem)
            return false
        if (oldItem is HomeListChildItems.TrackItem && newItem !is HomeListChildItems.TrackItem)
            return false
        if (oldItem is HomeListChildItems.AlbumItem && newItem !is HomeListChildItems.AlbumItem)
            return false
        if (oldItem is HomeListChildItems.GenreItem && newItem !is HomeListChildItems.GenreItem)
            return false
        if (oldItem is HomeListChildItems.MoreItem && newItem !is HomeListChildItems.MoreItem)
            return false

        var oldItemId: Long = 0
        var newItemId: Long = 0

        if (oldItem is HomeListChildItems.ArtistItem) {
            oldItemId = oldItem.artist.artistId
            newItemId = (newItem as HomeListChildItems.ArtistItem).artist.artistId
        }
        if (oldItem is HomeListChildItems.PlaylistItem) {
            oldItemId = oldItem.playlist.playlistId
            newItemId = (newItem as HomeListChildItems.PlaylistItem).playlist.playlistId
        }
        if (oldItem is HomeListChildItems.TrackItem) {
            oldItemId = oldItem.track.trackId
            newItemId = (newItem as HomeListChildItems.TrackItem).track.trackId
        }
        if (oldItem is HomeListChildItems.AlbumItem) {
            oldItemId = oldItem.album.albumId
            newItemId = (newItem as HomeListChildItems.AlbumItem).album.albumId
        }
        if (oldItem is HomeListChildItems.GenreItem) {
            oldItemId = oldItem.genre.genreId
            newItemId = (newItem as HomeListChildItems.GenreItem).genre.genreId
        }
        if (oldItemId != newItemId)
            return false
        return true
    }

    override fun areContentsTheSame(
        oldItem: HomeListChildItems,
        newItem: HomeListChildItems
    ): Boolean {

        if (oldItem is HomeListChildItems.ArtistItem && newItem !is HomeListChildItems.ArtistItem)
            return false
        if (oldItem is HomeListChildItems.PlaylistItem && newItem !is HomeListChildItems.PlaylistItem)
            return false
        if (oldItem is HomeListChildItems.TrackItem && newItem !is HomeListChildItems.TrackItem)
            return false
        if (oldItem is HomeListChildItems.AlbumItem && newItem !is HomeListChildItems.AlbumItem)
            return false
        if (oldItem is HomeListChildItems.GenreItem && newItem !is HomeListChildItems.GenreItem)
            return false
        if (oldItem is HomeListChildItems.MoreItem && newItem !is HomeListChildItems.MoreItem)
            return false

        var oldItemContent: DatabaseModel? = null
        var newItemContent: DatabaseModel? = null

        if (oldItem is HomeListChildItems.ArtistItem) {
            oldItemContent = oldItem.artist
            newItemContent = (newItem as HomeListChildItems.ArtistItem).artist
        }
        if (oldItem is HomeListChildItems.PlaylistItem) {
            oldItemContent = oldItem.playlist
            newItemContent = (newItem as HomeListChildItems.PlaylistItem).playlist
        }
        if (oldItem is HomeListChildItems.TrackItem) {
            oldItemContent = oldItem.track
            newItemContent = (newItem as HomeListChildItems.TrackItem).track
        }
        if (oldItem is HomeListChildItems.AlbumItem) {
            oldItemContent = oldItem.album
            newItemContent = (newItem as HomeListChildItems.AlbumItem).album
        }
        if (oldItem is HomeListChildItems.GenreItem) {
            oldItemContent = oldItem.genre
            newItemContent = (newItem as HomeListChildItems.GenreItem).genre
        }
        if (oldItemContent != newItemContent)
            return false
        return true

    }


}