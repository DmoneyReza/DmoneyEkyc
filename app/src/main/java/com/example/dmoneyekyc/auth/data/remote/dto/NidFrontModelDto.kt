package com.example.dmoneyekyc.auth.data.remote.dto

import com.example.dmoney.auth.domain.model.NidMainModel

data class NidFrontModelDto(
    val nid:String="",
    val dob:String=""
){
    fun toNidMainModel():NidMainModel{
        return NidMainModel(nid,dob)
    }
}
