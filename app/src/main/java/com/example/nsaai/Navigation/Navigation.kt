//package com.example.nsaai.Navigation
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable // Make sure to import composable here
//import com.example.nsaai.Authentication.GoogleAuth
//import com.example.nsaai.Authentication.SignInViewModel
//import com.example.nsaai.Screens.LoginScreen // Correct import for LoginScreen
//import com.example.nsaai.Screens.SignInScreen
//import com.example.nsaai.Screens.SignUpScreen
//import com.example.nsaai.ViewModels.AuthViewModel
//import com.example.nsaai.ViewModels.SignViewModel
//
//@Composable
//fun Navigation(navController: NavHostController, modifier: Modifier = Modifier,viewmodel: AuthViewModel) {
//    NavHost(navController = navController, // Corrected typo here
//        startDestination = (if(viewmodel.authState.value.isAuthenticated) Screens.Home.route else Screens.LoginScreen.route)),
//        modifier = modifier
//    ) {
//
//        composable(Screens.LoginScreen.route) {
//            LoginScreen(navController=navController) // Call the LoginScreen composable
//        }
//        composable(Screens.SignUp.route) {
//            SignUpScreen(navController=navController)
//        }
//        composable(Screens.SignIn.route) {
////            val viewModel= viewModel<SignInViewModel>()
////            val state by viewModel.state.collectAsStateWithLifecycle()
//            SignInScreen(viewModel = SignViewModel(),navController=navController)
//            }
//        }
//
//    }
//
package com.example.nsaai.Navigation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nsaai.Screens.AboutMovie
import com.example.nsaai.authscreens.LoginScreen
import com.example.nsaai.authscreens.SignInScreen
import com.example.nsaai.authscreens.SignUpScreen
import com.example.nsaai.ViewModels.AuthViewModel
import com.example.nsaai.Screens.HomeScreen
import com.example.nsaai.Screens.MovieScreen
import com.example.nsaai.Screens.PopularMovieScreen
import com.example.nsaai.Screens.TrendingMovieScreen
import com.example.nsaai.Screens.UpcomingMovieScreen
import com.example.nsaai.Screens.WatchListMovie
//import com.example.nsaai.Screens.WatchListMovies
import com.example.nsaai.ViewModels.AboutMovieViewModel
import com.example.nsaai.ViewModels.GenreViewModel

import com.example.nsaai.ViewModels.MovieViewModel


@Composable
fun Navigation(
    authenticated: Boolean,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = if (authenticated) Screens.Home.route else Screens.LoginScreen.route,
        modifier = modifier
    ) {
        composable(Screens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Screens.SignUp.route) {
            SignUpScreen(viewModel = viewModel, navController = navController)
        }
        composable(Screens.SignIn.route) {
            SignInScreen(viewModel = viewModel, navController = navController)
        }
        composable(Screens.Home.route) {
            HomeScreen(viewModel = viewModel, navController = navController)
        }
        composable(Screens.Movie.route) {
            MovieScreen(viewModel=MovieViewModel(),viewmodel= GenreViewModel(),navController=navController)
        }
        composable(Screens.PopularMovie.route) {
            PopularMovieScreen(viewModel=MovieViewModel(),navController=navController)
        }
        composable(Screens.TrendingMovies.route) {
            TrendingMovieScreen(viewModel=MovieViewModel(),navController=navController)
        }
        composable(Screens.Notification.route){
            UpcomingMovieScreen(modifier=Modifier,viewModel=MovieViewModel(),navController=navController)
        }
        composable(Screens.WatchList.route) {
            WatchListMovie(viewmodel= AboutMovieViewModel(),viewModel= MovieViewModel(),navController=navController)
        }
        composable(Screens.AboutMovie.route,
            arguments= listOf(navArgument("id"){
                type= NavType.IntType
            })
            ) {backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("id") ?: 0
            Log.d("is id sent to aboutmnovie?","$movieId")

            AboutMovie(viewModel=MovieViewModel(),viewmodel= AboutMovieViewModel(),id= movieId)
        }
    }
}

