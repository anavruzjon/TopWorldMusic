package com.nakhmadov.topworldmusic.adapters.home_list

import androidx.recyclerview.widget.DiffUtil
import com.nakhmadov.topworldmusic.data.db.models.DatabaseModel
import com.nakhmadov.topworldmusic.presentation.models.HomeItems

class HomeItemsDiffCallback : DiffUtil.ItemCallback<HomeItems>() {
    override fun areItemsTheSame(oldItem: HomeItems, newItem: HomeItems): Boolean {
        if (oldItem is HomeItems.GenresItem && newItem !is HomeItems.GenresItem)
            return false
        if (oldItem is HomeItems.AlbumsItem && newItem !is HomeItems.AlbumsItem)
            return false
        if (oldItem is HomeItems.TracksItem && newItem !is HomeItems.TracksItem)
            return false
        if (oldItem is HomeItems.PlaylistsItem && newItem !is HomeItems.PlaylistsItem)
            return false
        if (oldItem is HomeItems.ArtistsItem && newItem !is HomeItems.ArtistsItem)
            return false
        if (oldItem.title != newItem.title)
            return false

        val oldItemList = oldItem.items
        val newItemList = newItem.items

        if (oldItemList.size != newItemList.size)
            return false
        for (i in oldItemList.indices) {

            var oldItemId: Long = 0
            var newItemId: Long = 0
            when (oldItem) {
                is HomeItems.GenresItem -> {
                    oldItemId = oldItem.genres[i].genreId
                    newItemId = (newItem as HomeItems.GenresItem).genres[i].genreId
                }

                is HomeItems.AlbumsItem -> {
                    oldItemId = oldItem.albums[i].albumId
                    newItemId = (newItem as HomeItems.AlbumsItem).albums[i].albumId
                }

                is HomeItems.TracksItem -> {
                    oldItemId = oldItem.tracks[i].trackId
                    newItemId = (newItem as HomeItems.TracksItem).tracks[i].trackId
                }
                is HomeItems.PlaylistsItem -> {
                    oldItemId = oldItem.playlists[i].playlistId
                    newItemId = (newItem as HomeItems.PlaylistsItem).playlists[i].playlistId
                }
                is HomeItems.ArtistsItem -> {
                    oldItemId = oldItem.artists[i].artistId
                    newItemId = (newItem as HomeItems.ArtistsItem).artists[i].artistId
                }
            }
            if (oldItemId != newItemId)
                return false
        }

        return true
    }

    override fun areContentsTheSame(oldItem: HomeItems, newItem: HomeItems): Boolean {
        if (oldItem is HomeItems.GenresItem && newItem !is HomeItems.GenresItem)
            return false
        if (oldItem is HomeItems.AlbumsItem && newItem !is HomeItems.AlbumsItem)
            return false
        if (oldItem is HomeItems.TracksItem && newItem !is HomeItems.TracksItem)
            return false
        if (oldItem is HomeItems.PlaylistsItem && newItem !is HomeItems.PlaylistsItem)
            return false
        if (oldItem is HomeItems.ArtistsItem && newItem !is HomeItems.ArtistsItem)
            return false
        if (oldItem.title != newItem.title)
            return false

        val oldItemList = oldItem.items
        val newItemList = newItem.items

        if (oldItemList.size != newItemList.size)
            return false
        for (i in oldItemList.indices) {

            var oldItemContent: DatabaseModel? = null
            var newItemContent: DatabaseModel? = null

            when (oldItem) {
                is HomeItems.GenresItem -> {
                    oldItemContent = oldItem.genres[i]
                    newItemContent = (newItem as HomeItems.GenresItem).genres[i]
                }
                is HomeItems.AlbumsItem -> {
                    oldItemContent = oldItem.albums[i]
                    newItemContent = (newItem as HomeItems.AlbumsItem).albums[i]
                }
                is HomeItems.TracksItem -> {
                    oldItemContent = oldItem.tracks[i]
                    newItemContent = (newItem as HomeItems.TracksItem).tracks[i]
                }
                is HomeItems.PlaylistsItem -> {
                    oldItemContent = oldItem.playlists[i]
                    newItemContent = (newItem as HomeItems.PlaylistsItem).playlists[i]
                }
                is HomeItems.ArtistsItem -> {
                    oldItemContent = oldItem.artists[i]
                    newItemContent = (newItem as HomeItems.ArtistsItem).artists[i]
                }
            }
            if (oldItemContent != newItemContent)
                return false

        }

        return true
    }

}