package com.example.dmoney.auth.data.remote.dto

import com.example.dmoney.auth.domain.model.Model
import java.util.Date

class ModelDto(
    val access_token:String,
    val token_type:String,
    val expires_in:Int,
    val created_at:Date
) {
//    yet to declare
    fun toModel():Model{
        return Model(
            access_token = access_token,
            token_type = token_type,
            expires_in = expires_in
        )
    }
}