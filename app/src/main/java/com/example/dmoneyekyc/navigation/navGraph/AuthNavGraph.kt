package com.example.dmoney.navigation.navGraph
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.dmoney.auth.presentation.ServiceViewModel
import com.example.dmoney.feature.SelfieVerification.FaceScanningScreen
import com.example.dmoney.navigation.route.AuthRoute
import com.example.dmoney.navigation.route.GraphRoute
import com.example.dmoneyekyc.Screen.Final
import com.example.dmoneyekyc.Screen.Home
import com.example.dmoneyekyc.Screen.NIDScanData.NIDScanDataScreen
import com.example.imagetotextextractor.utlis.working.CaptureNIDScreen


fun NavGraphBuilder.AuthNavGraph(
    navController: NavController,
) {

    navigation(
        startDestination = AuthRoute.Home.route,
        route = GraphRoute.AuthGraph
    ) {
        composable(
            route = AuthRoute.Home.route,
            enterTransition = { ->
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300))
            },
            exitTransition = { ->
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300))
            },
            popExitTransition = { ->
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300))
            },
            popEnterTransition = { ->
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300))
            },
        ) {
            Home(navController = navController)
        }

        composable(
            route = AuthRoute.NIDScanning.route,
            enterTransition = { ->
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300))
            },
            exitTransition = { ->
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300))
            },
            popExitTransition = { ->
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300))
            },
            popEnterTransition = { ->
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300))
            },
        ) {

            CaptureNIDScreen(navController = navController)
        }

        composable(
            route = AuthRoute.NIDScanData.route +"?nid={nid}"+"?dob={dob}",
            arguments = listOf(
                navArgument(
                    name = "nid"
                ){
                    type  =NavType.StringType
                    defaultValue = ""
                },
                navArgument(
                    name = "dob"
                ){
                    type  =NavType.StringType
                    defaultValue = ""
                }
            ),
            enterTransition = { ->
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300))
            },
            exitTransition = { ->
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300))
            },
            popExitTransition = { ->
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300))
            },
            popEnterTransition = { ->
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300))
            },
        ) {
            NIDScanDataScreen(navController = navController)
        }


        composable(
            route = AuthRoute.FaceAnalyzer.route,
            enterTransition = { ->
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300))
            },
            exitTransition = { ->
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300))
            },
            popExitTransition = { ->
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300))
            },
            popEnterTransition = { ->
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300))
            },
        ) {
            FaceScanningScreen(navController = navController)
        }
        composable(
            route = AuthRoute.Final.route,
            enterTransition = { ->
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300))
            },
            exitTransition = { ->
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300))
            },
            popExitTransition = { ->
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300))
            },
            popEnterTransition = { ->
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300))
            },
        ) {
            Final(navController = navController)
        }
    }

}