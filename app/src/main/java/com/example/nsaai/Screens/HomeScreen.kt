package com.example.nsaai.Screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.nsaai.ViewModels.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               viewModel: AuthViewModel,
               navController: NavController,
               ) {

    Text("Home Screen")
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

}