package com.example.userretrofit.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.userretrofit.data.RestApi
import com.example.userretrofit.data.database.UserDao
import com.example.userretrofit.model.ModelComments
import com.example.userretrofit.model.ModelPost
import com.example.userretrofit.model.ModelUser
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.*

const val MYKEYSTRING = "key"

class Repository(private val dbSourse: UserDao, private val service: RestApi) {
    private val TAG = "UserRepository"
    private lateinit var timer: Timer
    private var updateTime: Long = 0


    private fun waitAndOpenOtherFragment() {


//
//            if (navController.currentDestination?.id == R.id.mainFragment) {
//                if (lastBackPressed + 2000 >= System.currentTimeMillis()) {
//                    finishAffinity()
//                } else {
//                    lastBackPressed = System.currentTimeMillis()
//                    Toast.makeText(this, "Chiqish uchun yana bir marta bosing", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            } else {
//                super.onBackPressed()
//            }


    }


    fun getUsers(): Flow<List<ModelUser>> {
        return dbSourse.getUsers()
    }

    suspend fun updateDataUsers(context: Context) {
        var response: Response<List<ModelUser>>? = null
        try {
            response = withContext(Dispatchers.IO) { service.getUsers() }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (response?.body() != null && response.isSuccessful) {
            dbSourse.insertUser(response.body()!!)
            updateTime = System.currentTimeMillis()

            val preferences: SharedPreferences =
                context.getSharedPreferences("timer", Context.MODE_PRIVATE)
            Log.d(TAG, "updateDataUsers: $updateTime")

            preferences.edit()
                .putLong("LastUpdate", updateTime)
                .apply()
        } else {
            Log.e(TAG, "getPosts: ${response?.errorBody()}")
        }


    }


    fun getPosts(userId: Int): LiveData<List<ModelPost>> {
        return dbSourse.getPost(userId)

    }


    suspend fun updateDataPost(userId: Int) {
        var response: Response<List<ModelPost>>? = null
        try {
            response = withContext(Dispatchers.IO) { service.getPosts(userId) }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        if (response?.body() != null && response.isSuccessful) {
            dbSourse.insertPosts(response.body()!!)
        } else {
            Log.e(TAG, "updataDataPost: ${response?.errorBody()}")
        }
    }


    fun getAllPosts(): LiveData<List<ModelPost>> {
        return dbSourse.getAllPosts()

    }

    suspend fun updateDataAllPosts() {
        var response: Response<List<ModelPost>>? = null
        try {
            response = withContext(Dispatchers.IO) { service.getAllPosts() }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        if (response?.body() != null && response.isSuccessful) {
            dbSourse.insertPosts(response.body()!!)
        } else {
            Log.e(TAG, "updataDataPost: ${response?.errorBody()}")
        }
    }


    fun getComments(postId: Int): LiveData<List<ModelComments>> {
        return dbSourse.getComments(postId)
    }

    @SuppressLint("CheckResult")
    fun updateDataComments(postId: Int) {
        getCommentsOneByOne(postId)
            ?.filter {
                it.id % 2 == 0
            }
            ?.subscribeOn(Schedulers.io())
            ?.subscribe(object : Observer<ModelComments> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(modelComments: ModelComments) {
                    dbSourse.insertComment(modelComments)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: ", e)
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: tugadi")
                }

            })
    }

    private fun getCommentsOneByOne(postId: Int): Observable<ModelComments>? {
        return service.getComments(postId)
            ?.flatMap {
                Observable.fromIterable(it)
            }
    }

//    suspend fun updateDataComments(postId: Int){
//        var response: Response<List<ModelComments>>? = null
//        try {
//            response = withContext(Dispatchers.IO) { service.getComments(postId) }
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//        if (response?.body() != null && response.isSuccessful) {
//            dbSourse.insertComment(response.body()!!)
//        } else {
//            Log.e(TAG, "updateDataPost: ${response?.errorBody()}")
//        }
//    }


    suspend fun postPosts(map: Map<String, String>): ArrayList<ModelPost> {

        val response = service.postPosts(map)

        Log.d(TAG, "postByField: ${response.code()}")
        if (response.isSuccessful && response.body() != null) {
            return arrayListOf(response.body()!!)
        } else {
            Log.e(TAG, "postPosts: ${response.errorBody()}")
        }
        return arrayListOf()


    }


}