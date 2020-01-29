package com.nakhmadov.topworldmusic.adapters.home_list

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nakhmadov.topworldmusic.databinding.ItemHomeListBinding
import com.nakhmadov.topworldmusic.listeners.*
import com.nakhmadov.topworldmusic.presentation.models.HomeItems

class HomeListAdapter(
    private val homeListListener: HomeListClickListener,
    private val albumClickListener: AlbumClickListener,
    private val artistClickListener: ArtistClickListener,
    private val playlistClickListener: PlaylistClickListener,
    private val genreClickListener: GenreClickListener,
    private val trackClickListener: TrackClickListener
) :
    ListAdapter<HomeItems, RecyclerView.ViewHolder>(HomeItemsDiffCallback()) {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeListItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val parent = getItem(position)
        val childRecyclerLayoutManager = LinearLayoutManager(
            (holder as HomeListItemViewHolder).getChildRecycler().context, HORIZONTAL, false
        )
        childRecyclerLayoutManager.initialPrefetchItemCount = 6
        val childAdapter = HomeListChildAdapter(
            homeListListener,
            albumClickListener,
            artistClickListener,
            playlistClickListener,
            genreClickListener,
            trackClickListener
        )
        childAdapter.submitItem(parent)
        holder.getChildRecycler().apply {
            layoutManager = childRecyclerLayoutManager
            setHasFixedSize(false)
            adapter = childAdapter
            setRecycledViewPool(viewPool)
        }

        holder.bind(parent, homeListListener)

    }

    class HomeListItemViewHolder private constructor(private val binding: ItemHomeListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeItems, listener: HomeListClickListener) {
            binding.item = item
            binding.listener = listener
            binding.executePendingBindings()

        }

        fun getChildRecycler(): RecyclerView {
            return binding.albumsListRecycler
        }

        companion object {
            fun from(parent: ViewGroup): HomeListItemViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemHomeListBinding.inflate(inflater, parent, false)
                return HomeListItemViewHolder(binding)
            }

        }
    }
}