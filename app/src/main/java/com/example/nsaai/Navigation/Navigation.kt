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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nsaai.authscreens.LoginScreen
import com.example.nsaai.authscreens.SignInScreen
import com.example.nsaai.authscreens.SignUpScreen
import com.example.nsaai.ViewModels.AuthViewModel
import com.example.nsaai.Screens.HomeScreen
import com.example.nsaai.Screens.MovieScreen

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
            MovieScreen(viewModel=MovieViewModel(),navController=navController)
        }
    }
}

