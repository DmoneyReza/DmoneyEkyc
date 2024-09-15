package com.example.dmoneyekyc.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dmoney.auth.presentation.ServiceViewModel
import com.example.dmoney.feature.SelfieVerification.FaceDetectionViewModel

@Composable
fun Final(
    navController: NavController,
    viewModel: ServiceViewModel = hiltViewModel(),
) {
    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Text(text = "Result Page")
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "nid: ${viewModel.localStorage.getString("nid")}")
        Text(text = "dob: ${viewModel.localStorage.getString("dob")}")
    }
}