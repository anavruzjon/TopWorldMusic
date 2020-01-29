package com.nakhmadov.topworldmusic.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nakhmadov.topworldmusic.BuildConfig

import com.nakhmadov.topworldmusic.R
import com.nakhmadov.topworldmusic.databinding.FragmentAuthBinding
import com.nakhmadov.topworldmusic.presentation.presenters.AuthPresenter
import com.nakhmadov.topworldmusic.presentation.views.AuthView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get

class AuthFragment : MvpAppCompatFragment(), AuthView {

    @InjectPresenter
    internal lateinit var presenter: AuthPresenter

    @ProvidePresenter
    fun provideAuthPresenter(): AuthPresenter {
        return get()
    }

    private lateinit var binding: FragmentAuthBinding
    private val clientId = BuildConfig.AppId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAuthBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun showError(errorMsg: String) {
        Log.i("myLogs", "Error message: $errorMsg")
    }

    override fun navigateToHomeFragment(token: String) {
        Log.i("myLogs", "TOKEN ACCEPTED: $token")
        findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToHomeFragment(token))
    }

    override fun startLoading() {
        binding.circularProgressView.visibility = View.VISIBLE
        binding.webView.visibility = View.GONE
    }

    override fun stopLoading() {
        binding.circularProgressView.visibility = View.GONE
        binding.webView.visibility = View.VISIBLE
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initWebView() {
        binding.webView.webViewClient = presenter.webViewClient
        binding.webView.settings.javaScriptEnabled = true
        val redirectUrl = getString(R.string.redirect_url)
        val loadUrl = getString(R.string.auth_url, clientId, redirectUrl)
        binding.webView.loadUrl(loadUrl)
    }

    companion object {
        fun newInstance() = AuthFragment()
    }

}
