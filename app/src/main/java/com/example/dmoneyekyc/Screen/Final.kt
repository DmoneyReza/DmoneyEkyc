package com.example.dmoneyekyc.Screen

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.dmoney.auth.presentation.ServiceViewModel
import com.example.dmoney.feature.SelfieVerification.FaceDetectionViewModel

@Composable
fun Final(
    navController: NavController,
    viewModel: ServiceViewModel = hiltViewModel(),
) {
    val sharedViewModel: ServiceViewModel = hiltViewModel(LocalContext.current as ComponentActivity)

    val painter = rememberAsyncImagePainter(sharedViewModel.eyeOpenFaceImageUri.value)

    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Text(text = "Result Page")
        Spacer(modifier = Modifier.height(30.dp))
//        Text(text = "nid: ${viewModel.localStorage.getString("nid")}")
//        Text(text = "dob: ${viewModel.localStorage.getString("dob")}")

        androidx.compose.foundation.Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .rotate(270f)
                .padding(16.dp) // Adjust padding as needed
        )

        Text(text = "Rnid: ${sharedViewModel.nidFrontRemoteState.value.nid}")
        Text(text = "Rdob: ${sharedViewModel.nidFrontRemoteState.value.dob}")

    }
}