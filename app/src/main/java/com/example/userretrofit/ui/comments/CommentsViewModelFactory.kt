package com.example.userretrofit.ui.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.userretrofit.data.RestApi
import com.example.userretrofit.data.database.UserDao
import com.example.userretrofit.ui.userPosts.PostViewModel

class CommentsViewModelFactory (private val dbSource: UserDao,private val service:RestApi): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentsViewModel::class.java)){
            return CommentsViewModel(dbSource,service) as T
        } else {
            throw IllegalArgumentException("CommentsViewModel not found")
        }
    }
}