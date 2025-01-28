package com.example.nsaai.Screens


import android.media.Image
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
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
import coil.compose.rememberAsyncImagePainter
import com.example.nsaai.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun WatchListMovie(
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel,
    viewmodel: AboutMovieViewModel,
    navController: NavController
) {
    val firestore = FirebaseFirestore.getInstance()
    val movieIds = remember { mutableStateOf<List<Long>>(emptyList()) }
//    val poster_path = remember{mutableStateOf<List<String>>}
//    val title = remember{mutableStateOf<List<String>>}


    // Function to fetch movie IDs from Firestore
    fun fetchMovieIds() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid // Dynamically get the logged-in user's UID
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val fetchedMovieIds = document.get("favoriteMovies") as? List<Long> ?: emptyList()
                        movieIds.value = fetchedMovieIds

                        // Log all movie IDs fetched
                        Log.d("Firestore", "Fetched Movie IDs: $fetchedMovieIds")
                    } else {
                        Log.e("Firestore", "Document does not exist")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error fetching movie IDs", e)
                }
        } else {
            Log.e("Firestore", "No authenticated user found")
        }
    }

    // Fetch movie IDs when the composable is launched
    LaunchedEffect(Unit) {
        fetchMovieIds()
    }

    // Display a loading state if the movieIds list is empty
    if (movieIds.value.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
            .padding(8.dp),

    ) {

        items(movieIds.value) { movieId ->
            // Use aboutMovieViewModel as required

            viewModel.fetchExternalIds(movieId.toInt())
            val externalid= viewModel.externalId.value
            viewmodel.fetchAboutTheMovie(externalid)

            val posterPath = viewmodel.posterPath.value
            val title = viewmodel.title.value

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable {
                        navController.navigate("aboutmovie/${movieId.toInt()}")
                    }
                    .padding(8.dp)
            ) {
                // Display movie poster and title
                Log.d("Firestore", "Poster Path: $posterPath, title:$title")
                if (posterPath.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = "https://image.tmdb.org/t/p/w500${posterPath}"
                        ),
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                }

                Text(
                    text = title,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}




@Preview(showBackground = true)
@Composable
private fun WatchListMoviePreview() {
    WatchListMovie(viewmodel = AboutMovieViewModel(), viewModel = MovieViewModel(), navController = NavController(LocalContext.current))
}