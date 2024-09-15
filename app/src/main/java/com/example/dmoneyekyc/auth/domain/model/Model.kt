package com.example.dmoney.auth.domain.model

class Model(
    val access_token:String,
    val token_type:String,
    val expires_in:Int,
)


data class NidMainModel(
    val nid:String="",
    val dob:String=""
)