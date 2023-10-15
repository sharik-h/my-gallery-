package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.data.ViewModel
import com.example.myapplication.pages.MainPage
import com.example.myapplication.pages.detailViewPage
import com.example.myapplication.pages.newImage
import com.example.myapplication.pages.splashPage

@Composable
fun NavHost(
    navHostController: NavHostController,
    viewModel: ViewModel
) {
     NavHost(
         navController = navHostController,
         startDestination = Screen.splashScreen.route
     ){
         composable(route = Screen.mainPage.route){
             MainPage(navController = navHostController, viewModel = viewModel)
         }
         composable(route = Screen.detailPage.route){
             detailViewPage(navController = navHostController, viewModel = viewModel)
         }
         composable(route = Screen.newImage.route){
             newImage(navController = navHostController, viewModel = viewModel)
         }
         composable(route = Screen.splashScreen.route){
             splashPage(navController = navHostController, viewModel = viewModel)
         }
     }
}