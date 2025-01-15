package com.example.nsaai.Navigation

sealed class Screens(val route: String) {

    object LoginScreen: Screens("login")
    object SignUp: Screens("signup")
    object SignIn: Screens("signin")

    object Home: Screens("home")
    object Movie: Screens("movie")
}