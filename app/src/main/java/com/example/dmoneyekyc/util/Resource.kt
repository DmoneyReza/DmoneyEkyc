package com.example.dmoneyekyc.util

sealed class Resource<T>(val data: T? = null, val message: String? = null ,val time:String? = null) {
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Success<T>(data: T?,time:String? = null): Resource<T>(data, time = time)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
}