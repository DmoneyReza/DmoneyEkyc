package com.example.dmoney.util

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.platform.LocalContext


class LocalStorageService(
    context: Context,
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
) {


    fun putString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
    fun putInt(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getString(key: String):String?{
        return  sharedPreferences.getString(key,null)
    }

    fun getBoolean(key: String):Boolean{
        return  sharedPreferences.getBoolean(key,true)
    }


}