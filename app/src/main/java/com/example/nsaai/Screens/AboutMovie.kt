package com.example.nsaai.Screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.nsaai.ViewModels.AboutMovieViewModel
import com.example.nsaai.ViewModels.MovieViewModel




@Composable
fun AboutMovie(modifier: Modifier = Modifier,
               viewmodel: AboutMovieViewModel,
               viewModel: MovieViewModel,
               id:Int

) {
    val externalid = viewModel.externalId.value
    Log.d("aboutmnovie_exid1", "Fetching external ID for movie ID: $externalid")

    var poster_path by remember { mutableStateOf("") }

    LaunchedEffect(id) {
        viewModel.fetchExternalIds(id)
        Log.d("aboutmnovie_externalid", "Fetching external ID for movie ID: $externalid")

    }

    LaunchedEffect(externalid) {
        if (externalid.isNotEmpty()) {
            viewmodel.fetchAboutTheMovie(externalid)
            poster_path = viewmodel.posterPath.value
            Log.d("aboutmnovie_posterpath", "Fetching poster path for movie ID: $poster_path")

        }
    }




    Column(modifier=  Modifier.fillMaxSize()
        .background(MaterialTheme.colors.onPrimary),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally

        ) {

        Box(
            modifier= Modifier.fillMaxWidth()
                .height(600.dp)
                .background(MaterialTheme.colors.onBackground)

        ) {

//            Image(painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/original/oHPoF0Gzu8xwK4CtdXDaWdcuZxZ.jpg"), contentDescription = null)
            Image(painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/original/${poster_path}"), contentDescription = null)



        }

    }



}


//@Preview(showBackground = true)
//@Composable
//private fun ABoutMoviePreview() {
//    val mockAboutMovieViewModel = AboutMovieViewModel().apply {
//        posterPath.value = "/jbOSUAWMGzGL1L4EaUF8K6zYFo7.jpg" // Replace with a valid TMDB poster path for preview
//    }
//
//    val mockMovieViewModel = MovieViewModel().apply {
//        externalId.value = "tt13186482" // Mock external ID
//    }
//    AboutMovie(modifier=Modifier.fillMaxSize(),viewmodel = AboutMovieViewModel(), id=762509,viewModel = MovieViewModel())
//}