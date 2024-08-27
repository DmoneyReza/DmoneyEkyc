package com.example.dmoney.navigation.route

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenRoute(
    val route:String,
    val title:String,
    val ImageVector: ImageVector
) {

    object Home: ScreenRoute(
        route = "Home",
        title = "Home",
        ImageVector =  Icons.Rounded.Home
    )

    object Other: ScreenRoute(
        route = "Other",
        title = "Other",
        ImageVector =  Icons.Rounded.AccountCircle
    )

    object Camera: ScreenRoute(
        route = "Camera",
        title = "Camera",
        ImageVector =  Icons.Rounded.CameraAlt
    )


}