package com.example.dmoneyekyc.Screen.SelfieVerification.domain

data class LivelinessResponseModel (

    val  successMessage:String?,
    val  errorMessage:String?,
    val data:FaceMatchData
)
data class FaceMatchData(
    val accuracy_level:Int?=0,
    val distance:Int?=0,
    val normal_cutoff_matched:Boolean?=false,
    val strict_cutoff_matched:Boolean?=false,
)


