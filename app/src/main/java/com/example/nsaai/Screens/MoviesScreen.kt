package com.example.nsaai.Screens



import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import com.example.nsaai.R
import com.example.nsaai.ViewModels.GenreViewModel
import com.example.nsaai.ViewModels.MovieViewModel
import com.example.nsaai.datafromapi.MovieResult  // Ensure this import is correct
import java.lang.reflect.Method
import androidx.compose.foundation.layout.Box as Box


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieScreen(viewModel: MovieViewModel, modifier: Modifier = Modifier, navController: NavController,
                viewmodel: GenreViewModel
) {
    val state = viewModel.moviestate.value
    val genreState = viewmodel.genrestate.value

    Scaffold(
        topBar = {
            TopBarMovie(navController = navController)
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary.copy(0.3f))
        ) {
            if (genreState.loading) {
                Text("Loading genre...")
            } else {
                LazyColumn {  // Fixed the nesting issue here
                    items(genreState.list) { genre ->
                        Log.d("genreid", "Genre: ${genre.id}")
                        Text(
                            text = genre.name,
                            modifier = Modifier.padding(10.dp),
                            fontSize = 24.sp,
                            fontFamily = Font(R.font.font).toFontFamily()
                        )

                        // Constrain LazyRow properly with a fixed height
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                        ) {

                            items(state.list) { movie: MovieResult ->


                                Log.d("genreid", "Movie: ${movie.genre_ids}")
                                if (movie.genre_ids.contains(genre.id)) {
                                    Box(
                                        modifier = Modifier
                                            .height(100.dp)
                                            .width(200.dp)
                                            .padding(start = 10.dp, end = 10.dp)
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(MaterialTheme.colorScheme.onPrimary)

                                    ) {
                                        Image(
                                            painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500${movie.backdrop_path}"),
                                            contentDescription = null,

                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}
