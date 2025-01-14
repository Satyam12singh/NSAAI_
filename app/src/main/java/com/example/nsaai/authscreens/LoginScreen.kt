package com.example.nsaai.authscreens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nsaai.Navigation.Screens


@Composable
fun LoginScreen(navController: NavController, modifier: Modifier = Modifier) {

    var selectedTab by remember { mutableStateOf("SignUp") }
    var isTabClicked by remember { mutableStateOf(false) }

    BackHandler(enabled = isTabClicked) {
        selectedTab = "SignUp"
        isTabClicked = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary.copy(0.5f)),
        contentAlignment = Alignment.Center
    ) {
//        Image(
//            painter = painterResource(R.drawable.background),
//            contentDescription = "login background",
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop // To make the image cover the entire Box
//        )
        Text("Welcome Back!", fontSize = 30.sp, fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .height(60.dp)
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // SignIn Tab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomEnd = 20.dp))
                        .background(if (selectedTab != "SignIn") Color.Transparent else Color.White)
                        .clickable {
                            if (!isTabClicked) {
                                selectedTab = "SignIn"
                                isTabClicked = true
                                navController.navigate(Screens.SignIn.route) // Use navController for navigation
                            }
                        }
                        .height(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sign in",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
//                    HorizontalDivider(
//                        thickness = 3.dp,
//                        modifier = Modifier.align(Alignment.BottomCenter),
//                        color = if (selectedTab != "SignIn") Color.White else Color.Transparent
//                    )
                }

                // SignUp Tab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 20.dp))
                        .background(if (selectedTab != "SignUp") Color.Transparent else Color.White)
                        .clickable {
                            if (!isTabClicked) {
                                selectedTab = "SignUp"
                                isTabClicked = true
                                navController.navigate(Screens.SignUp.route) // Use navController for navigation
                            }
                        }
                        .height(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sign up",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
//                    HorizontalDivider(
//                        thickness = 1.dp,
//                        modifier = Modifier.align(Alignment.BottomCenter),
//                        color = if (selectedTab != "SignUp") Color.White else Color.Transparent
//                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    // You can preview this by passing the NavController
    // Assuming an empty NavController for preview
    LoginScreen(navController = rememberNavController()) // Use rememberNavController for preview
}
