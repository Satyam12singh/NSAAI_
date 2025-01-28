package com.example.nsaai.Navigation

sealed class Screens(val route: String) {

    object LoginScreen: Screens("login")
    object SignUp: Screens("signup")
    object SignIn: Screens("signin")

    object Home: Screens("home")
    object Movie: Screens("movie")
    object AboutMovie: Screens("aboutmovie/{id}")
    object PopularMovie:Screens("popularmovie")
    object TrendingMovies:Screens("trendingmovie")
    object Notification:Screens("notification")
    object Favourite:Screens("favourite")

}