package com.example.nsaai.authscreens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CodeOff
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nsaai.Authentication.GoogleAuth
import com.example.nsaai.Navigation.Screens
import com.example.nsaai.R
import com.example.nsaai.ViewModels.AuthState
import com.example.nsaai.ViewModels.AuthViewModel
import com.example.nsaai.signinOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(navController: NavController,
                 modifier: Modifier = Modifier,
                 viewModel: AuthViewModel
) {
    val authState by viewModel.authState
    val context = LocalContext.current

    val visible = remember { mutableStateOf(true) }
    val _ispasswordvisibleforsignup= viewModel.ispasswordvisible.value



    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(true) }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                // Check email verification before navigating
                val user = FirebaseAuth.getInstance().currentUser
                if (user?.isEmailVerified == true) {

                    navController.navigate(Screens.Home.route) {
                        popUpTo(Screens.LoginScreen.route) { inclusive = true }
                    }
                } else {
                    Toast.makeText(context, "Please verify your email before logging in.", Toast.LENGTH_SHORT).show()
                }
            }
            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            }
            AuthState.Loading -> {
                // Show loading spinner
            }
            AuthState.Idle -> {
                // Do nothing
            }
        }
    }
//    LaunchedEffect(authState) {
//        when (authState) {
//            is AuthState.Success -> {
//                Log.d("SignInScreen", "Login Successful")
//                navController.navigate(Screens.Home.route) {
//                    popUpTo(Screens.LoginScreen.route) { inclusive = true }
//                }
//            }
//            is AuthState.Error -> {
//                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
//            }
//            AuthState.Loading -> {
//                Log.d("SignUpScreen", "Loading...")
//                // TODO: Show some Loading screen like loading
//            }
//            AuthState.Idle -> {
//                // Do nothing
//            }
//        }
//    }
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.ArrowBackIosNew,
//                            contentDescription = "Back",
//                            modifier = Modifier.size(18.dp) // Adjust icon size to match text size
//                        )
//                        Spacer(modifier = Modifier.width(8.dp)) // Add spacing between icon and text
//                        Text(
//                            text = "Back",
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 18.sp
//                        )
//                    }
//                }
//            )
//        }
//    ) {


    Column(modifier= Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primary.copy(0.5f))) {

        Row(modifier= Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(top = 20.dp, start = 10.dp)
            .clickable {
                navController.navigateUp()
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                modifier=Modifier.size(12.dp))

            Text("Back",
                modifier= Modifier.padding(start = 5.dp),
                fontFamily = Font(R.font.font).toFontFamily(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp)

        }


        AnimatedVisibility(
            visible = visible.value,
            enter = slideInVertically(
                initialOffsetY = { it }, // Slide in from the bottom
                animationSpec = tween(durationMillis = 1000) // Duration of the animation
            )
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.CenterVertically, unbounded = true)
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(MaterialTheme.colorScheme.onBackground)
                        .align(Alignment.BottomCenter)
                        .animateContentSize { initialValue, targetValue -> }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        Text(
                            "GET STARTED",
                            fontSize = 30.sp,
                            fontFamily = Font(R.font.font).toFontFamily(),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.background
                        )


                        Spacer(modifier = Modifier.height(30.dp))

                        // Animate Name TextField

                        TextField(
                            value = name,
                            onValueChange = { name = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            placeholder = { Text(text = "Name") },
                            singleLine = true
                        )




                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            placeholder = { Text(text = "Email") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            maxLines = 1,
                            singleLine = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.Email,
                                    contentDescription = "Enter Email"
                                )
                            }
                        )


                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            placeholder = { Text(text = "Password") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            maxLines = 1,
                            singleLine = true,
                            visualTransformation = if (_ispasswordvisibleforsignup) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.CodeOff,
                                    contentDescription = "Password Visibility",
                                    modifier=Modifier.clickable {
                                        viewModel.togglepasswordVisibility()
                                    }
                                )
                            }
                        )

//
//                        Row(
//                            modifier = Modifier.fillMaxWidth()
//                                .,
//                            verticalAlignment = Alignment.CenterVertically,
//                        ) {
//                            Checkbox(
//                                checked = checked,
//                                onCheckedChange = { checked = it },
//                                modifier = Modifier.padding(8.dp)
//                            )
//                            Text("I agree to the processing of", color = MaterialTheme.colorScheme.background)
//                            Text(
//                                " Personal data",
//                                color = MaterialTheme.colorScheme.primary,
//                                fontWeight = FontWeight.Bold
//                            )
//                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = checked,
                                onCheckedChange = { checked = it },
//                                modifier = Modifier.padding(end = 4.dp)
                            )

                            Text(
                                text = "I agree to the processing of",
                                color = MaterialTheme.colorScheme.background,
//                                modifier = Modifier.weight(1f) // Allows text to take necessary space
                            )

                            Text(
                                text = " Personal data",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }



                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.onSecondary
                        )

                        Spacer(modifier = Modifier.height(20.dp))


                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary.copy(0.4f)),
                            onClick = {
                                if (email.isNotEmpty() && password.isNotEmpty()) {
                                    viewModel.signup(email, password)
                                } else {
                                    Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
                                }
                            }) {
                            Text("Sign up")
                        }


                        Spacer(modifier = Modifier.height(30.dp))
                        Divider(modifier = Modifier.height(1.dp), color = MaterialTheme.colorScheme.background)


//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(20.dp)
//                                .padding(horizontal = 16.dp),
//                            horizontalArrangement = Arrangement.SpaceEvenly,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            signinOptions.forEach { item ->
//                                Box(
//                                    modifier = Modifier
//                                        .height(30.dp)
//                                        .width(30.dp)
//                                        .clickable(
//                                            onClick = {
//                                                Log.d(
//                                                    "SignUpScreen",
//                                                    "Button clicked: ${item.title}"
//                                                )
//                                            },
//                                            indication = rememberRipple(bounded = true), // Add ripple effect
//                                            interactionSource = remember { MutableInteractionSource() }
//                                        )
//                                        .clip(CircleShape)
//                                        .background(Color.White)
//                                        ,
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    Image(
//                                        painter = painterResource(id = item.icon),
//                                        contentDescription = item.title,
//                                        modifier = Modifier.width(60.dp)
//                                    )
//                                }
//                            }
//                        }
                        IconButton(
                            modifier=Modifier
                                .fillMaxSize()
                                .padding(horizontal = 40.dp, vertical = 10.dp)
                                .clip(RoundedCornerShape(100.dp))
                                .background(MaterialTheme.colorScheme.background),
                            onClick = {
                                val googleauth= GoogleAuth(context)
                                CoroutineScope(Dispatchers.Main).launch {
                                    val isSignedIn = googleauth.signIn()
                                    if (isSignedIn) {
//                                        Toast.makeText(context, "Google Sign-In Successful", Toast.LENGTH_SHORT).show()
                                        navController.navigate(Screens.Home.route) {
                                            popUpTo(Screens.LoginScreen.route) { inclusive = true }
                                        }
                                    } else {
//                                        Toast.makeText(context, "Google Sign-In Failed", Toast.LENGTH_SHORT).show()
                                    }
                                }

//                                Toast.makeText(context, "Google Auth Button Has been Clicked", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Row(
                                modifier= Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 10.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(painter = painterResource(id = R.drawable.googleicon60), contentDescription = "Google Auth Button",
                                    modifier=Modifier
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.onBackground)
                                )
                                Text("Sign in with Google",
                                    modifier=Modifier.padding(start = 10.dp),
                                    fontSize = 16.sp,
                                    fontFamily = Font(R.font.font).toFontFamily(),
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }

                        }




                        Spacer(modifier = Modifier.height(20.dp))


                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Already have an account?", color = MaterialTheme.colorScheme.background)
                            Text(
                                " Sign in", color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                modifier= Modifier.clickable {
                                    navController.navigate(Screens.SignIn.route)
                                }
                            )
                        }


                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }



    }


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview(modifier: Modifier = Modifier) {
    SignUpScreen(viewModel =  viewModel(),navController = rememberNavController())
}
