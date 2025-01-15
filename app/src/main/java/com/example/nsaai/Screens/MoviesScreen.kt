package com.example.nsaai.Screens

//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import com.example.nsaai.ViewModels.MovieViewModel
//
//@Composable
//fun MovieScreen(viewModel: MovieViewModel
//    ,modifier: Modifier = Modifier) {
//
//
//        val state = viewModel.moviestate.value
//
//        if (state.loading) {
//            Text("Loading...")
//        } else if (state.error != null) {
//            Text("Error: ${state.error}")
//        } else {
//            LazyColumn {
//                items(state.list) { movie ->
//                    Text(movie.title)
//                }
//            }
//        }
//
//
//
//}

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.nsaai.ViewModels.MovieViewModel
import com.example.nsaai.data.MovieResult  // Ensure this import is correct

@Composable
fun MovieScreen(viewModel: MovieViewModel, modifier: Modifier = Modifier,navController: NavController) {

    val state = viewModel.moviestate.value

    if (state.loading) {
        Text("Loading...")
    } else if (state.error != null) {
        Text("Error: ${state.error}")
    } else {
        LazyColumn {
            items(state.list) { movie: MovieResult ->  // Explicit type hinting
                Text(text = movie.title)               // Access the title correctly
            }
        }
    }
}
