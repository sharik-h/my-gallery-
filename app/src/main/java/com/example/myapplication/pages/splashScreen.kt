package com.example.myapplication.pages

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.data.ViewModel
import com.example.myapplication.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun splashPage(navController: NavController, viewModel: ViewModel) {
    var startAnimState by remember { mutableStateOf(false) }

    val floatAsState = animateFloatAsState(
        targetValue = if (startAnimState) 1f else 0f,
        animationSpec = keyframes {
            durationMillis = 1000
            0f at 1 with LinearOutSlowInEasing
        }, label = ""
    )
    LaunchedEffect(key1 = true ) {
        startAnimState = true
        delay(500)
        navController.navigate(Screen.mainPage.route) {
            popUpTo(Screen.splashScreen.route) { inclusive = true }
        }
    }
    SplashScreen(textOpacity = floatAsState.value)
}

@Composable
fun SplashScreen(textOpacity: Float) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = null,
            modifier = Modifier.size(100.dp).alpha(textOpacity)
        )
    }
}