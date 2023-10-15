package com.example.myapplication.navigation

sealed class Screen(val route: String) {
    object mainPage: Screen(route = "mainPage")
    object detailPage: Screen(route = "detailPage")
    object newImage: Screen(route = "newImage")
    object splashScreen: Screen(route = "splashScreen")
}