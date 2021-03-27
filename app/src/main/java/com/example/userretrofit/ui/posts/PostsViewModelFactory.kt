package com.example.userretrofit.ui.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.userretrofit.data.RestApi
import com.example.userretrofit.data.database.UserDao
import com.example.userretrofit.ui.userPosts.PostViewModel

class PostsViewModelFactory (private val dbSource: UserDao, private val service : RestApi): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostsViewModel::class.java)){
            return PostsViewModel(dbSource,service) as T
        } else {
            throw IllegalArgumentException("PostsViewModel not found")
        }
    }
}