package com.nakhmadov.topworldmusic.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nakhmadov.topworldmusic.R
import com.nakhmadov.topworldmusic.databinding.FragmentArtistDetailBinding
import com.nakhmadov.topworldmusic.presentation.presenters.ArtistDetailPresenter
import com.nakhmadov.topworldmusic.presentation.views.ArtistDetailView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get

/**
 * A simple [Fragment] subclass.
 */
class ArtistDetailFragment : MvpAppCompatFragment(), ArtistDetailView {

    private lateinit var binding: FragmentArtistDetailBinding

    @InjectPresenter
    internal lateinit var presenter: ArtistDetailPresenter

    @ProvidePresenter
    fun provideArtistDetailPresenter(): ArtistDetailPresenter {
        return get()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtistDetailBinding.inflate(inflater, container, false)

        return binding.root
    }


}
