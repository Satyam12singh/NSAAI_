package com.example.nsaai

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.nsaai.Navigation.Navigation
import com.example.nsaai.ViewModels.AuthViewModel

import com.example.nsaai.ui.theme.NSAAITheme
import com.google.firebase.auth.FirebaseAuth

//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val authViewModel = AuthViewModel() // Ensure this is appropriately scoped
//        setContent {
//            NSAAITheme {
//                enableEdgeToEdge()
//                val navController = rememberNavController()
//                Scaffold(
//                    modifier = Modifier.fillMaxSize(),
//                    content = { paddingValues ->
//                        Navigation(
//                            navController = navController,
//                            modifier = Modifier.padding(paddingValues),
//                            viewModel = authViewModel
//                        )
//                    }
//                )
//            }
//        }
//    }
//}
//
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel = AuthViewModel()

        setContent {
            NSAAITheme {
                val navController = rememberNavController()
                val isAuthenticated = AuthViewModel().isAuthenticated // Use the isAuthenticated method to check if the user is logged in
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { paddingValues ->
                        Navigation(
                            authenticated = isAuthenticated, // Pass the correct authentication state to the navigation
                            navController = navController,
                            modifier = Modifier.padding(paddingValues),
                            viewModel = authViewModel
                        )
                    }
                )
            }
        }
    }
}


