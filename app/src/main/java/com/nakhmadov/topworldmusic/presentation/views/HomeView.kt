package com.nakhmadov.topworldmusic.presentation.views

import com.nakhmadov.topworldmusic.presentation.models.HomeItems
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface HomeView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun startLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun stopLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun initRecycler()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun submitListToAdapter(list: List<HomeItems>)
}