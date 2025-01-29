package com.example.nsaai.Screens

import android.content.res.Configuration
import android.util.Log
import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.util.lerp
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material.icons.rounded.Favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Black
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.nsaai.CastByApi.Cast
import com.example.nsaai.R

import com.example.nsaai.ViewModels.AboutMovieViewModel
import com.example.nsaai.ViewModels.MovieViewModel
//import com.example.nsaai.dataofwatchlist.WatchList
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlin.math.abs
import kotlin.math.min


@Composable
fun AboutMovie(modifier: Modifier = Modifier,
               viewmodel: AboutMovieViewModel,
               viewModel: MovieViewModel,
               id:Int

) {
//    LaunchedEffect(Unit) {
//        viewModel.fetchExternalIds(id)
//    }

    Log.d("AM id passed", "$id")
    viewModel.fetchExternalIds(id)

    val externalid_fetched = viewModel.externalId.value
    Log.d("AM externalid fetched", "$externalid_fetched")

    viewmodel.fetchAboutTheMovie(externalid_fetched)
    val poster_path = viewmodel.posterPath.value
    val image_path = viewmodel.imageofmovie.value

    val key = viewmodel.key.value
    val istrailer= viewmodel.trailerornot.value

//    Details of Movie
    viewmodel.fetchAllMovieDetails(id)
    val title= viewmodel.title.value
    val adult=viewmodel.adult.value
    val original_language=viewmodel.original_language.value
    val vote_average=viewmodel.vote_average.value
    val overview=viewmodel.overview.value
    val release_date=viewmodel.release_date.value

//    About Cast
    viewmodel.fetchCastDetails(id)
    val cast=viewmodel.cast.value

//    val content="Lorem ipsum odor amet, consectetuer adipiscing elit. Quisque malesuada vivamus morbi at; nisi faucibus. Cursus netus nulla dictumst nec lobortis mattis. Tristique leo eleifend nullam et aptent scelerisque egestas pulvinar. Nam sem purus et nibh habitant sem ac et. Nostra luctus placerat integer ultricies aptent euismod. Maximus at adipiscing aliquet magna non varius volutpat adipiscing! Ac commodo at maecenas eget egestas auctor. Nec torquent dolor urna libero euismod sagittis neque lobortis. Convallis faucibus aliquet dolor ex duis arcu. Fermentum fermentum blandit mi conubia semper condimentum etiam. Varius viverra fermentum nulla litora class. Odio sodales interdum sed inceptos integer et taciti sollicitudin. Netus torquent dis neque iaculis curabitur mattis ad elementum erat. Parturient primis curae pulvinar urna varius et id. Viverra et finibus tincidunt taciti ad hac porttitor venenatis. Bibendum congue placerat lobortis facilisi at potenti. Accumsan faucibus tincidunt vivamus erat class. Cursus quis sodales molestie natoque viverra netus habitant. Hac neque lacus primis mi nisi aliquet sodales dis iaculis. Ex iaculis tempus fermentum cursus ex et. Justo facilisi primis netus parturient, laoreet libero finibus mauris curae. Dignissim mus sapien, aliquam nostra morbi primis eu. Proin senectus ultricies sollicitudin sagittis dapibus hac. Magna sed a; lectus platea elementum mattis. Proin tortor taciti magnis velit felis rutrum risus enim. Conubia parturient iaculis aliquet est sapien curabitur. Nulla litora mattis nunc facilisis dapibus pulvinar non. Nostra natoque faucibus aliquet; pretium efficitur id. Vehicula erat conubia libero suspendisse eget sollicitudin dis. Turpis mattis fermentum natoque lectus mattis libero leo vivamus. Morbi magna eget adipiscing senectus et; per hendrerit? Id molestie taciti aliquam massa integer curabitur sollicitudin iaculis. Nam vehicula porta rhoncus curabitur lorem neque ex. Varius sapien arcu massa urna auctor. Augue mollis sociosqu pellentesque convallis nec ex blandit molestie id. Maximus at eleifend ad gravida taciti. Massa class pulvinar fames placerat libero?"
    Log.d("AM poster path", "$poster_path")
//    val scrollState = rememberScrollState()

    val pagerState = rememberPagerState(pageCount = { 3 }) // Two pages

    Box(modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        HorizontalPager(state = pagerState) { page ->

            when (page) {
                0 -> {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
//                            .background(
//                                brush = Brush.verticalGradient(
//                                    colors = listOf(Color.Black, Color.Gray),
//                                    endY = 5000f
//                                )
//                            )
                            .background(MaterialTheme.colorScheme.background)
                        ,

                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top

                    ) {



                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize()
                                .padding(vertical = 10.dp, horizontal = 30.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            val painter= rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/original/${poster_path}")
                            val painterstate= painter.state
                            Image(
                                painter = painter,
                                contentDescription = null
                            )
                            when(painterstate){
                                is AsyncImagePainter.State.Error->{
                                    Box(modifier= Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center){

                                        Column {
                                            Icon(
                                                imageVector = Icons.Rounded.BrokenImage,
                                                contentDescription = "Image not Loaded Properly",
                                                tint = MaterialTheme.colorScheme.onBackground,
                                                modifier = Modifier.size(40.dp)
                                            )
                                            Text(
                                                text = "Image not Loaded Properly",
                                                color = MaterialTheme.colorScheme.onBackground

                                            )
                                        }
                                    }

                                }
                                is AsyncImagePainter.State.Success ->{
                                    Card(
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter)
                                            .size(40.dp)
                                            .border(
                                                width = 2.dp,
                                                color = Color.Gray,
                                                shape = CircleShape
                                            )
                                            .clip(CircleShape)
                                            .padding(2.dp),
                                        elevation = 20.dp,
                                        backgroundColor = Color.White


                                    )
                                    {
                                        Image(
                                            painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500${image_path}"),
                                            contentDescription = null,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }
                                is AsyncImagePainter.State.Loading -> {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                                is AsyncImagePainter.State.Empty -> {
                                    Text(
                                        text = "No image to display",
                                        modifier = Modifier.align(Alignment.Center),
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }

                            }

                        }

                    }


                }

                1 -> {

                        AboutTheMovie(id=id,
                            title = title,
                            adult = adult,
                            original_language = original_language,
                            overview = overview,
                            release_date = release_date,
                            vote_average = vote_average,
                            poster_path = poster_path,
                            cast=cast,
//                            viewmodel=viewmodel
                        )

                    }
                2 -> {
                    if(pagerState.currentPage==2){
                        PlayYoutubeTrailer(id=id,viewmodel=viewmodel, lifeCycleOwner = LocalLifecycleOwner.current)
                    }

                }

                }
            }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val isDarkTheme = isSystemInDarkTheme() // Check if dark theme is enabled
                val color = if (isDarkTheme) {
                    if (pagerState.currentPage == iteration) Color.LightGray else Color.DarkGray
                } else {
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                }

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(12.dp)
                )
            }
        }
    }



}

@Composable
fun CircularRatingProgressBar(rating: Float) {
    // Ensure the rating is clamped between 0 and 10
    val normalizedRating = rating.coerceIn(0f, 10f)
    val progress = normalizedRating / 10f // Convert to a value between 0 and 1

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(80.dp) // Set the size of the circular progress bar
    ) {
        // Circular progress indicator
        CircularProgressIndicator(
            progress = progress,
            color = MaterialTheme.colorScheme.onBackground,
            strokeWidth = 6.dp,
            modifier = Modifier.fillMaxSize()
        )
        // Text showing the rating
        Row(
            modifier=Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.Center
        ){

            Text(
                text = String.format("%.1f/10", rating),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 12.sp
            )
            Icon(Icons.Filled.Star, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(18.dp))

        }
    }
}

@Composable
fun AboutTheMovie(
    modifier: Modifier = Modifier,
    title: String,
    adult: Boolean,
    original_language: String,
    vote_average: Double,
    overview: String,
    release_date: String,
    poster_path: String? = null,
    cast: List<Cast>,
    id: Int
) {

    FirebaseFirestore.getInstance().firestoreSettings = FirebaseFirestoreSettings.Builder()
        .setPersistenceEnabled(true)
        .build()

    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    // State to track if the movie is a favorite
    val isFavorite = remember { mutableStateOf(false) }

    // Get the current user from Firebase Authentication
    val currentUser = auth.currentUser

    // Function to create a user document if it doesn't exist
    fun createUserDocumentIfNotExists(userId: String, movieId: Long) {
        val userRef = firestore.collection("users").document(userId)

        userRef.get().addOnSuccessListener { document ->
            if (!document.exists()) {
                val userData = mapOf(
                    "favoriteMovies" to listOf(movieId) // Add the movie to the list immediately
                )
                userRef.set(userData)
                    .addOnSuccessListener {
                        Log.d("Firestore", "User document created and movie added to favorites for user ID: $userId.")
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore", "Error creating user document for user ID: $userId", e)
                    }
            } else {
                Log.d("Firestore", "User document already exists for user ID: $userId.")
                // Add the movie to the favorite list if document already exists
                userRef.update("favoriteMovies", FieldValue.arrayUnion(movieId))
                    .addOnSuccessListener {
                        Log.d("Firestore", "Movie added to favorites for user ID: $userId.")
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore", "Error adding movie to favorites for user ID: $userId", e)
                    }
            }
        }.addOnFailureListener { e ->
            Log.e("Firestore", "Error fetching user document for user ID: $userId", e)
        }
    }


    // Function to toggle the favorite movie
    fun toggleFavorite() {
        currentUser?.let { user ->
            Log.d("Firestore", "Toggling favorite for user ID: ${user.uid}, movie ID: $id")

            // Ensure the user document exists before toggling the favorite
            createUserDocumentIfNotExists(user.uid, id.toLong())

            val userRef = firestore.collection("users").document(user.uid)

            userRef.get().addOnSuccessListener { document ->
                if (document.exists()) {
                    val movieIds = document.get("favoriteMovies") as? List<Long> ?: listOf()

                    Log.d("Firestore", "Favorite movies list for user ID ${user.uid}: $movieIds")

                    if (movieIds.contains(id.toLong())) {
                        // If the movie is already in the list, remove it
                        userRef.update("favoriteMovies", FieldValue.arrayRemove(id.toLong()))
                            .addOnSuccessListener {
                                Log.d("Firestore", "Movie removed from favorites for user ID: ${user.uid}.")
                                isFavorite.value = false
                            }
                            .addOnFailureListener { e ->
                                Log.e("Firestore", "Error removing movie from favorites for user ID: ${user.uid}", e)
                            }
                    } else {
                        // If the movie is not in the list, add it
                        userRef.update("favoriteMovies", FieldValue.arrayUnion(id.toLong()))
                            .addOnSuccessListener {
                                Log.d("Firestore", "Movie added to favorites for user ID: ${user.uid}.")
                                isFavorite.value = true
                            }
                            .addOnFailureListener { e ->
                                Log.e("Firestore", "Error adding movie to favorites for user ID: ${user.uid}", e)
                            }
                    }
                } else {
                    Log.e("Firestore", "User document does not exist for user ID: ${user.uid}.")
                }
            }.addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching user document for user ID: ${user.uid}", e)
            }
        }
    }

    // Function to check if the movie is already in the favorites
    fun checkIfMovieIsInDatabase() {
        currentUser?.let { user ->
            val userRef = firestore.collection("users").document(user.uid)

            userRef.get().addOnSuccessListener { document ->
                if (document.exists()) {
                    val movieIds = document.get("favoriteMovies") as? List<Long> ?: listOf()
                    isFavorite.value = movieIds.contains(id.toLong())
                    Log.d("Firestore", "Movie ID: $id is ${if (isFavorite.value) "in" else "not in"} the favorite list for user ID: ${user.uid}.")
                } else {
                    Log.e("Firestore", "User document does not exist for user ID: ${user.uid}.")
                }
            }.addOnFailureListener { e ->
                Log.e("Firestore", "Error checking favorite movies for user ID: ${user.uid}", e)
            }
        }
    }

    // Trigger the check when the composable is first loaded
    LaunchedEffect(Unit) {
        checkIfMovieIsInDatabase()
    }

    // UI layout for the movie details
    Column(
        modifier = Modifier
            .fillMaxSize()

            .padding(horizontal = 10.dp)
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            fontFamily = Font(R.font.font).toFontFamily(),
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Row for additional movie information (e.g., adult rating, release date, language)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Adult: ",
                        fontFamily = Font(R.font.font).toFontFamily(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = if (adult) "Yes" else "No",
                        fontFamily = Font(R.font.font).toFontFamily(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Release Date: $release_date",
                    fontFamily = Font(R.font.font).toFontFamily(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Language: $original_language",
                    fontFamily = Font(R.font.font).toFontFamily(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Circular rating progress bar (replace with your own component)
            CircularRatingProgressBar(rating = vote_average.toFloat())
        }

        // Favorite Icon Button (toggles movie in favorites)
        IconButton(
            modifier = Modifier
                .padding(24.dp)
                .size(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.onBackground),
            onClick = {
                toggleFavorite()
            }
        ) {
            Icon(
                Icons.Rounded.Favorite, contentDescription = null,
                tint = if (isFavorite.value) Color.Red else MaterialTheme.colorScheme.background
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Scrollable overview text
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = overview,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = Font(R.font.font).toFontFamily()
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Cast",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = Font(R.font.montserrat).toFontFamily(),
            )

            // Display cast in a LazyRow
            LazyRow {
                items(cast.size) {
                    cast.forEach { item ->
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .height(200.dp)
                                .width(160.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .wrapContentSize(),
                            elevation = 20.dp,
                            backgroundColor = MaterialTheme.colorScheme.onBackground
                        ) {

                            Image(
                                painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500${item.profile_path}"),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 8.dp),
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                Text(
                                    text = item.name,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontFamily = Font(R.font.font).toFontFamily(),
                                    modifier = Modifier.padding(start = 8.dp),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}




//@Preview(showBackground = true)
//@Composable
//private fun AboutthemoviePreview() {
//    AboutTheMovie(title = "hello This is the title of the movie", adult = false, original_language = "English", overview = "Mufasa, a cub lost and alone, meets a sympathetic lion named Taka, the heir to a royal bloodline. The chance meeting sets in motion an expansive journey of a group of misfits searching for their destiny", release_date = "2023", vote_average = 8.0
//    , poster_path = "/jbOSUAWMGzGL1L4EaUF8K6zYFo7.jpg",
//        cast = listOf(
//            Cast(
//                adult = false,
//                gender = 2,
//                id = 1763709,
//                known_for_department = "Acting",
//                name = "Aaron Pierre",
//                original_name = "Aaron Pierre",
//                popularity = 104.416,
//                profile_path = "/hNwZWdT2KxKj1YLbipvtUhNjfAp.jpg",
//                cast_id = 73,
//                character = "Mufasa (voice)",
//                credit_id = "6784ce60bd793c03544ec5ff",
//                order = 0
//            ),
//            Cast(
//                adult = false,
//                gender = 2,
//                id = 1763709,
//                known_for_department = "Acting",
//                name = "Aaron Pierre",
//                original_name = "Aaron Pierre",
//                popularity = 104.416,
//                profile_path = "/hNwZWdT2KxKj1YLbipvtUhNjfAp.jpg",
//                cast_id = 73,
//                character = "Mufasa (voice)",
//                credit_id = "6784ce60bd793c03544ec5ff",
//                order = 0
//            ),
//            Cast(
//                adult = false,
//                gender = 2,
//                id = 1763709,
//                known_for_department = "Acting",
//                name = "Aaron Pierre",
//                original_name = "Aaron Pierre",
//                popularity = 104.416,
//                profile_path = "/hNwZWdT2KxKj1YLbipvtUhNjfAp.jpg",
//                cast_id = 73,
//                character = "Mufasa (voice)",
//                credit_id = "6784ce60bd793c03544ec5ff",
//                order = 0
//            ),
//            Cast(
//                adult = false,
//                gender = 2,
//                id = 1763709,
//                known_for_department = "Acting",
//                name = "Aaron Pierre",
//                original_name = "Aaron Pierre",
//                popularity = 104.416,
//                profile_path = "/hNwZWdT2KxKj1YLbipvtUhNjfAp.jpg",
//                cast_id = 73,
//                character = "Mufasa (voice)",
//                credit_id = "6784ce60bd793c03544ec5ff",
//                order = 0
//            ),
//            Cast(
//                adult = false,
//                gender = 2,
//                id = 1763709,
//                known_for_department = "Acting",
//                name = "Aaron Pierre",
//                original_name = "Aaron Pierre",
//                popularity = 104.416,
//                profile_path = "/hNwZWdT2KxKj1YLbipvtUhNjfAp.jpg",
//                cast_id = 73,
//                character = "Mufasa (voice)",
//                credit_id = "6784ce60bd793c03544ec5ff",
//                order = 0
//            ),
//            Cast(
//                adult = false,
//                gender = 2,
//                id = 1763709,
//                known_for_department = "Acting",
//                name = "Aaron Pierre",
//                original_name = "Aaron Pierre",
//                popularity = 104.416,
//                profile_path = "/hNwZWdT2KxKj1YLbipvtUhNjfAp.jpg",
//                cast_id = 73,
//                character = "Mufasa (voice)",
//                credit_id = "6784ce60bd793c03544ec5ff",
//                order = 0
//            )
//        ))
//
//}


@Composable
fun calculateScaleFor(index: Int, listState: androidx.compose.foundation.lazy.LazyListState): Float {
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
fun PlayYoutubeTrailer(
    modifier: Modifier = Modifier,
    id: Int,
    viewmodel: AboutMovieViewModel,
    lifeCycleOwner: LifecycleOwner
) {
    viewmodel.fetchyoutubetrailerid(id)
    val listofvideolinks = viewmodel.listofresultlinks.value

    val orientation = LocalConfiguration.current.orientation
    val isLandscape = orientation == Configuration.ORIENTATION_LANDSCAPE
    val systemUiController = rememberSystemUiController()


    LaunchedEffect(isLandscape) {
        systemUiController.isSystemBarsVisible = !isLandscape
    }


    val firstTrailer = listofvideolinks.firstOrNull { it.type == "Trailer" }


    firstTrailer?.let { video ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                modifier = if (isLandscape) {
                    Modifier.fillMaxSize()
                } else {
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                },
                factory = { context ->
                    YouTubePlayerView(context = context).apply {
                        lifeCycleOwner.lifecycle.addObserver(this)

                        addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                // Load the first trailer video
                                youTubePlayer.loadVideo(video.key, 0f)
                            }
                        })
                    }
                }
            )
        }
    }
}
