package com.example.nsaai.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Email
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nsaai.Navigation.Screens
import com.example.nsaai.ViewModels.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               viewModel: AuthViewModel,
               navController: NavController,

               ) {
    var searchText by remember { mutableStateOf("") }

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope= rememberCoroutineScope()
    ModalNavigationDrawer(drawerState = drawerState,
        drawerContent = {
            DrawerContent()
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
                .padding(paddingValues),
                verticalArrangement = Arrangement.Center
                ) {
                Button(
                    onClick = {
                        viewModel.logout()
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                ) {
                    Text("Logout")
                }

                Button(
                    onClick = {
                        navController.navigate(Screens.Movie.route)
                    }
                ) {
                    Text("Navigate to Movie Section")
                }
            }

        }

    }






}

@Composable
fun DrawerContent(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier

            .fillMaxHeight() // Occupy only height
            .width(300.dp) // Limit the drawer width
    ) { Text("Scaffold And TopBar",
        modifier= Modifier.padding(16.dp),
        fontSize = 24.sp)
        HorizontalDivider()
        Spacer(modifier=Modifier.height(16.dp))
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
        ) }



}