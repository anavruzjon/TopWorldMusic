package com.nakhmadov.topworldmusic.ui


import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nakhmadov.topworldmusic.R
import com.nakhmadov.topworldmusic.adapters.main_item_list.MainItemListAdapter
import com.nakhmadov.topworldmusic.data.db.models.Album
import com.nakhmadov.topworldmusic.data.db.models.Genre
import com.nakhmadov.topworldmusic.databinding.FragmentGenreDetailBinding
import com.nakhmadov.topworldmusic.listeners.*
import com.nakhmadov.topworldmusic.presentation.presenters.GenreDetailPresenter
import com.nakhmadov.topworldmusic.presentation.views.GenreDetailView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


/**
 * A simple [Fragment] subclass.
 */
class GenreDetailFragment : MvpAppCompatFragment(), GenreDetailView {

    private lateinit var binding: FragmentGenreDetailBinding
    private lateinit var adapter: MainItemListAdapter
    private var genreId: Long? = null
    private var genre: Genre? = null

    @InjectPresenter
    internal lateinit var presenter: GenreDetailPresenter

    @ProvidePresenter
    fun provideGenreDetailPresenter(): GenreDetailPresenter {
        genreId = GenreDetailFragmentArgs.fromBundle(arguments!!).genreId
        val presenter: GenreDetailPresenter by inject { parametersOf(genreId) }
        return presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenreDetailBinding.inflate(inflater, container, false)
        setupRecycler()
        return binding.root
    }

    private fun setupRecycler() {
        adapter = MainItemListAdapter(
            albumClickListener = AlbumClickListener {
                findNavController().navigate(
                    GenreDetailFragmentDirections.actionGenreDetailFragmentToAlbumDetailFragment(
                        albumId = it
                    )
                )
            },
            artistClickListener = ArtistClickListener { },
            trackClickListener = TrackClickListener { },
            playlistClickListener = PlaylistClickListener { },
            genreClickListener = GenreClickListener { }
        )

        binding.genreAlbumsRecycler.setHasFixedSize(false)
        binding.genreAlbumsRecycler.layoutManager = GridLayoutManager(context, 2)
        binding.genreAlbumsRecycler.adapter = adapter
        binding.genreAlbumsRecycler.addItemDecoration(object : RecyclerView.ItemDecoration() {
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
    }

    override fun submitAlbums(albums: List<Album>) {
        adapter.submitAlbums(albums)
        Log.d("myLogs", "VIEW GENRE ALBUMS: $albums")

    }


}
