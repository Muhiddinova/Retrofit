package com.example.userretrofit.ui.user

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.userretrofit.data.RestApi
import com.example.userretrofit.data.database.UserDao

class UserViewModelFactory(private val dbSource:UserDao,private val service:RestApi,private var context: Context):ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(dbSource,service,context) as T
        } else {
            throw IllegalArgumentException("UserViewModel not found")
        }
    }
}