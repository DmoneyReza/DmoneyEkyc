package com.example.dmoneyekyc.Screen.NIDScanData

import com.example.dmoneyekyc.util.ValidationResult


class ValidationNidScanDataUseCase {

    fun executeNid(nid:String): ValidationResult {
        return if (nid.isNotEmpty()){
            ValidationResult.Success
        }else{
            ValidationResult.Error("Can not leave empty")
        }
    }

    fun executeDate(dob:String):ValidationResult{
        return if (dob.isNotEmpty() && dob !=""){
            ValidationResult.Success
        }else{
            ValidationResult.Error("Can not leave empty")
        }
    }
}