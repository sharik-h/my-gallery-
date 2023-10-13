package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.data.ViewModel
import com.example.myapplication.detailViewPage
import com.example.myapplication.mainPage
import com.example.myapplication.newImage

@Composable
fun NavHost(
    navHostController: NavHostController,
    viewModel: ViewModel
) {
     NavHost(
         navController = navHostController,
         startDestination = Screen.mainPage.route
     ){
         composable(route = Screen.mainPage.route){
             mainPage(navController = navHostController, viewModel = viewModel)
         }
         composable(route = Screen.detailPage.route){
             detailViewPage(navController = navHostController, viewModel = viewModel)
         }
         composable(route = Screen.newImage.route){
             newImage(navController = navHostController, viewModel = viewModel)
         }
     }
}