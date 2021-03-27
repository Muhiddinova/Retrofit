package com.example.userretrofit.ui.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userretrofit.data.RestApi
import com.example.userretrofit.data.database.UserDao
import com.example.userretrofit.model.ModelComments
import com.example.userretrofit.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentsViewModel(dbSource: UserDao, service:RestApi) : ViewModel() {
    private val repo = Repository(dbSource,service)
    private var result: LiveData<List<ModelComments>> = MutableLiveData()

    fun fetchComments(postId: Int): LiveData<List<ModelComments>> {
        result = repo.getComments(postId)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.updateDataComments(postId)
            }
        }
        return result
    }


}