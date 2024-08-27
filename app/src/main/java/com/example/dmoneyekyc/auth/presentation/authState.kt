package com.example.dmoney.auth.presentation

import com.example.dmoney.auth.domain.model.Model

data class authState (
    val data: Model? = null,
    val message: String = "",
    val isLoading:Boolean = false
)