package com.example.nsaai.Screens



import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NetworkCheck
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.nsaai.R
import com.example.nsaai.TopBar.TopBarMovie
import com.example.nsaai.ViewModels.GenreViewModel
import com.example.nsaai.ViewModels.MovieViewModel
import com.example.nsaai.datafromapi.MovieResult  // Ensure this import is correct
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.min
import kotlin.time.Duration.Companion.seconds
import androidx.compose.foundation.layout.Box as Box


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(
    viewModel: MovieViewModel, modifier: Modifier = Modifier, navController: NavController,
    viewmodel: GenreViewModel
) {

    val state = viewModel.moviestate.value
    val genreState = viewmodel.genrestate.value
    val scrollState = rememberScrollState()
    var fontifoverflow = 14.sp
    val context= LocalContext.current

    var forceUpdate by remember { mutableStateOf(false) }

    LaunchedEffect(state, genreState, forceUpdate) {
        viewmodel.fetchGenre()
        viewModel.fetchMovies()
    }
    LaunchedEffect(checkSlowInternet(context)) {
        viewmodel.fetchGenre()
        viewModel.fetchMovies()
    }

//    var forceUpdate by remember { mutableStateOf(false) }
    val isConnected = rememberConnectivityState()
    LaunchedEffect(isConnected) {
        if (isConnected) {
            viewmodel.fetchGenre()
            viewModel.fetchMovies()
            forceUpdate = !forceUpdate
        }
    }





//    val coroutineScope = rememberCoroutineScope()

    if(checkInternetConnectivity(context)){
        if (!checkSlowInternet(context)){
            Scaffold(
                topBar = {
                    TopBarMovie(navController = navController,name="Movies")
                }
            ) { paddingValues ->

                var isRefreshing by remember { mutableStateOf(false) }
                val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

                LaunchedEffect(isRefreshing) {
                    if (isRefreshing) {
                        try {
                            viewmodel.fetchGenre()
                            viewModel.fetchMovies()
                        } catch (e: Exception) {
                            Log.e("RefreshError", "Error fetching data", e)
                        } finally {
                            isRefreshing = false
                        }
                    }
                }

                SwipeRefresh (
                    state = swipeRefreshState,
                    onRefresh = { isRefreshing = true },
                    modifier = Modifier.padding(paddingValues)
                ) {
//

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (genreState.loading) {
//                Text("Loading genre...")
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary,
                                strokeWidth = 4.dp)
                        } else {


                            LazyColumn(modifier=Modifier.fillMaxSize()) {  // Fixed the nesting issue here
                                items(genreState.list) { genre ->
                                    val filteredMovie = state.list.filter { movie: MovieResult ->
                                        genre.id in movie.genre_ids

                                    }
                                    if (filteredMovie.isNotEmpty()) {
                                        Log.d("genreid", "Genre: ${genre.id}")
                                        Text(
                                            text = genre.name,
                                            modifier = Modifier.padding(10.dp),
                                            fontSize = 24.sp,
                                            fontFamily = Font(R.font.font).toFontFamily()
                                        )

                                        val lazyListState = rememberLazyListState()

                                        Spacer(modifier = Modifier.height(10.dp))
                                        // Constrain LazyRow properly with a fixed height
                                        LazyRow(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(170.dp),
                                            contentPadding = PaddingValues(20.dp),
                                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                                            state = lazyListState
                                        ) {

                                            itemsIndexed(filteredMovie) { index, movie: MovieResult ->

                                                val scale = calculateScale(index, lazyListState)

                                                Log.d("genreid", "Movie: ${movie.genre_ids}")
//                                    if (movie.genre_ids.contains(genre.id)) {
                                                Box(
                                                    modifier = Modifier
                                                        .height(150.dp)
                                                        .width(200.dp)
                                                        .graphicsLayer {
                                                            scaleX = scale
                                                            scaleY = scale
                                                        }
                                                        .clickable {
//                                                    Toast.makeText(context,"${movie.original_title} is clicked",Toast.LENGTH_SHORT).show()
//                                                    viewModel.fetchExternalIds(movie.id)
                                                            Log.d("this is sent to aboutmnovie","${movie.id}")
                                                            navController.navigate("aboutmovie/${movie.id}")
//                                                    navController.navigate("aboutmovie")

//                                                    navController.navigate("externalids/${movie.id}")
                                                        }
                                                    ,


                                                    ) {
                                                    Column {
                                                        Box(
                                                            modifier = Modifier
                                                                .height(100.dp)
                                                                .width(250.dp)
                                                                .padding(start = 10.dp, end = 10.dp)
                                                                .clip(RoundedCornerShape(20.dp))
                                                                .background(MaterialTheme.colorScheme.onPrimary),
                                                            contentAlignment = Alignment.Center

                                                        ) {
                                                            Image(
                                                                painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500${movie.backdrop_path}"),
                                                                contentDescription = null,

                                                                )
                                                            Text(
                                                                "(${movie.original_language})",
                                                                fontSize = 10.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                fontFamily = Font(R.font.poppins).toFontFamily(),
                                                                color = Color.White,
                                                                modifier = Modifier
                                                                    .align(Alignment.BottomCenter)
                                                                    .padding(bottom = 3.dp),
                                                            )
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .padding(horizontal = 5.dp),
                                                            verticalAlignment = Alignment.Bottom,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            var fontSize by remember { mutableStateOf(16.sp) }

                                                            Log.d(
                                                                "MovieTitle",
                                                                "Title: ${movie.original_title}"
                                                            )
                                                            Text(
                                                                "${movie.original_title}",
                                                                fontSize = fontSize,
                                                                maxLines = 1,
                                                                overflow = TextOverflow.Ellipsis,
                                                                onTextLayout = { textLayoutResult ->
                                                                    if (textLayoutResult.hasVisualOverflow) {
                                                                        fontSize =
                                                                            10.sp
                                                                    }
                                                                },
                                                                fontWeight = FontWeight.Bold,
                                                                fontFamily = Font(R.font.poppins).toFontFamily(),
                                                                color = MaterialTheme.colorScheme.onBackground,
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
                }

            }
        }else{
            Scaffold(
                topBar = {
                    TopBarMovie(navController = navController,name="Movies")
                }
            ){PaddingValues->

                var isRefreshing by remember { mutableStateOf(false) }
                val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

                LaunchedEffect(isRefreshing) {
                    if (isRefreshing) {
                        try {
                            if(checkInternetConnectivity(context)){
                            viewmodel.fetchGenre()
                            viewModel.fetchMovies()
                                }
                        } catch (e: Exception) {
                            Log.e("RefreshError", "Error fetching data", e)
                        } finally {
                            isRefreshing = false
                        }
                    }
                }

                SwipeRefresh (
                    state = swipeRefreshState,
                    onRefresh = { isRefreshing = true },
                    modifier = Modifier.padding(PaddingValues)
                ){
                    Column(
                        modifier=Modifier.fillMaxSize()
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

    }
    else {
        Scaffold(
            topBar = {
                TopBarMovie(navController = navController, name = "Movies")
            }
        ) { paddingValues ->
            var isRefreshing by remember { mutableStateOf(false) }
            val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

            LaunchedEffect(isRefreshing) {
                if (isRefreshing) {
                    try {
                        // Check if internet is now available
                        if (checkInternetConnectivity(context)) {
                            viewmodel.fetchGenre()
                            viewModel.fetchMovies()
                        }
                    } catch (e: Exception) {
                        Log.e("RefreshError", "Error fetching data", e)
                    } finally {
                        isRefreshing = false
                    }
                }
            }

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { isRefreshing = true },
                modifier = Modifier.padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize()
                                .padding(paddingValues)
                                .background(MaterialTheme.colorScheme.background.copy(0.5f)),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                Icons.Rounded.NetworkCheck,
                                contentDescription = "No Internet Connection",
                                modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
                            )
                            Text(
                                "No Internet Connection",
                                fontFamily = Font(R.font.font).toFontFamily(),
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Pull to refresh",
                                modifier = Modifier.padding(top = 10.dp),
                                fontFamily = Font(R.font.font).toFontFamily(),
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
        }
    }

}



@Composable
fun calculateScale(index: Int, listState: androidx.compose.foundation.lazy.LazyListState): Float {
    // Get the visible items and the center of the viewport
    val visibleItemsInfo = listState.layoutInfo.visibleItemsInfo
    val viewportCenter = (listState.layoutInfo.viewportEndOffset + listState.layoutInfo.viewportStartOffset) / 2

    // Find the item info for the given index
    val itemInfo = visibleItemsInfo.find { it.index == index }

    return if (itemInfo != null) {
        // Calculate the distance from the center of the viewport
        val itemCenter = itemInfo.offset + (itemInfo.size / 2)
        val distanceFromCenter = abs(itemCenter - viewportCenter)

        // Calculate the scale based on the distance
        1.0f + (0.5f * (1.0f - min(1f, distanceFromCenter / 500f)))
    } else {
        1.0f // Default scale if the item is not visible
    }
}

@Composable
fun checkSlowInternet(context:Context):Boolean {
    val context = context
    val connectivityManager = context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val capabilities = connectivityManager.getNetworkCapabilities(network)

    return (capabilities?.linkDownstreamBandwidthKbps ?: 0) < 1000 // Example threshold: 1 Mbps

}

@Composable
fun rememberConnectivityState(): Boolean {
    val context = LocalContext.current
    val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
    val isConnected = remember { mutableStateOf(checkInternetConnectivity(context)) }

    LaunchedEffect(Unit) {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                isConnected.value = true
            }

            override fun onLost(network: android.net.Network) {
                isConnected.value = false
            }
        }
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    return isConnected.value
}

