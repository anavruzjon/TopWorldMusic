package com.nakhmadov.topworldmusic.presentation.views

import com.nakhmadov.topworldmusic.data.db.models.DatabaseModel
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface HomeListDetailView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun submitItems(items: List<DatabaseModel>)
}