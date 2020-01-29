package com.nakhmadov.topworldmusic.ui


import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.nakhmadov.topworldmusic.adapters.main_item_list.MainItemListAdapter
import com.nakhmadov.topworldmusic.data.db.models.*
import com.nakhmadov.topworldmusic.databinding.FragmentHomeListDetailBinding
import com.nakhmadov.topworldmusic.listeners.*
import com.nakhmadov.topworldmusic.presentation.presenters.HomeListDetailPresenter
import com.nakhmadov.topworldmusic.presentation.views.HomeListDetailView
import com.nakhmadov.topworldmusic.util.HomeListType
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeListDetailFragment : MvpAppCompatFragment(), HomeListDetailView {

    private lateinit var binding: FragmentHomeListDetailBinding
    private lateinit var listType: HomeListType
    private lateinit var adapter: MainItemListAdapter


    @InjectPresenter
    internal lateinit var presenter: HomeListDetailPresenter

    @ProvidePresenter
    fun provideHomeListDetailPresenter(): HomeListDetailPresenter {
        listType = HomeListDetailFragmentArgs.fromBundle(arguments!!).listType
        val presenter: HomeListDetailPresenter by inject { parametersOf(listType) }
        return presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeListDetailBinding.inflate(inflater, container, false)
        setupRecycler()

        binding.playButton.visibility = View.GONE
        val layoutParams = binding.toolbar.layoutParams as AppBarLayout.LayoutParams
        var height =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56f, resources.displayMetrics)
        layoutParams.height = height.toInt()
        binding.listType = listType
        binding.title = when (listType) {
            HomeListType.EDITORIAL_RELEASE -> "Рекомендуемые новинки"
            HomeListType.EDITORIAL_PLAYLIST -> "Рекомендуемые плейлитсты"
            HomeListType.CHART_ALBUM -> "Топ альбомы"
            HomeListType.CHART_TRACK -> {
                binding.playButton.visibility = View.VISIBLE
                height = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    84f,
                    resources.displayMetrics
                )
                layoutParams.height = height.toInt()
                "Лучшие треки"
            }
            HomeListType.EDITORIAL_ARTIST -> "Рекомендуемые исполнители"
            HomeListType.GENRE -> "Музыка по жанрам"
        }
        binding.toolbar.layoutParams = layoutParams
        binding.toolbar.requestLayout()
        return binding.root
    }

    private fun setupRecycler() {
        adapter = MainItemListAdapter(
            albumClickListener = AlbumClickListener {
                findNavController().navigate(
                    HomeListDetailFragmentDirections.actionHomeListDetailFragmentToAlbumDetailFragment(
                        albumId = it
                    )
                )
            },
            artistClickListener = ArtistClickListener {
                findNavController().navigate(
                    HomeListDetailFragmentDirections.actionHomeListDetailFragmentToArtistDetailFragment(
                        artistId = it
                    )
                )
            },
            genreClickListener = GenreClickListener {
                findNavController().navigate(
                    HomeListDetailFragmentDirections.actionHomeListDetailFragmentToGenreDetailFragment(
                        genreId = it
                    )
                )
            },
            playlistClickListener = PlaylistClickListener {
                findNavController().navigate(
                    HomeListDetailFragmentDirections.actionHomeListDetailFragmentToPlaylistDetailFragment(
                        playlistId = it
                    )
                )
            },
            trackClickListener = TrackClickListener { }
        )
        binding.itemsListRecycler.setHasFixedSize(true)
        binding.itemsListRecycler.adapter = adapter

        if (listType != HomeListType.CHART_TRACK) {
            binding.itemsListRecycler.layoutManager = GridLayoutManager(context, 2)
            binding.itemsListRecycler.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val spacing = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        8f,
                        resources.displayMetrics
                    )
                    outRect.left = spacing.toInt()
                    outRect.right = spacing.toInt()
                }
            })
        } else
            binding.itemsListRecycler.layoutManager = LinearLayoutManager(context)


    }

    override fun submitItems(items: List<DatabaseModel>) {
        Log.d("myLogs", "SUBMITTING LIST: $items")
        when (listType) {
            HomeListType.EDITORIAL_RELEASE -> {
                adapter.submitAlbums(items.map { it as Album })
            }
            HomeListType.EDITORIAL_PLAYLIST -> {
                adapter.submitPlaylists(items.map { it as Playlist })
            }
            HomeListType.CHART_ALBUM -> {
                adapter.submitAlbums(items.map { it as Album })
            }
            HomeListType.CHART_TRACK -> {
                adapter.submitTracks(items.map { it as Track })
            }
            HomeListType.EDITORIAL_ARTIST -> {
                adapter.submitArtists(items.map { it as Artist })
            }
            HomeListType.GENRE -> {
                adapter.submitGenres(items.map { it as Genre })
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
