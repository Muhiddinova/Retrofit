package com.example.userretrofit.ui.userPosts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userretrofit.data.RestApi
import com.example.userretrofit.data.database.UserDao
import com.example.userretrofit.model.ModelPost
import com.example.userretrofit.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostViewModel(dbSource: UserDao,service:RestApi) : ViewModel() {
    private val repo = Repository(dbSource,service)
    private var result: LiveData<List<ModelPost>> = MutableLiveData()


    fun fetchPosts(userId: Int): LiveData<List<ModelPost>> {
        result = repo.getPosts(userId)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.updateDataPost(userId)
            }
        }
        return result
    }


}