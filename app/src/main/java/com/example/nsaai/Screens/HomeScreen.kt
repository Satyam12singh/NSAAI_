package com.example.nsaai.Screens


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.rememberDrawerState

import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nsaai.Navigation.Screens
import com.example.nsaai.R
import com.example.nsaai.ViewModels.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               viewModel: AuthViewModel,
               navController: NavController,

               ) {
    val context= LocalContext.current
    var searchText by remember { mutableStateOf("") }

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope= rememberCoroutineScope()
    ModalNavigationDrawer(drawerState = drawerState,
        drawerContent = {
            DrawerContent(viewModel=viewModel,navController=navController)
        }
    ) {

        Scaffold(
            topBar = {

                TopBar(
                    searchText = searchText,
                    onSearchTextChanged = {
                        searchText = it
                    },
                    onOpenDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }

        ) { paddingValues->

            Column(modifier= Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.primary.copy(0.5f)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
                ) {

//                Button(
//                    onClick = {
//                        viewModel.logout()
//                        navController.navigate("login") {
//                            popUpTo("home") { inclusive = true }
//                        }
//                    }
//                ) {
//                    Text("Logout")
//                }

//                Button(
//                    onClick = {
//                        navController.navigate(Screens.Movie.route)
//                    }
//                ) {
//                    Text("Navigate to Movie Section")
//                }

                Box(modifier=Modifier
                    .height(150.dp)
                    .width(200.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(MaterialTheme.colorScheme.onPrimary)
                    .clickable {
                        navController.navigate(Screens.Movie.route)
                    }
                ) {
                    Image(painter= painterResource(R.drawable.movies), contentDescription = null,
                        modifier=Modifier.fillMaxSize().clip(RoundedCornerShape(50.dp)))
                    Text("Movies",modifier=Modifier.padding(bottom = 8.dp).align(Alignment.BottomCenter),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Font(R.font.font).toFontFamily(),
                        color = Color.White

                        )
                }
                Spacer(modifier=Modifier.height(30.dp))
                Box (
                    modifier= Modifier
                        .height(150.dp)
                        .width(200.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(MaterialTheme.colorScheme.onPrimary)
                        .clickable {
                            Toast.makeText(context,"Coming Soon",Toast.LENGTH_SHORT).show()
                        }
                ){
                    Image(painter= painterResource(R.drawable.tv_series), contentDescription = null,
                        modifier=Modifier.fillMaxSize().clip(RoundedCornerShape(50.dp)))
                    Text("Tv Series",
                        modifier=Modifier.padding(bottom = 8.dp).align(Alignment.BottomCenter),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Font(R.font.font).toFontFamily(),
                        color = Color.White)

                }


            }

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