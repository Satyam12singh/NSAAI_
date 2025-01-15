package com.example.nsaai.Screens



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.nsaai.ViewModels.GenreViewModel
import com.example.nsaai.ViewModels.MovieViewModel
import com.example.nsaai.datafromapi.MovieResult  // Ensure this import is correct

@Composable
fun MovieScreen(viewModel: MovieViewModel, modifier: Modifier = Modifier,navController: NavController,
                viewmodel: GenreViewModel
) {

    val state = viewModel.moviestate.value
    val genreState=viewmodel.genrestate.value
//
//    if (state.loading) {
//        Text("Loading...")
//    } else if (state.error != null) {
//        Text("Error: ${state.error}")
//    } else {
//        LazyColumn {
//            items(state.list) { movie: MovieResult ->  // Explicit type hinting
//                Text(text = movie.title)               // Access the title correctly
//            }
//        }
//    }

    Column(modifier= Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background.copy(0.5f))){

        Box(modifier= Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(MaterialTheme.colorScheme.primary.copy(0.5f))
        ) {
            if (state.loading){
                Text("Loading..")
            }
            else{
                LazyColumn {
                    items(state.list){ movie:MovieResult ->
                        Text(text=movie.title)

                    }
                }
            }
        }
        Box(modifier= Modifier
            .fillMaxWidth()
            .weight(1f)){
            if (genreState.loading){
                Text("Loading..")
            }else{
                LazyColumn {
                    items(genreState.list){ genre ->
                        Text(text=genre.name)
                    }
                }
            }
        }

    }
}
