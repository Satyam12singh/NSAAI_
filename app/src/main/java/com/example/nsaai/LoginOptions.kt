package com.example.nsaai

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

data class LoginOptions(
    val title: String,
     @DrawableRes val icon:Int
)

val signinOptions = listOf(
    LoginOptions(
        title = "Facebook",
        icon= R.drawable.ic_facebook
    ),
    LoginOptions(
        title = "Twitter",
        icon= R.drawable.ic_twitter
    ),
    LoginOptions(
        title = "Google",
        icon= R.drawable.ic_google
    ),
    LoginOptions(
        title = "Apple",
        icon= R.drawable.ic_apple
    ),

)