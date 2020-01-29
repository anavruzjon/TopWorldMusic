package com.nakhmadov.topworldmusic.ui


import android.content.Intent
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
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.nakhmadov.topworldmusic.adapters.track_list.TrackListAdapter
import com.nakhmadov.topworldmusic.data.db.models.Album
import com.nakhmadov.topworldmusic.data.db.models.Track
import com.nakhmadov.topworldmusic.databinding.FragmentAlbumDetailBinding
import com.nakhmadov.topworldmusic.listeners.TrackClickListener
import com.nakhmadov.topworldmusic.presentation.presenters.AlbumDetailPresenter
import com.nakhmadov.topworldmusic.presentation.views.AlbumDetailView
import com.nakhmadov.topworldmusic.services.MusicService
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AlbumDetailFragment : MvpAppCompatFragment(), AlbumDetailView {

    private lateinit var binding: FragmentAlbumDetailBinding
    private lateinit var adapter: TrackListAdapter
    private var albumId: Long? = null
    private var album: Album? = null

    @InjectPresenter
    internal lateinit var presenter: AlbumDetailPresenter

    @ProvidePresenter
    fun provideAlbumDetailPresenter(): AlbumDetailPresenter {

        albumId = AlbumDetailFragmentArgs.fromBundle(arguments!!).albumId
        val presenter: AlbumDetailPresenter by inject { parametersOf(albumId) }
        return presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        setupRecycler()
        return binding.root
    }

    private fun setupRecycler() {
        adapter = TrackListAdapter(listener = TrackClickListener {
            /**TODO HANDLE TRACK CLICK **/

            val application = (activity as AppCompatActivity).application

            val musicServiceIntent = Intent(application.applicationContext, MusicService::class.java)
            musicServiceIntent.putExtra("url", )

        })
        binding.tracksRecycler.setHasFixedSize(false)
        binding.tracksRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.tracksRecycler.adapter = adapter
        Log.d("myLogs", "setupRecycler")
    }

    override fun submitAlbum(album: Album) {
        binding.album = album
        this.album = album
        collapsingToolbarSetup()
        setSupportActionBar()
        presenter.getAlbumTracks()
        Log.d("myLogs", "submitAlbum")
    }

    override fun submitAlbumTracks(tracks: List<Track>) {
        adapter.submitAlbumTracks(tracks)
        Log.d("myLogs", "submitTracks, List: $tracks")
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

        binding.appbar.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                Log.d("myLogs", "SCROLL RANGE: $scrollRange, VERTICAL OFFSET: $verticalOffset")
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.collapsingToolbar.title = album?.title
                    binding.toolbarTitleText.text = album?.title

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
