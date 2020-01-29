package com.nakhmadov.topworldmusic.presentation.presenters

import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.nakhmadov.topworldmusic.data.repositories.AuthRepository
import com.nakhmadov.topworldmusic.presentation.views.AuthView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class AuthPresenter(private val authRepository: AuthRepository) : MvpPresenter<AuthView>() {

    private val job = Job()
    private val presenterScope = CoroutineScope(Dispatchers.Main + job)

    val webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(v: WebView?, r: WebResourceRequest?): Boolean {

            val url = r?.url.toString()
            Log.i("myLogs", "Accepted URL: $url")
            if (url.startsWith("https://nakhmadov.com/?code=")) {
                val code = url.split("=")[1]

                presenterScope.launch {
                    authRepository.getTokenNetwork(code)
                    getToken()
                }
                Log.i("myLogs", "ACCEPTED URL STARTS WITH REDIRECT URL")

                return true
            }

            return false

        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            viewState.startLoading()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            viewState.stopLoading()
        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onReceivedError(v: WebView?, r: WebResourceRequest?, e: WebResourceError?) {
            super.onReceivedError(v, r, e)
            Log.i("myLogs", "WEBVIEW ERROR: ${e?.description}")
            viewState.showError(e.toString())
        }
    }

    override fun attachView(view: AuthView?) {
        super.attachView(view)
        viewState.startLoading()
        viewState.initWebView()
    }

    private fun getToken() {
        presenterScope.launch {
            val token = authRepository.getToken()
            viewState.stopLoading()
            if (token == null) {
                viewState.showError("ERROR: NO TOKEN")
            } else
                viewState.navigateToHomeFragment(token)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}