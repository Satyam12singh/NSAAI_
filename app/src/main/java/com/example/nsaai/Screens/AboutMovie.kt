package com.example.nsaai.Screens

import android.util.Log
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight.Companion.Black
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.nsaai.CastByApi.Cast
import com.example.nsaai.R

import com.example.nsaai.ViewModels.AboutMovieViewModel
import com.example.nsaai.ViewModels.MovieViewModel
import kotlin.math.abs
import kotlin.math.min


@Composable
fun AboutMovie(modifier: Modifier = Modifier,
               viewmodel: AboutMovieViewModel,
               viewModel: MovieViewModel,
               id:Int

) {

    Log.d("AM id passed", "$id")
    viewModel.fetchExternalIds(id)

    val externalid_fetched = viewModel.externalId.value
    Log.d("AM externalid fetched", "$externalid_fetched")

    viewmodel.fetchAboutTheMovie(externalid_fetched)
    val poster_path = viewmodel.posterPath.value
    val image_path = viewmodel.imageofmovie.value

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

    val pagerState = rememberPagerState(pageCount = { 2 }) // Two pages

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
                            Image(
                                painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/original/${poster_path}"),
                                contentDescription = null
                            )

                            Card(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .size(40.dp)
                                    .border(width = 2.dp, color = Color.Gray, shape = CircleShape)
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

                    }


                }

                1 -> {

                        AboutTheMovie(title = title, adult = adult, original_language = original_language, overview = overview, release_date = release_date, vote_average = vote_average, poster_path = poster_path,cast=cast)

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
fun AboutTheMovie(modifier: Modifier = Modifier,
                   title:String,
                   adult:Boolean,
                   original_language:String,
                   vote_average:Double,
                   overview:String,
                   release_date:String,
                  poster_path:String?=null,
                    cast:List<Cast>
                  )
{
    Column(
        modifier=Modifier.fillMaxSize()
            .padding(horizontal = 10.dp)
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Text("${title}",
            modifier= Modifier.fillMaxWidth()
                .padding(8.dp),
            fontFamily = Font(R.font.font).toFontFamily(),
            fontSize = 35.sp,
            fontWeight = Bold,
            maxLines = 2,
            color = MaterialTheme.colorScheme.onBackground

        )
        Spacer(modifier=Modifier.height(60.dp))
        Row(
            modifier=Modifier.fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Adult: ",
                        modifier= Modifier
                            .padding(start = 8.dp),
                        fontFamily = Font(R.font.font).toFontFamily(),
                        fontSize = 24.sp,
                        fontWeight = Bold,
                        maxLines = 2,
                        color = MaterialTheme.colorScheme.onBackground

                    )
                    if(adult==true){
                        Text("Yes",
                            fontFamily = Font(R.font.font).toFontFamily(),
                            fontSize = 24.sp,
                            fontWeight = Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }else{
                        Text("No",
                            fontFamily = Font(R.font.font).toFontFamily(),
                            fontSize = 24.sp,
                            fontWeight = Bold,
                            color = MaterialTheme.colorScheme.onBackground)

                    }
                }
                Spacer(modifier=Modifier.height(8.dp))
                Text("Release Date: $release_date",
                    modifier=Modifier.padding(start = 8.dp),
                    fontFamily = Font(R.font.font).toFontFamily(),
                    fontWeight = Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground
                    )
                Spacer(modifier=Modifier.height(8.dp))
                Text("Language: $original_language",
                    modifier=Modifier.padding(start = 8.dp),
                    fontFamily = Font(R.font.font).toFontFamily(),
                    fontWeight = Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

            }

            CircularRatingProgressBar(rating = vote_average.toFloat())



        }

        Spacer(modifier=Modifier.height(60.dp))
        val scrollState = rememberScrollState()
        Column(modifier= Modifier.verticalScroll(scrollState)
        ) {
            Text(
            "${overview}",
            fontSize = 28.sp,
            fontWeight = Bold,
                color = MaterialTheme.colorScheme.onBackground,
            fontFamily = Font(R.font.font).toFontFamily(),

        )
            Spacer(modifier=Modifier.height(40.dp))
            Text(
                "Cast"
                ,
                fontSize = 30.sp,
                fontWeight = Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = Font(R.font.montserrat).toFontFamily(),
            )
            LazyRow {
                items(cast.size) {
                    cast.forEach {item ->
                        Card(
                            modifier=Modifier
                                .padding(8.dp)
                                .height(200.dp)
                                .width(160.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .wrapContentSize(),
                            elevation = 20.dp
                            ,
                            backgroundColor = MaterialTheme.colorScheme.onBackground
                        ) {

                            Image(
                                painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500${item.profile_path}"),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                            Column(
                                modifier=Modifier.fillMaxSize().padding(bottom = 8.dp),
                                verticalArrangement = Arrangement.Bottom,

                                ) {
                                Text(
                                    "${item.name}",
                                    fontSize = 24.sp,
                                    fontWeight = Bold,
                                    color = Color.White,
                                    fontFamily = Font(R.font.font).toFontFamily(),
                                    modifier=Modifier.padding(start = 8.dp),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )


                            }

                        }
                    }
                }
            }
            Spacer(modifier=Modifier.height(50.dp))
        }



    }

}



@Preview(showBackground = true)
@Composable
private fun AboutthemoviePreview() {
    AboutTheMovie(title = "hello This is the title of the movie", adult = false, original_language = "English", overview = "Mufasa, a cub lost and alone, meets a sympathetic lion named Taka, the heir to a royal bloodline. The chance meeting sets in motion an expansive journey of a group of misfits searching for their destiny", release_date = "2023", vote_average = 8.0
    , poster_path = "/jbOSUAWMGzGL1L4EaUF8K6zYFo7.jpg",
        cast = listOf(
            Cast(
                adult = false,
                gender = 2,
                id = 1763709,
                known_for_department = "Acting",
                name = "Aaron Pierre",
                original_name = "Aaron Pierre",
                popularity = 104.416,
                profile_path = "/hNwZWdT2KxKj1YLbipvtUhNjfAp.jpg",
                cast_id = 73,
                character = "Mufasa (voice)",
                credit_id = "6784ce60bd793c03544ec5ff",
                order = 0
            ),
            Cast(
                adult = false,
                gender = 2,
                id = 1763709,
                known_for_department = "Acting",
                name = "Aaron Pierre",
                original_name = "Aaron Pierre",
                popularity = 104.416,
                profile_path = "/hNwZWdT2KxKj1YLbipvtUhNjfAp.jpg",
                cast_id = 73,
                character = "Mufasa (voice)",
                credit_id = "6784ce60bd793c03544ec5ff",
                order = 0
            ),
            Cast(
                adult = false,
                gender = 2,
                id = 1763709,
                known_for_department = "Acting",
                name = "Aaron Pierre",
                original_name = "Aaron Pierre",
                popularity = 104.416,
                profile_path = "/hNwZWdT2KxKj1YLbipvtUhNjfAp.jpg",
                cast_id = 73,
                character = "Mufasa (voice)",
                credit_id = "6784ce60bd793c03544ec5ff",
                order = 0
            ),
            Cast(
                adult = false,
                gender = 2,
                id = 1763709,
                known_for_department = "Acting",
                name = "Aaron Pierre",
                original_name = "Aaron Pierre",
                popularity = 104.416,
                profile_path = "/hNwZWdT2KxKj1YLbipvtUhNjfAp.jpg",
                cast_id = 73,
                character = "Mufasa (voice)",
                credit_id = "6784ce60bd793c03544ec5ff",
                order = 0
            ),
            Cast(
                adult = false,
                gender = 2,
                id = 1763709,
                known_for_department = "Acting",
                name = "Aaron Pierre",
                original_name = "Aaron Pierre",
                popularity = 104.416,
                profile_path = "/hNwZWdT2KxKj1YLbipvtUhNjfAp.jpg",
                cast_id = 73,
                character = "Mufasa (voice)",
                credit_id = "6784ce60bd793c03544ec5ff",
                order = 0
            ),
            Cast(
                adult = false,
                gender = 2,
                id = 1763709,
                known_for_department = "Acting",
                name = "Aaron Pierre",
                original_name = "Aaron Pierre",
                popularity = 104.416,
                profile_path = "/hNwZWdT2KxKj1YLbipvtUhNjfAp.jpg",
                cast_id = 73,
                character = "Mufasa (voice)",
                credit_id = "6784ce60bd793c03544ec5ff",
                order = 0
            )
        ))

}


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