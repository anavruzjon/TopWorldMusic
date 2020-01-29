package com.nakhmadov.topworldmusic.listeners

import com.nakhmadov.topworldmusic.util.HomeListType

class HomeListClickListener(val listener: (listType: HomeListType) -> Unit) {
    fun onClick(listType: HomeListType) = listener(listType)
}