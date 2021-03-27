package com.example.userretrofit.ui.posts

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

class  PostsViewModel (dbSource:UserDao, service:RestApi): ViewModel() {
    private val repo=Repository(dbSource,service)
    private var result:LiveData<List<ModelPost>> =  MutableLiveData()
    private var resultPost=MutableLiveData<ArrayList<ModelPost>>()

    init {
        fetchPosts()
    }

    private fun fetchPosts(){
         result =repo.getAllPosts()
        viewModelScope.launch {
             withContext(Dispatchers.IO){
                repo.updateDataAllPosts()
            }
        }

    }
    fun getAllPosts():LiveData<List<ModelPost>>{
        return result
    }


    fun postPosts(map: Map<String,String>) {
        viewModelScope.launch {
            resultPost.value= withContext(Dispatchers.IO){
                repo.postPosts(map)
            }
        }
    }
    fun postResult():LiveData<ArrayList<ModelPost>>{
        return resultPost
    }



}