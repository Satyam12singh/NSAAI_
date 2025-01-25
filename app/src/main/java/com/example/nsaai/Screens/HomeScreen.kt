package com.example.nsaai.Screens


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.nsaai.HomeScreenContent.Types
import com.example.nsaai.HomeScreenContent.homescreenitems
import com.example.nsaai.Navigation.Screens
import com.example.nsaai.R
import com.example.nsaai.ViewModels.AuthViewModel
import com.google.android.play.integrity.internal.n
import kotlinx.coroutines.launch



@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    navController: NavController,
//    viewmodel: MovieViewModel
) {
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }



    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(viewModel = viewModel, navController = navController)
        }
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    searchText = searchText,
                    onSearchTextChanged = { searchText = it },
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }
        ) { paddingValues ->
            LazyColumn(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)),
                contentPadding = PaddingValues(16.dp)
            ) {
                itemsIndexed(homescreenitems) { _, item ->
                    ColumnItem(item = item,navController)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}


@Composable
fun ColumnItem(item: Types,navController: NavController) {
    Column(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp)) // Apply rounded corners to the Column
            .background(MaterialTheme.colorScheme.primary.copy(0.5f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context= LocalContext.current
        Box(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)) // Ensure the Box has rounded corners
                .clickable {
                    if (item.name=="Movies"){
                        navController.navigate(Screens.Movie.route)
                    }else if (item.name=="Popular"){
                        navController.navigate(Screens.PopularMovie.route)
                    }else if(item.name=="Trending"){
                        navController.navigate(Screens.TrendingMovies.route)
                    }
                    else{

                        Toast.makeText(context,"Coming Soon",Toast.LENGTH_SHORT).show()
                    }
                }
                .background(Color.Black), // Optional: Set a background for clarity
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = item.image),
                contentDescription = item.name,
                contentScale = ContentScale.Crop, // Ensures the image scales properly within the bounds
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp)) // Apply clipping directly to the Image

            )
            Text(
                text = item.name,
                fontWeight = FontWeight.Bold,
                fontFamily = Font(R.font.montserrat).toFontFamily(),
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun DrawerContent(modifier: Modifier = Modifier,
                  viewModel: AuthViewModel,navController: NavController) {
    Column(
        modifier = Modifier

            .fillMaxHeight() // Occupy only height
            .width(300.dp) // Limit the drawer width
            .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            .background(MaterialTheme.colorScheme.surface.copy(0.5f))
            .padding(horizontal = 10.dp)
    ) {
        Column (modifier = Modifier.padding(vertical = 30.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ){
            Text("Just Chill!!",
                modifier= Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp)
        }


        Spacer(modifier=Modifier.height(60.dp))
        HorizontalDivider(modifier= Modifier.padding(horizontal = 15.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(0.5f))
        Spacer(modifier=Modifier.height(50.dp))
        NavigationDrawerItem(
            icon = { Icon(imageVector = Icons.Rounded.AccountCircle, contentDescription = null) },
            label = { Text("Account") },
            selected = false,
            onClick = {

            }
        )
        Spacer(modifier=Modifier.height(16.dp))
        NavigationDrawerItem(
            icon = { Icon(imageVector = Icons.Rounded.Notifications, contentDescription = null) },
            label = { Text("Notification") },
            selected = false,
            onClick = {

            }
        )
        Spacer(modifier=Modifier.height(16.dp))
        NavigationDrawerItem(
            icon = { Icon(imageVector = Icons.Rounded.Email, contentDescription = null) },
            label = { Text("InBox") },
            selected = false,
            onClick = {

            }
        )
        Spacer(modifier=Modifier.height(16.dp))
        NavigationDrawerItem(
            icon = { Icon(imageVector = Icons.Rounded.Logout, contentDescription = null) },
            label = { Text("LogOut") },
            selected = false,
            onClick = {
                viewModel.logout()
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            }
        )

    }



}


//{
//    item {
//        Box(modifier=Modifier
//            .height(150.dp)
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(30.dp))
//            .padding(horizontal = 30.dp)
//            .background(MaterialTheme.colorScheme.background)
//            .clickable {
//                navController.navigate(Screens.Movie.route)
//            }
//        ) {
//            Image(painter= painterResource(R.drawable.movies), contentDescription = null,
//                modifier=Modifier.fillMaxSize().clip(RoundedCornerShape(50.dp)))
//            Text("Movies",modifier=Modifier.padding(bottom = 8.dp).align(Alignment.BottomCenter),
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                fontFamily = Font(R.font.font).toFontFamily(),
//                color = Color.White
//
//            )
//        }
//    }
//    item {
//        Box(modifier=Modifier
//            .height(150.dp)
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(30.dp))
//            .padding(horizontal = 30.dp)
//            .background(MaterialTheme.colorScheme.background)
//            .clickable {
//                navController.navigate(Screens.Movie.route)
//            }
//        ) {
//            Image(painter= rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${firsttrendingmovie?.backdrop_path}"), contentDescription = null,
//                modifier=Modifier.fillMaxSize().clip(RoundedCornerShape(50.dp)))
//            Text("Trending Movies",modifier=Modifier.padding(bottom = 8.dp).align(Alignment.BottomCenter),
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                fontFamily = Font(R.font.font).toFontFamily(),
//                color = MaterialTheme.colorScheme.onSurface
//            )
//        }
//    }
//    item {
//        Box(modifier=Modifier
//            .height(150.dp)
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(30.dp))
//            .padding(horizontal = 30.dp)
//            .background(MaterialTheme.colorScheme.background)
//            .clickable {
//                navController.navigate(Screens.Movie.route)
//            }
//        ) {
//            Image(painter= rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${firstpopulatmovie?.backdrop_path}"), contentDescription = null,
//                modifier=Modifier.fillMaxSize().clip(RoundedCornerShape(50.dp)))
//            Text("Popular Movies",modifier=Modifier.padding(bottom = 8.dp).align(Alignment.BottomCenter),
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                fontFamily = Font(R.font.font).toFontFamily(),
//                color = Color.White
//
//            )
//        }
//    }
//
////                Spacer(modifier=Modifier.height(30.dp))
//    item {
//        Box (
//            modifier= Modifier
//                .height(150.dp)
//                .fillMaxWidth()
//                .clip(RoundedCornerShape(30.dp))
//                .background(MaterialTheme.colorScheme.background)
//                .padding(horizontal = 30.dp)
//                .clickable {
//                    Toast.makeText(context,"Coming Soon",Toast.LENGTH_SHORT).show()
//                }
//        ){
//            Image(painter= painterResource(R.drawable.tv_series), contentDescription = null,
//                modifier=Modifier.fillMaxSize().clip(RoundedCornerShape(50.dp)))
//            Text("Tv Series",
//                modifier=Modifier.padding(bottom = 8.dp).align(Alignment.BottomCenter),
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                fontFamily = Font(R.font.font).toFontFamily(),
//                color = Color.White)
//
//        }
//    }
//
//
//
//}