package com.example.dmoney.navigation.navGraph


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dmoney.navigation.route.GraphRoute

@Composable
fun RootNav(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = GraphRoute.AuthGraph,
        route = GraphRoute.RootGraph
    ) {
//this for user onBoarding and access process
        AuthNavGraph(
            navController
        )
// This is my Home Screen Contents
        composable(
            route = GraphRoute.MainGraph
        ){

        }





    }
}