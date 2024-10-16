package com.example.dmoney.auth.domain.model

data class BaseRespondModel <T>(
    val responseCode:String,
    val activityCode:String,
    val responseMessage:String,
    val data:T?
)
