package com.nakhmadov.topworldmusic.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL

import com.nakhmadov.topworldmusic.adapters.home_list.HomeListAdapter
import com.nakhmadov.topworldmusic.databinding.FragmentHomeBinding
import com.nakhmadov.topworldmusic.listeners.*
import com.nakhmadov.topworldmusic.presentation.models.HomeItems
import com.nakhmadov.topworldmusic.presentation.presenters.HomePresenter
import com.nakhmadov.topworldmusic.presentation.views.HomeView
import com.nakhmadov.topworldmusic.util.HomeListType
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get

class HomeFragment : MvpAppCompatFragment(), HomeView {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeListAdapter: HomeListAdapter

    @InjectPresenter
    internal lateinit var presenter: HomePresenter

    @ProvidePresenter
    fun provideHomePresenter(): HomePresenter {
        return get()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initRecycler()
        return binding.root
    }

    override fun startLoading() {
        binding.mainContentRecycler.visibility = View.GONE
        binding.circularProgressView.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        binding.mainContentRecycler.visibility = View.VISIBLE
        binding.circularProgressView.visibility = View.GONE
    }

    override fun initRecycler() {
        homeListAdapter =
            HomeListAdapter(homeListListener = HomeListClickListener { type: HomeListType ->
                Log.d("myLogs", type.toString())
                findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToHomeListDetailFragment(type))
            }, albumClickListener = AlbumClickListener { albumId ->
                findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToAlbumDetailFragment(albumId))
                Log.d("myLogs", "AlbumId: $albumId")
            }, artistClickListener = ArtistClickListener { artistId ->
                findNavController()
                    .navigate(
                        HomeFragmentDirections.actionHomeFragmentToArtistDetailFragment(artistId)
                    )
                Log.d("myLogs", "ArtistId: $artistId")
            }, playlistClickListener = PlaylistClickListener { playlistId ->
                findNavController()
                    .navigate(
                        HomeFragmentDirections.actionHomeFragmentToPlaylistDetailFragment(playlistId)
                    )
                Log.d("myLogs", "PlaylistId: $playlistId")
            }, genreClickListener = GenreClickListener { genreId ->
                findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToGenreDetailFragment(genreId))
                Log.d("myLogs", "GenreId: $genreId")
            }, trackClickListener = TrackClickListener { trackId ->
                Log.d("myLogs", "TrackId: $trackId")
            })
        binding.mainContentRecycler.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)
            adapter = homeListAdapter
        }
    }

    override fun submitListToAdapter(list: List<HomeItems>) {
        homeListAdapter.submitList(list)
        list.forEach {
            Log.d("myLogs", "Title: ${it.title} List: ${it.items}")
        }

    }

    companion object {
        fun newInstance() = HomeFragment()
    }


}
