package com.example.nsaai.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
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
               viewModel: MovieViewModel
) {
    var externalid by remember {
        mutableStateOf("")
    }
    var poster_path by remember {
        mutableStateOf("")
    }

    externalid= viewModel.externalId.value
    viewmodel.fetchaboutthemovie(externalid)
    poster_path = viewmodel.fetchaboutthemovie(externalid).poster_path


    Column(modifier=  Modifier.fillMaxSize()
        .background(MaterialTheme.colors.onPrimary),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally

        ) {

        Box(
            modifier= Modifier.fillMaxWidth()
                .height(600.dp)

        ) {

            Image(painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/original/${poster_path}"))



        }

    }



}


@Preview(showBackground = true)
@Composable
private fun ABoutMoviePreview() {
    AboutMovie(modifier=Modifier.fillMaxSize(),viewmodel = AboutMovieViewModel(), viewModel = MovieViewModel())
}