package com.example.nsaai.HomeScreenContent

import android.media.Image
import androidx.annotation.DrawableRes
import com.example.nsaai.R

data class Types(
    val name: String,
    @DrawableRes val image: Int
)

val homescreenitems= listOf(
    Types("Movies", R.drawable.img_2),
    Types("Popular",R.drawable.img),
    Types("Trending",R.drawable.img_1),
    Types("Tv Series",R.drawable.img_3)


)