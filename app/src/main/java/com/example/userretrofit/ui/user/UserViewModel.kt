package com.example.userretrofit.ui.user

import android.content.Context
import androidx.lifecycle.*
import com.example.userretrofit.data.RestApi
import com.example.userretrofit.data.database.UserDao
import com.example.userretrofit.model.ModelUser
import com.example.userretrofit.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class UserViewModel(dbSource: UserDao, service: RestApi, context: Context) : ViewModel() {
    private val repo = Repository(dbSource, service)
    private var result: LiveData<List<ModelUser>> = MutableLiveData()
    private lateinit var timer: Timer


    init {
        fetchUser(context)
    }

    fun fetchUser(context: Context) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.updateDataUsers(context)
            }
        }

    }

    fun getUser(): LiveData<List<ModelUser>> {
        return repo.getUsers()
            //.take(2)
            .flowOn(Dispatchers.IO)
            .catch {

            }
            .asLiveData()

    }


}




