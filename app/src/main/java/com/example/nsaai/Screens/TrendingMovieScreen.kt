package com.example.nsaai.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NetworkCheck
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.nsaai.R
import com.example.nsaai.TopBar.TopBarMovie

import com.example.nsaai.ViewModels.MovieViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.time.Duration.Companion.seconds

@Composable
fun TrendingMovieScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel,
    navController: NavController,
    )
{

//    LaunchedEffect(Unit) {
//        viewModel.fetchTrendingMovies()
//    }

    val trendingmoviestate = viewModel.trendmoviestate.value
    val trendingMovieList = viewModel.alltrendingmovies.value

    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val context= LocalContext.current
    if(checkInternetConnectivity(context)){
        if(!checkSlowInternet(context)) {


            Scaffold(
                topBar = {
                    TopBarMovie(navController = navController, name = "Trending Movies")
                }
            ) { paddingValues ->
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = {
                        coroutineScope.launch {
                            isRefreshing = true
                            viewModel.fetchTrendingMovies()
                            delay(3.seconds)
                            isRefreshing = false
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        if (trendingmoviestate.loading) {
                            // Center the loading indicator
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center // Center alignment
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary,
                                    strokeWidth = 4.dp
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        } else {
                            LazyColumn(
                                state = listState,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.background),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(36.dp)
                            ) {
                                itemsIndexed(trendingMovieList) { index, movie ->
                                    val scale = if (isItemCentered(index, listState)) {
                                        1.0f // Scale factor for the centered item
                                    } else {
                                        0.9f // Default scale for other items
                                    }
                                    Column(
                                        modifier = Modifier
                                            .height(150.dp)
                                            .fillMaxWidth()
                                            .scale(scale)
                                            .clip(RoundedCornerShape(30.dp))
                                            .background(MaterialTheme.colorScheme.primary.copy(0.5f)),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        val context = LocalContext.current
                                        Box(
                                            modifier = Modifier
                                                .height(150.dp)
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(20.dp))
                                                .clickable {
                                                    navController.navigate("aboutmovie/${movie.id}")
                                                }
                                                .background(Color.Black),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Image(
                                                painter = rememberAsyncImagePainter(
                                                    model = "https://image.tmdb.org/t/p/w500${movie.backdrop_path}"
                                                ),
                                                contentDescription = movie.original_title,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clip(RoundedCornerShape(20.dp))

                                            )
                                            Text(
                                                text = movie.original_title,
                                                fontWeight = FontWeight.Bold,
//                                        fontFamily = Font(R.font.montserrat).toFontFamily(),
                                                fontSize = 24.sp,
                                                color = Color.White,
                                                modifier = Modifier
                                                    .align(Alignment.BottomCenter)
                                                    .padding(8.dp)
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
        else{
            Scaffold(
                topBar = {
                    TopBarMovie(navController = navController,name="Trending Movies")
                }
            ){PaddingValues->
                Column(
                    modifier=Modifier.fillMaxSize().padding(PaddingValues)
                        .background(MaterialTheme.colorScheme.background.copy(0.5f)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        Icons.Rounded.NetworkCheck, contentDescription = "Slow Internet Connection",
                        modifier= Modifier.background(MaterialTheme.colorScheme.onBackground))
                    androidx.compose.material.Text(
                        "Slow Internet Connection",
                        fontFamily = Font(R.font.font).toFontFamily(),
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    }
    else
    {
        Scaffold(
            topBar = {
                TopBarMovie(navController = navController,name="Trending Movies")
            }
        ){PaddingValues->
            Column(
                modifier=Modifier.fillMaxSize().padding(PaddingValues)
                    .background(MaterialTheme.colorScheme.background.copy(0.5f)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    Icons.Rounded.NetworkCheck, contentDescription = "No Internet Connection",
                    modifier= Modifier.background(MaterialTheme.colorScheme.onBackground))
                androidx.compose.material.Text(
                    "No Internet Connection",
                    fontFamily = Font(R.font.font).toFontFamily(),
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }


}

