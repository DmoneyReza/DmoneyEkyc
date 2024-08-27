package com.example.dmoneyekyc.Screen

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dmoney.auth.presentation.ServiceViewModel
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.example.dmoney.navigation.route.AuthRoute

@Composable
fun Home (
    viewModel: ServiceViewModel = hiltViewModel(),
    navController: NavController
){
    var context = LocalContext.current

    // Remember a launcher to request location permission
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->


    }

    val galleryPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->

    }

    LaunchedEffect(Unit) {
        if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != android.content.pm.PackageManager.PERMISSION_GRANTED &&
            context.checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != android.content.pm.PackageManager.PERMISSION_GRANTED
        ){
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                galleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        }

        if(  ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) != PermissionChecker.PERMISSION_GRANTED)
        {
            locationPermissionLauncher.launch(Manifest.permission.CAMERA)

        }
    }




    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

//        Button(onClick = {
////            navigate to Gallery
//            if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != android.content.pm.PackageManager.PERMISSION_GRANTED &&
//                context.checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != android.content.pm.PackageManager.PERMISSION_GRANTED
//            ){
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
//                galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
//            } else {
//                galleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
//            }
//            }else{
//
//            }
//        }) {
//          Text(text = "Gallery")
//        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
//            navigate to Camera
          if(  ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) != PermissionChecker.PERMISSION_GRANTED)
          {
              locationPermissionLauncher.launch(Manifest.permission.CAMERA)

          }else{
              navController.navigate(AuthRoute.NIDScanning.route) {
                  popUpTo(AuthRoute.Home.route) {
                      inclusive = false
                  }
              }
          }
        }) {
            Text(text = "Camera")
        }

    }
}