package com.nakhmadov.topworldmusic.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.nakhmadov.topworldmusic.R
import kotlin.random.Random

@BindingAdapter("imageWithUrl")
fun ImageView.imageWithUrl(imageUrl: String?) {

    if (imageUrl != null) {
        Glide
            .with(this)
            .load(imageUrl)
            .centerInside()
            .placeholder(R.drawable.music_ic)
            .error(R.drawable.music_ic)
            .into(this)
    } else
        this.setImageResource(R.drawable.music_ic)
}

@BindingAdapter("randomBackground")
fun View.randomBackground(bound: Int) {

    val random = Random.nextInt(0, bound)
    var backgroundColor = when (random) {
        1 -> context.resources.getColor(R.color.random1)
        2 -> context.resources.getColor(R.color.random2)
        3 -> context.resources.getColor(R.color.random3)
        4 -> context.resources.getColor(R.color.random4)
        5 -> context.resources.getColor(R.color.random5)
        6 -> context.resources.getColor(R.color.random6)
        else -> context.resources.getColor(R.color.random5)
    }
    this.setBackgroundColor(backgroundColor)

}

@BindingAdapter("setText")
fun TextView.setText(text: String?) {

    if (text == null) {
        this.text = " "
    } else {
        this.text = text
    }
}