package com.nakhmadov.topworldmusic.adapters.main_item_list

import androidx.recyclerview.widget.DiffUtil
import com.nakhmadov.topworldmusic.data.db.models.DatabaseModel

class MainItemListItemDiffCallback : DiffUtil.ItemCallback<MainItemListItem>() {
    override fun areItemsTheSame(oldItem: MainItemListItem, newItem: MainItemListItem): Boolean {

        if (oldItem is MainItemListItem.ArtistItem && newItem !is MainItemListItem.ArtistItem)
            return false
        if (oldItem is MainItemListItem.PlaylistItem && newItem !is MainItemListItem.PlaylistItem)
            return false
        if (oldItem is MainItemListItem.TrackItem && newItem !is MainItemListItem.TrackItem)
            return false
        if (oldItem is MainItemListItem.AlbumItem && newItem !is MainItemListItem.AlbumItem)
            return false
        if (oldItem is MainItemListItem.GenreItem && newItem !is MainItemListItem.GenreItem)
            return false
        if (oldItem is MainItemListItem.AllItems && newItem !is MainItemListItem.AllItems)
            return false

        var oldItemId: Long = 0
        var newItemId: Long = 0

        if (oldItem is MainItemListItem.ArtistItem) {
            oldItemId = oldItem.artist.artistId
            newItemId = (newItem as MainItemListItem.ArtistItem).artist.artistId
        }
        if (oldItem is MainItemListItem.PlaylistItem) {
            oldItemId = oldItem.playlist.playlistId
            newItemId = (newItem as MainItemListItem.PlaylistItem).playlist.playlistId
        }
        if (oldItem is MainItemListItem.TrackItem) {
            oldItemId = oldItem.track.trackId
            newItemId = (newItem as MainItemListItem.TrackItem).track.trackId
        }
        if (oldItem is MainItemListItem.AlbumItem) {
            oldItemId = oldItem.album.albumId
            newItemId = (newItem as MainItemListItem.AlbumItem).album.albumId
        }
        if (oldItem is MainItemListItem.GenreItem) {
            oldItemId = oldItem.genre.genreId
            newItemId = (newItem as MainItemListItem.GenreItem).genre.genreId
        }
        if (oldItem is MainItemListItem.AllItems)
            return oldItem.title == (newItem as MainItemListItem.AllItems).title
        if (oldItemId != newItemId)
            return false
        return true

    }

    override fun areContentsTheSame(oldItem: MainItemListItem, newItem: MainItemListItem): Boolean {
        if (oldItem is MainItemListItem.ArtistItem && newItem !is MainItemListItem.ArtistItem)
            return false
        if (oldItem is MainItemListItem.PlaylistItem && newItem !is MainItemListItem.PlaylistItem)
            return false
        if (oldItem is MainItemListItem.TrackItem && newItem !is MainItemListItem.TrackItem)
            return false
        if (oldItem is MainItemListItem.AlbumItem && newItem !is MainItemListItem.AlbumItem)
            return false
        if (oldItem is MainItemListItem.GenreItem && newItem !is MainItemListItem.GenreItem)
            return false
        if (oldItem is MainItemListItem.AllItems && newItem !is MainItemListItem.AllItems)
            return false

        var oldItemContent: DatabaseModel? = null
        var newItemContent: DatabaseModel? = null

        if (oldItem is MainItemListItem.ArtistItem) {
            oldItemContent = oldItem.artist
            newItemContent = (newItem as MainItemListItem.ArtistItem).artist
        }
        if (oldItem is MainItemListItem.PlaylistItem) {
            oldItemContent = oldItem.playlist
            newItemContent = (newItem as MainItemListItem.PlaylistItem).playlist
        }
        if (oldItem is MainItemListItem.TrackItem) {
            oldItemContent = oldItem.track
            newItemContent = (newItem as MainItemListItem.TrackItem).track
        }
        if (oldItem is MainItemListItem.AlbumItem) {
            oldItemContent = oldItem.album
            newItemContent = (newItem as MainItemListItem.AlbumItem).album
        }
        if (oldItem is MainItemListItem.GenreItem) {
            oldItemContent = oldItem.genre
            newItemContent = (newItem as MainItemListItem.GenreItem).genre
        }
        if (oldItem is MainItemListItem.AllItems)
            return oldItem.title == (newItem as MainItemListItem.AllItems).title
        if (oldItemContent != newItemContent)
            return false
        return true
    }

}