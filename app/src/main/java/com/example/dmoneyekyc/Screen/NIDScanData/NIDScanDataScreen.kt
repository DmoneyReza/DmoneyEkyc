package com.example.dmoneyekyc.Screen.NIDScanData


import android.app.DatePickerDialog
import android.location.Location
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dmoney.feature_ekyc.NIDScanData.presentation.NidScanDataUiEvent
import com.example.dmoney.feature_ekyc.NIDScanData.presentation.NidScanInputEvent

import com.example.dmoney.navigation.route.AuthRoute


import com.example.dmoneyekyc.R
import com.example.dmoneyekyc.component.DButton
import com.example.dmoneyekyc.component.ErrorDialog
import com.example.dmoneyekyc.ui.theme.inter
import com.example.dmoneyekyc.util.GifImage
import kotlinx.coroutines.flow.collectLatest
import java.util.Calendar


@Composable
fun NIDScanDataScreen(
    nidScanDataViewModel: NIDScanDataViewModel = hiltViewModel(), navController: NavController

) {
    val keyboardController = LocalSoftwareKeyboardController.current
//    var setNid by remember { mutableStateOf(nidScanDataViewModel.nid.value) }
//    var setDOB by remember { mutableStateOf(nidScanDataViewModel.dob.value) }

    var setNid  = nidScanDataViewModel.nidState
    var setDob  = nidScanDataViewModel.dobState
    var context = LocalContext.current
    var loadingState = nidScanDataViewModel.nidResponseState.value.isLoading
    var isErrorDialogShow by remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val datePickerDialog = DatePickerDialog(
        context, R.style.CustomDatePickerDialog,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
//            selectedDate = "$dayOfMonth/${month + 1}/$year"
            nidScanDataViewModel.eventListener(NidScanInputEvent.EnteredDate("$dayOfMonth/${month + 1}/$year"))
        }, year, month, day)

    LaunchedEffect(key1 = true) {
        nidScanDataViewModel.eventFlow.collectLatest {event->
            when(event){
                NidScanDataUiEvent.EventFailed -> {
                    isErrorDialogShow = true
                }
                NidScanDataUiEvent.EventSuccess ->{
                    navController.navigate(AuthRoute.SelfieVerification.route){
                        popUpTo(AuthRoute.SignUp.route){
                            inclusive = false
                        }
                    }
                }
            }

        }
    }



    Box(
        modifier = Modifier
            .padding(top = 20.dp)
            .navigationBarsPadding()
            .imePadding()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                    }
                    .padding(top = 20.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_left),
                    contentDescription = "Start Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.height(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Sign Up", style = MaterialTheme.typography.titleMedium)


            }
            Spacer(modifier = Modifier.height(20.dp))
//            ProgressStepIndicator(totalSteps = 6, currentStep =4 )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "NID Scan Data",
                color = Color(0xFF444444),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Make sure your NID number and DoB is right.",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF666666)
            )

            Spacer(modifier = Modifier.height(35.dp))
            Text(text = "NID Number", style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = setNid.value.nid,
                onValueChange = {
                    if(it.length <10){
                      nidScanDataViewModel.eventListener(NidScanInputEvent.EnteredNid(it))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF666666),
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(500)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done
                ),

                placeholder = {
                    Text(
                        text = "123 456 78890",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                singleLine = true,
                isError = setNid.value.nidError != "",
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                    errorTextColor = Color.Red,
                    cursorColor = Color.Black
                ))
            Spacer(modifier = Modifier.height(10.dp))
            if (setNid.value.nidError != "") {
                Text(
                    text = setNid.value.nidError,
                    style = TextStyle(
                        fontFamily = inter,
                        fontWeight = FontWeight(400),
                        fontSize = 11.sp,
                        color = Color(0xFFFF3B30)
                    )
                )
            }

            Spacer(modifier = Modifier.height(25.dp))
            Text(text = "Date of Birth", style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value =setDob.value.dob,
                onValueChange = {
                        nidScanDataViewModel.eventListener(NidScanInputEvent.EnteredDate(it))
                },

                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF666666),
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(500)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),

                placeholder = {
                    Text(
                        text = "01-11-1990",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "Start Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.height(24.dp).clickable {
                            datePickerDialog.show()
                        }
                    )
                },
                enabled = false,
                singleLine = true,
                isError = setDob.value.dobError != "",
                colors = TextFieldDefaults.colors(
                    disabledContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                    errorTextColor = Color.Red,
                    cursorColor = Color.Black
                )
               )
            Spacer(modifier = Modifier.height(10.dp))
            if (setDob.value.dobError != "") {
                Text(
                    text = setDob.value.dobError,
                    style = TextStyle(
                        fontFamily = inter,
                        fontWeight = FontWeight(400),
                        fontSize = 11.sp,
                        color = Color(0xFFFF3B30)
                    )
                )
            }


            Spacer(modifier = Modifier.weight(1f))

            DButton(
                IsEnable = true,
                onClick = {
                    nidScanDataViewModel.deviceIdManager.getLastKnownLocation { location: Location? ->
                        nidScanDataViewModel.eventListener(NidScanInputEvent.SubmitEvent(location))
                    }
                },
                text = "Next"
            )


        }

        if (loadingState) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GifImage(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(10.dp),
                    gifDrawable = R.drawable.dmoney_loader
                )
            }

        }

        AnimatedVisibility(
            visible = isErrorDialogShow,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ){
            ErrorDialog(messageBody =  nidScanDataViewModel.nidResponseState.value.responseMessage,
                onClick = {
                    isErrorDialogShow = false
                },

                )
        }


    }
}



