package com.nakhmadov.topworldmusic.ui

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.nakhmadov.topworldmusic.R
import com.nakhmadov.topworldmusic.adapters.track_list.TrackListAdapter
import com.nakhmadov.topworldmusic.data.db.models.Album
import com.nakhmadov.topworldmusic.data.db.models.Playlist
import com.nakhmadov.topworldmusic.data.db.models.Track
import com.nakhmadov.topworldmusic.databinding.FragmentAlbumDetailBinding
import com.nakhmadov.topworldmusic.databinding.FragmentPlaylistDetailBinding
import com.nakhmadov.topworldmusic.listeners.TrackClickListener
import com.nakhmadov.topworldmusic.presentation.presenters.AlbumDetailPresenter
import com.nakhmadov.topworldmusic.presentation.presenters.PlaylistDetailPresenter
import com.nakhmadov.topworldmusic.presentation.views.PlaylistDetailView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class PlaylistDetailFragment : MvpAppCompatFragment(), PlaylistDetailView {

    private lateinit var binding: FragmentPlaylistDetailBinding
    private lateinit var adapter: TrackListAdapter
    private var playlistId: Long? = null
    private var playlist: Playlist? = null

    @InjectPresenter
    internal lateinit var presenter: PlaylistDetailPresenter

    @ProvidePresenter
    fun providePlaylistDetailPresenter(): PlaylistDetailPresenter {
        playlistId = PlaylistDetailFragmentArgs.fromBundle(arguments!!).playlistId
        val presenter: PlaylistDetailPresenter by inject { parametersOf(playlistId) }
        return presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false)
        setupRecycler()
        return binding.root
    }

    private fun setupRecycler() {
        adapter = TrackListAdapter(listener =
        /**TODO HANDLE TRACK CLICK **/
        TrackClickListener { /**TODO HANDLE TRACK CLICK **/ })
        binding.tracksRecycler.setHasFixedSize(false)
        binding.tracksRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.tracksRecycler.adapter = adapter
        Log.d("myLogs", "setupRecycler")
    }

    override fun submitPlaylist(playlist: Playlist?) {
        binding.playlist = playlist
        this.playlist = playlist
        presenter.getPlaylistTracks()
        Log.d("myLogs", "submitPlaylist: $playlist")
        collapsingToolbarSetup()
        setSupportActionBar()
    }

    override fun submitPlaylistTracks(tracks: List<Track>) {
        adapter.submitPlaylistTracks(tracks)
        Log.d("myLogs", "submitPlaylistTracks, List: $tracks")
    }

    private fun collapsingToolbarSetup() {

        val toolbarHeightDp = 84f
        val toolbarHeightToPx =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                toolbarHeightDp,
                resources.displayMetrics
            )

        Log.d("myLogs", "84dp to pixels $toolbarHeightToPx")

        binding.appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                Log.d("myLogs", "SCROLL RANGE: $scrollRange, VERTICAL OFFSET: $verticalOffset")
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.collapsingToolbar.title = playlist?.title
                    binding.toolbarTitleText.text = playlist?.title

                    isShow = true
                } else if (isShow) {
                    binding.collapsingToolbar.title = " "
                    binding.toolbarTitleText.text = " "

                    isShow = false
                }
            }
        })
    }

    private fun setSupportActionBar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


}
