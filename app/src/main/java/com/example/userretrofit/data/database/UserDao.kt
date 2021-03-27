package com.example.userretrofit.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.userretrofit.model.ModelComments
import com.example.userretrofit.model.ModelPost
import com.example.userretrofit.model.ModelUser
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: List<ModelUser>)

    @Query("select * from users")
    fun getUsers(): Flow<List<ModelUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<ModelPost>)

    @Query("select * from posts")

    fun getAllPosts(): LiveData<List<ModelPost>>


    @Query("select * from posts where userId=:userId")
    fun getPost(userId:Int):LiveData<List<ModelPost>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insertComment(comments: ModelComments)

    @Query("select * from comments where postId= :postId")
    fun getComments(postId:Int):LiveData<List<ModelComments>>


}