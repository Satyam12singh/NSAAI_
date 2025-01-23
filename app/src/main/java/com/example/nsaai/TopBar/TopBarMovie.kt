package com.example.nsaai.TopBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nsaai.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMovie(modifier: Modifier = Modifier,navController: NavController,name:String) {
    TopAppBar(modifier= Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary.copy(0.5f)),
        title = {
            Text(
                name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Font(R.font.font).toFontFamily(),
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        navigationIcon = {
            IconButton(modifier = Modifier.padding(start = 10.dp),
                onClick = {
                    navController.navigateUp()
                }
            ) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        }
    )
}