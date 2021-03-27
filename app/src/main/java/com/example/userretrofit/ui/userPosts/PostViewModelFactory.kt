package com.example.userretrofit.ui.userPosts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.userretrofit.data.RestApi
import com.example.userretrofit.data.database.UserDao

class PostViewModelFactory(private val dbSource:UserDao,private val service:RestApi):ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)){
            return PostViewModel(dbSource,service) as T
        } else {
            throw IllegalArgumentException("PostViewModel not found")
        }
    }
}