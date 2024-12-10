package com.example.dmoneyekyc.Screen

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.dmoney.auth.domain.model.BaseRespondModel
import com.example.dmoney.auth.presentation.ServiceViewModel
import com.example.dmoney.feature.SelfieVerification.FaceDetectionViewModel
import com.example.dmoney.navigation.route.AuthRoute
import com.example.dmoney.navigation.route.ScreenRoute
import com.example.dmoneyekyc.R
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.FaceMatchData
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.LivelinessResponseModel
import com.example.dmoneyekyc.Screen.SelfieVerification.domain.scrappingData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun Final(
    navController: NavController,
    viewModel: ServiceViewModel = hiltViewModel(),
) {
    val sharedViewModel: ServiceViewModel = hiltViewModel(LocalContext.current as ComponentActivity)

    val painter = rememberAsyncImagePainter(sharedViewModel.eyeOpenFaceImageUri.value)
    val painterNid = rememberAsyncImagePainter(sharedViewModel.nidFrontImage.value)

    val type = object : TypeToken<scrappingData>() {}.type
    val type2 = object : TypeToken<LivelinessResponseModel>() {}.type
    val ecData =Gson().fromJson<scrappingData>(viewModel.localStorage.getString("ecData"),type)
    val selfie  = viewModel.localStorage.getString("selfie")

    val faceMatchData = Gson().fromJson<LivelinessResponseModel>(viewModel.localStorage.getString("livliness"),type2)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(top = 20.dp)
            .padding(25.dp)
            .imePadding()
            .background(color = MaterialTheme.colorScheme.background)

    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_success_check),
                contentDescription = "Start Icon",
                tint = Color.Unspecified,
                modifier = Modifier.height(85.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Congratulations!",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Your wallet has been successfully created.",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(700)

                ),
                color = Color(0xFF666666)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Now let’s enjoy Dmoney features!",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(20.dp))


        }

        Box(
            modifier = Modifier
                .weight(1f)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                           bitmap = base64StringToBitmap(ecData.imageByte)!!.asImageBitmap(),
                            modifier = Modifier.size(50.dp).clip(RoundedCornerShape(152.dp)),
                            contentDescription = "dmoney logo"
                        )
                        Text(text = "NID Photo", style = MaterialTheme.typography.labelSmall)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Photo Matching", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text= "${ faceMatchData.data.accuracy_level }%",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(500),
                                fontSize = 26.sp,
                                lineHeight = 31.47.sp
                            )
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                            bitmap =sharedViewModel.selfiBitmap.value!!.asImageBitmap(),
                            modifier = Modifier.size(50.dp).clip(RoundedCornerShape(152.dp)),
                            contentDescription = "dmoney logo"
                        )
                        Text(text = "KYC Photo", style = MaterialTheme.typography.labelSmall)
                    }


                }

                if(true){
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 20.dp)) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .clip(shape = RoundedCornerShape(50.dp))
                                .background(Color(0xFF6DC580))
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            text = "eKYC Approved",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.White

                        )
                    }

                }else{
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 20.dp)) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .clip(shape = RoundedCornerShape(50.dp))
                                .background(Color(0xFFF5953E))
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            text = "Waiting for manual Approval",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.White

                        )
                    }
                }



                Column(modifier = Modifier) {
                    Text(
                        text = ecData.name,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontSize = 13.sp,
                            fontWeight = FontWeight(700)
                        ),
                        color = Color(0xFF222222)
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {

                        Text(
                            text = "Wallet ID:",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "01234567890",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontSize = 13.sp,
                                fontWeight = FontWeight(700)
                            ),
                            color = Color(0xFF222222)
                        )
                    }
                }
                Column(
                    modifier = Modifier.padding(top = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "Name (Bangla)",
                        style = MaterialTheme.typography.labelSmall,
                    )

                    Text(
                        text = ecData.nameBangla,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(700),
                            fontSize = 13.sp
                        ),
                        color = Color(0xFF222222)
                    )

                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)){
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "eKYC Type",
                            style = MaterialTheme.typography.labelSmall,
                        )

                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_ekyc),
                                contentDescription = "Start Icon",
                                tint = Color.Unspecified,
                                modifier = Modifier.height(11.42.dp)
                            )
                            Text(
                                text = "NID",
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.inter)),
                                    fontWeight = FontWeight(700),
                                    fontSize = 13.sp
                                ),
                                color = Color(0xFF222222)
                            )
                        }



                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "Country",
                            style = MaterialTheme.typography.labelSmall,
                        )

                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            Icon(
                                painter = painterResource(id = R.drawable.bd_flag),
                                contentDescription = "Start Icon",
                                tint = Color.Unspecified,
                                modifier = Modifier.height(11.42.dp)
                            )
                            Text(
                                text = "Bangladesh",
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.inter)),
                                    fontWeight = FontWeight(700),
                                    fontSize = 13.sp
                                ),
                                color = Color(0xFF222222)
                            )
                        }

                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)){
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "Father’s Name",
                            style = MaterialTheme.typography.labelSmall,
                        )



                        Text(
                            text = ecData.fahterName,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(700),
                                fontSize = 13.sp
                            ),
                            color = Color(0xFF222222)
                        )

                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "Mothers Name",
                            style = MaterialTheme.typography.labelSmall,
                        )

                        Text(
                            text = ecData.motherName,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(700),
                                fontSize = 13.sp
                            ),
                            color = Color(0xFF222222)
                        )

                    }
                }



                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)){
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "Nid Number",
                            style = MaterialTheme.typography.labelSmall,
                        )



                        Text(
                            text = ecData.nidNumber,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(700),
                                fontSize = 13.sp
                            ),
                            color = Color(0xFF222222)
                        )

                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "Date of Birth",
                            style = MaterialTheme.typography.labelSmall,
                        )

                        Text(
                            text = ecData.dob,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(700),
                                fontSize = 13.sp
                            ),
                            color = Color(0xFF222222)
                        )

                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)){
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "Profession",
                            style = MaterialTheme.typography.labelSmall,
                        )



                        Text(
                            text = ecData.occupation,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(700),
                                fontSize = 13.sp
                            ),
                            color = Color(0xFF222222)
                        )

                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "Gender",
                            style = MaterialTheme.typography.labelSmall,
                        )

                        Text(
                            text = "Male",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(700),
                                fontSize = 13.sp
                            ),
                            color = Color(0xFF222222)
                        )

                    }
                }

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {

                    Text(
                        text = "Permanent Address",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(700),
                            fontSize = 10.sp,
                            lineHeight = 12.1.sp
                        ),
                        color = Color(0xFF000000)
                    )

                    Text(
                        text ="${ecData.presentHomeHoldingNo} , ${ecData.presentRegion} ",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xCC000000)
                    )

                }

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {

                    Text(
                        text = "Present Address",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(700),
                            fontSize = 10.sp,
                            lineHeight = 12.1.sp
                        ),
                        color = Color(0xFF000000)
                    )

                    Text(
                        text = "বে ২৩, লেভেল ৪, প্লট ৬, ব্লক-এস ডাব্লিউ (1) গুলশান অ্যাভিনিউ, গুলশান",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xCC000000)
                    )

                }







            }

        }

//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 17.dp)
//        ) {
//            DButton(
//                IsEnable = true,
//                primaryColor = Color(0x0DF68E2A) ,
//                contentColor = MaterialTheme.colorScheme.primary,
//                onClick = {
//                    navController.navigate(AuthRoute.Login.route) {
//                        navController.popBackStack()
//                        popUpTo(AuthRoute.Login.route) {
//                            inclusive = false
//                        }
//                    }
//                },
//                text = "Get Started"
//            )
//        }

    }
}

fun base64StringToBitmap(base64String: String): android.graphics.Bitmap? {
    return try {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}