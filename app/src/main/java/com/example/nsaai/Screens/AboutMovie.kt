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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.nsaai.Navigation.Screens
import com.example.nsaai.ViewModels.AboutMovieViewModel
import com.example.nsaai.ViewModels.MovieViewModel




@Composable
fun AboutMovie(modifier: Modifier = Modifier,
               viewmodel: AboutMovieViewModel,
               viewModel: MovieViewModel,
               id:Int

) {

    Log.d("AM id passed","$id")
    viewModel.fetchExternalIds(id)

    val externalid_fetched= viewModel.externalId.value
    Log.d("AM externalid fetched","$externalid_fetched")

    viewmodel.fetchAboutTheMovie(externalid_fetched)
    val poster_path=viewmodel.posterPath.value

    Log.d("AM poster path","$poster_path")
//    val scrollState = rememberScrollState()

    
    Column(
        modifier= Modifier.fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(Color.Black, MaterialTheme.colors.background),
                endY = 5000f
            ))

        ,

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
        
    ) {
        Box(
            modifier= Modifier
                .fillMaxSize()
                .wrapContentSize()
                .padding(vertical = 10.dp, horizontal =30.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.TopCenter
        ){
            Image(painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/original/${poster_path}"),
                contentDescription = null)

        }
    }


}


@Preview(showBackground = true)
@Composable
private fun AboutMoviePreview() {
    AboutMovie(viewmodel= AboutMovieViewModel(), viewModel = MovieViewModel(), id = 762509)
}