package com.example.nsaai.authscreens

import android.annotation.SuppressLint
import androidx.lifecycle.lifecycleScope
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.rounded.CodeOff
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nsaai.signinOptions
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nsaai.Authentication.GoogleAuth
import com.example.nsaai.Navigation.Screens
import com.example.nsaai.R
import com.example.nsaai.ViewModels.AuthState
import com.example.nsaai.ViewModels.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignInScreen(
    viewModel: AuthViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,

) {
    val authState by viewModel.authState
    val context = LocalContext.current

    var showforgotpassworddialog by rememberSaveable { mutableStateOf(false) }
    var emailforforgotpass by remember { mutableStateOf("") }

    val isPasswordVisible = viewModel.isPasswordVisible.value

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var checked by rememberSaveable { mutableStateOf(true) }
//    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
//        sharedPreferences.edit().putBoolean("rememberMe", checked).apply()

    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colorScheme.primary.copy(0.5f)

    LaunchedEffect(systemUiController) {
        systemUiController.setSystemBarsColor(color = systemBarColor)
    }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                Log.d("SignInScreen", "Login Successful")
                navController.navigate(Screens.Home.route) {
                    popUpTo(Screens.LoginScreen.route) { inclusive = true }
                }
            }
            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            }
            AuthState.Loading -> {
                Log.d("SignInScreen", "Loading...")
            }
            AuthState.Idle -> {
                // Do nothing
            }
        }
    }

    val scrollState= rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            MaterialTheme.colorScheme.primary.copy(0.5f)
        )
        .verticalScroll(scrollState)
        ,
        verticalArrangement = Arrangement.SpaceBetween) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(top = 20.dp, start = 10.dp)
                .clickable {
                    navController.navigateUp()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null, modifier = Modifier.size(12.dp))
            Text("Back", modifier = Modifier.padding(start = 5.dp), fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }

        AnimatedVisibility(
            visible = true,
            enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(durationMillis = 1000))
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.CenterVertically, unbounded = true)
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(MaterialTheme.colorScheme.onBackground)
                        .align(Alignment.BottomCenter)
                        .animateContentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Welcome Back", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.background)

                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(
                            value = email,
                            onValueChange = { email=it  },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            placeholder = { Text(text = "Email") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.Email,
                                    contentDescription = "Enter Email Id"
                                )
                            }
                        )

                        Row (
                            modifier=Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            TextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                placeholder = { Text(text = "Password") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                singleLine = true,
                                visualTransformation =if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    Icon(
                                        modifier = Modifier.clickable {
                                            viewModel.togglePasswordVisibility()
                                        },
                                        imageVector = Icons.Rounded.CodeOff,
                                        contentDescription = "Enter Password"
                                    )
                                }
                            )

                        }


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(checked = checked, onCheckedChange = {
                                    checked = it
//                                    handleRememberMe(context, checked)
                                    }
                                )

                                Text("Remember me", color = MaterialTheme.colorScheme.background)
                            }
                            Text("Forgot password?", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold,
                                modifier=Modifier.clickable {
                                    showforgotpassworddialog= true

                                })
                        }
                        if(showforgotpassworddialog){
                            AlertDialog(modifier=Modifier.width(800.dp).
                                padding(horizontal = 10.dp),
                                onDismissRequest = { showforgotpassworddialog = false }, // Handle dismissal
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = "Email Icon"
                                    )
                                },
                                title = {
                                    Text("Enter Your Email Address")
                                },
                                text = {
                                    Column {

                                        TextField(
                                            value = emailforforgotpass,
                                            onValueChange = { emailforforgotpass=it  },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(10.dp)),
                                            placeholder = { Text(text = "Email") },
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                            singleLine = true,
                                            trailingIcon = {
                                                Icon(
                                                    imageVector = Icons.Rounded.Email,
                                                    contentDescription = "Enter Email Id"
                                                )
                                            }
                                        )
                                    }
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            // Handle confirmation (e.g., send reset email)
                                            FirebaseAuth.getInstance().sendPasswordResetEmail(emailforforgotpass)
                                                .addOnCompleteListener{task ->
                                                    if(task.isSuccessful){
                                                        Toast.makeText(context,"Reset Email Sent",Toast.LENGTH_SHORT).show()
                                                    }else{
                                                        Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
                                                    }

                                                }
                                            showforgotpassworddialog = false
                                        }
                                    ) {
                                        Text("Confirm")
                                    }
                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = { showforgotpassworddialog = false } // Dismiss dialog
                                    ) {
                                        Text("Dismiss")
                                    }
                                }
                            )
                        }


                        Spacer(modifier = Modifier.height(20.dp))

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
                                    viewModel.login(email, password)
                                } else {

                                    Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
                                }
                            }
                        ) {
                            Text("Sign in")
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
//                                        .height(20.dp)
//                                        .width(20.dp)
//                                        .clip(CircleShape)
//                                        .clickable(
//                                            onClick = {
////                                                when (item.title) {
////                                                    "Google" -> signInWithGoogle()
////                                                    "Facebook" -> signInWithFacebook()
////                                                }
//                                                Log.d("IconClicked", "${item.title} Clicked")
//                                            },
//                                            indication = rememberRipple(bounded = true),
//                                            interactionSource = remember { MutableInteractionSource() }
//                                        )
//                                        .background(Color.White),
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
                                    color= MaterialTheme.colorScheme.onBackground
                                )
                            }

                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Don't have an account?", color = MaterialTheme.colorScheme.background)
                            Text(" Sign up", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold,
                                modifier=Modifier.clickable {
                                    navController.navigate(Screens.SignUp.route)
                                })
                        }

                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }
    }

}



//fun login(email: String, password: String, authState: AuthState){
//    _authState.value= authState.Loading
//    val auth= Firebase.auth
//    auth.signInWithEmailAndPassword(email, password)
//        .addOnCompleteListener{ task ->
//            if(task.isSuccessful){
//                Log.d("Login","Login Successful")
//            }else{
//                Log.d("Login","Login Failed")
//            }
//
//        }
//}




@Preview
@Composable
private fun SigninPreview() {
    SignInScreen(viewModel = viewModel(),navController = rememberNavController())
}
