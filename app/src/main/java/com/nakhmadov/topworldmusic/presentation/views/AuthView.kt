package com.nakhmadov.topworldmusic.presentation.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface AuthView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(errorMsg: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun navigateToHomeFragment(token: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun startLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun stopLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun initWebView()

}