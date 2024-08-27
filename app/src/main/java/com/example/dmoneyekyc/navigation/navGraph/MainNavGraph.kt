package com.example.dmoney.navigation.navGraph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dmoney.auth.presentation.ServiceViewModel
import com.example.dmoney.navigation.route.ScreenRoute


@Composable
fun MainNavGraph (
    rootNavController: NavHostController,
    homeNavController: NavHostController,
    innerPadding: PaddingValues
){
    NavHost(
        navController = homeNavController,
        startDestination = ScreenRoute.Home.route,
        route = "HomeGraph"
    ){
        composable(route = ScreenRoute.Home.route){
//            Home(innerPadding =  innerPadding)
        }
        composable(route = ScreenRoute.Other.route){
//            Other(innerPadding = innerPadding)
        }
        composable(route = ScreenRoute.Camera.route){
//            Camera(innerPadding = innerPadding)
        }
    }

}
