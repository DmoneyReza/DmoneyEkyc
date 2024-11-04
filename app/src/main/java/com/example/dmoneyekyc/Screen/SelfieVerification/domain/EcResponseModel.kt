package com.example.dmoneyekyc.Screen.SelfieVerification.domain


data class scrappingData(
    val nameBangla:String,
    val name:String,
    val dob:String,
    val fahterName:String,
    val motherName:String,
    val occupation:String,
    val presentDivision:String,
    val presentCityOrMunicipality:String,
    val presentAdditionalVillageOrRoad:String,
    val presentHomeHoldingNo:String,
    val presentPostOffice:String,
    val presentPostalCode:String,
    val presentRegion:String,
    val bloodGroup:String,
    val nidNumber:String,
    val nidPIN:String,
    val image:String,
    val imageByte:String,
)



data class EcResponseModel (
    val   status:String ,
    val message: String,
    val scrappedData: scrappingData?
)