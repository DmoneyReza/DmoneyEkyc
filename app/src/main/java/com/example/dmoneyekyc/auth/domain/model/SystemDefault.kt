package com.example.dmoney.auth.domain.model


data class Location(val latitude: Double?, val longitude: Double?)


data class NetworkInfo(val networkOperator: String, val mobileNumber: String, val ipAddress: String, val wifiSsid: String)


data class FingerprintData(
    val osVersion: String,
    val appVersion: String,
    val manufacturer: String,
    val model: String,
)

data class SystemDefault(
    val deviceId: String,
    val deviceName: String,
    val publicKey: String,
    val fingerprintData: FingerprintData,
    val networkInfo: NetworkInfo,
    var location: Location,
    val client_id: String
)
data class BaseRequestModel<T>(
    val data:T,
    val systemDefault:SystemDefault
)