package com.example.nsaai.Screens


import android.content.Context
import android.media.Image
import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.nsaai.ViewModels.AboutMovieViewModel
import com.example.nsaai.ViewModels.MovieViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NetworkCheck
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.F
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import coil.compose.rememberAsyncImagePainter
import com.example.nsaai.R
import com.example.nsaai.TopBar.TopBarMovie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


//@Composable
//fun WatchListMovie(
//    modifier: Modifier = Modifier,
//    viewModel: MovieViewModel,
//    viewmodel: AboutMovieViewModel,
//    navController: NavController
//) {
//    val firestore = FirebaseFirestore.getInstance()
//    val movieIds = remember { mutableStateOf<List<Long>>(emptyList()) }
//    var isLoading by remember { mutableStateOf(true) }
//
//    data class MovieInfo(
//        val id: Long,
//        val posterPath: String,
//        val title: String
//    )
//
//    val movieDetailsMap = remember { mutableStateMapOf<Long, MovieInfo>() }
//
//    // Function to fetch movie IDs from Firestore
//    fun fetchMovieIds() {
//        val currentUser = FirebaseAuth.getInstance().currentUser
//        if (currentUser != null) {
//            val userId = currentUser.uid
//            firestore.collection("users").document(userId).get()
//                .addOnSuccessListener { document ->
//                    if (document.exists()) {
//                        val fetchedMovieIds = document.get("favoriteMovies") as? List<Long> ?: emptyList()
//                        movieIds.value = fetchedMovieIds
//                        Log.d("Firestore", "Fetched Movie IDs: $fetchedMovieIds")
//                    } else {
//                        Log.e("Firestore", "Document does not exist")
//                    }
//                    isLoading = false
//                }
//                .addOnFailureListener { e ->
//                    Log.e("Firestore", "Error fetching movie IDs", e)
//                    isLoading = false
//                }
//        } else {
//            Log.e("Firestore", "No authenticated user found")
//            isLoading = false
//        }
//    }
//
//    // Function to fetch a single movie's details
//    suspend fun fetchMovieDetails(movieId: Long) {
//        try {
//            // Reset viewModel states before fetching new movie
//            viewModel.externalId.value = ""
//            viewmodel.posterPath.value = ""
//            viewmodel.title.value = ""
//
//            // First API call
//            viewModel.fetchExternalIds(movieId.toInt())
//            delay(1000) // Wait for external ID
//
//            val externalId = viewModel.externalId.value
//            if (externalId.isNotEmpty()) {
//                // Second API call
//                viewmodel.fetchAboutTheMovie(externalId)
//                delay(1000) // Wait for movie details
//
//                val posterPath = viewmodel.posterPath.value
//                val title = viewmodel.title.value
//
//                if (posterPath.isNotEmpty() && title.isNotEmpty()) {
//                    val movieInfo = MovieInfo(
//                        id = movieId,
//                        posterPath = posterPath,
//                        title = title
//                    )
//                    movieDetailsMap[movieId] = movieInfo
//                    Log.d("MovieLoading", "Successfully loaded movie: $title")
//                }
//            }
//        } catch (e: Exception) {
//            Log.e("MovieLoading", "Error loading movie ID: $movieId", e)
//        }
//    }
//
//    // Initial fetch of movie IDs
//    LaunchedEffect(Unit) {
//        fetchMovieIds()
//    }
//
//    // Fetch movie details sequentially
//    LaunchedEffect(movieIds.value) {
//        movieIds.value.forEach { movieId ->
//            if (!movieDetailsMap.containsKey(movieId)) {
//                fetchMovieDetails(movieId)
//                delay(500) // Add delay between movies
//            }
//        }
//    }
//
//    if (isLoading) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            CircularProgressIndicator()
//        }
//        return
//    }
//
//    if (movieIds.value.isEmpty()) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("No Movies In Watch List")
//        }
//        return
//    }
//
//    LazyColumn(
//        modifier = modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
//            .padding(8.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        items(
//            items = movieIds.value,
//            key = { it }
//        ) { movieId ->
//            val movieInfo = movieDetailsMap[movieId]
//
//            Box(
//                modifier = Modifier
//                    .width(300.dp)
//                    .height(150.dp)
//                    .clip(RoundedCornerShape(10.dp))
//                    .background(MaterialTheme.colorScheme.surface)
//                    .clickable {
//                        navController.navigate("aboutmovie/${movieId.toInt()}")
//                    }
//            ) {
//                if (movieInfo == null) {
//                    CircularProgressIndicator(
//                        modifier = Modifier
//                            .size(50.dp)
//                            .align(Alignment.Center)
//                    )
//                } else {
//                    Image(
//                        painter = rememberAsyncImagePainter(
//                            model = "https://image.tmdb.org/t/p/w500${movieInfo.posterPath}"
//                        ),
//                        contentDescription = movieInfo.title,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier.fillMaxSize()
//                    )
//
//                    Text(
//                        text = movieInfo.title,
//                        modifier = Modifier
//                            .align(Alignment.BottomCenter)
//                            .padding(bottom = 8.dp),
//                        color = Color.White,
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Bold,
//                        fontFamily = Font(R.font.font).toFontFamily()
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.height(10.dp))
//        }
//    }
//}

@Composable
fun WatchListMovie(
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel,
    viewmodel: AboutMovieViewModel,
    navController: NavController
) {
    val firestore = FirebaseFirestore.getInstance()
    val movieIds = remember { mutableStateOf<List<Long>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Create a data class to hold movie information
    data class MovieInfo(
        val id: Long,
        val posterPath: String,
        val title: String
    )

    // State to hold all movie details
    val movieDetailsMap = remember { mutableStateMapOf<Long, MovieInfo>() }

    // Function to fetch movie IDs from Firestore
    fun fetchMovieIds() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val fetchedMovieIds = document.get("favoriteMovies") as? List<Long> ?: emptyList()
                        movieIds.value = fetchedMovieIds
                        Log.d("Firestore", "Fetched Movie IDs: $fetchedMovieIds")
                    } else {
                        Log.e("Firestore", "Document does not exist")
                    }
                    isLoading = false
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error fetching movie IDs", e)
                    isLoading = false
                }
        } else {
            Log.e("Firestore", "No authenticated user found")
            isLoading = false
        }
    }

    val context= LocalContext.current
    // Initial fetch of movie IDs
    LaunchedEffect(Unit) {
        checkInternetConnectivity(context)
        fetchMovieIds()
    }
    val isLoading = remember { mutableStateOf(true) }


    // Fetch details for each movie
    LaunchedEffect(movieIds.value) {
        movieIds.value.forEach { movieId ->
            if (!movieDetailsMap.containsKey(movieId)) {
                viewModel.fetchExternalIds(movieId.toInt())
                delay(1500)
                val externalId = viewModel.externalId.value
                viewmodel.fetchAboutTheMovie(externalId){
                    isLoading=false
                }
                delay(1500)

                val movieInfo = MovieInfo(
                    id = movieId,
                    posterPath = viewmodel.imageofmovie.value,
                    title = viewmodel.title.value
                )
                movieDetailsMap[movieId] = movieInfo
            }
        }
    }
//    LaunchedEffect(movieIds.value) {
//        coroutineScope { // Use coroutineScope to launch coroutines within the LaunchedEffect
//            movieIds.value.forEach { movieId ->
//                if (!movieDetailsMap.containsKey(movieId)) {
//                    launch { // Launch a new coroutine for each movie ID
//                        try {
//                            val success = viewModel.fetchExternalIds(movieId.toInt())
//                            if (success) {
//                                val success2 = viewmodel.fetchAboutTheMovie(viewModel.externalId.value)
//                                if (success2) {
//                                    val movieInfo = MovieInfo(
//                                        id = movieId,
//                                        posterPath = viewmodel.imageofmovie.value,
//                                        title = viewmodel.title.value
//                                    )
//                                    movieDetailsMap[movieId] = movieInfo
//                                } else {
//                                    Log.e("fetchAboutTheMovie", "Failed for movie $movieId")
//                                }
//                            } else {
//                                Log.e("fetchExternalIds", "Failed for movie $movieId")
//                            }
//                        } catch (e: Exception) { // Handle exceptions within the coroutine
//                            Log.e("LaunchedEffect", "Error processing movie $movieId: ${e.message}")
//                        }
//                    }
//                }
//            }
//        }
//    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    if (movieIds.value.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No Movies In Watch List",
                color = MaterialTheme.colorScheme.onPrimary)
        }
        return
    }





    if(checkInternetConnectivity(context) )
    {

        Scaffold(
            topBar = {
                TopBarMovie(navController = navController,name="Favourite Movies")
            }
        )
        { paddingvalues ->
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingvalues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    items = movieIds.value,
                    key = { it } // Use movieId as key for stable item identity
                ) { movieId ->
                    val movieInfo = movieDetailsMap[movieId]

                    Box(
                        modifier = Modifier
                            .width(350.dp)
                            .height(150.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .clickable {
                                navController.navigate("aboutmovie/${movieId.toInt()}")
                            }
                    ) {
                        if (movieInfo == null) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(50.dp)
                                    .align(Alignment.Center)
                            )
                        } else {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = "https://image.tmdb.org/t/p/w500${movieInfo.posterPath}"
                                ),
                                contentDescription = movieInfo.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            Text(
                                text = movieInfo.title,
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 8.dp),
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
    else{

        Scaffold(
            topBar = {
                TopBarMovie(navController = navController,name="Favourite Movies")
            }
        )
        { PaddingValues->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(PaddingValues)
                    .background(MaterialTheme.colorScheme.background.copy(0.5f)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    Icons.Rounded.NetworkCheck, contentDescription = "No Internet Connection",
                    modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
                )
                Text(
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



@Preview(showBackground = true)
@Composable
private fun WatchListMoviePreview() {
    WatchListMovie(viewmodel = AboutMovieViewModel(), viewModel = MovieViewModel(), navController = NavController(LocalContext.current))
}

fun checkInternetConnectivity(context:Context):Boolean{
    val context= context
    val connectivityManager= context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
    val network = connectivityManager.activeNetworkInfo

    val isConnected = when{
        network!=null && network.isConnected -> {
            return true
        }

        else -> {
            return false
        }

    }

}
