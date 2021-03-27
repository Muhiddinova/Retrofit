package com.example.userretrofit.data

import com.example.userretrofit.model.ModelComments
import com.example.userretrofit.model.ModelPost
import com.example.userretrofit.model.ModelUser
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val BASE_URL = "https://jsonplaceholder.typicode.com"

object Network {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

interface RestApi {

    @GET("/posts?userId=userId")
    suspend fun getPosts(@Query("userId") userId: Int):Response< List<ModelPost>>?

    @GET("/comments?postId=postId")
    fun getComments(@Query("postId") postId: Int): Observable<List<ModelComments>>?

    @GET("/users")
    suspend fun getUsers(): Response<List<ModelUser>>?

    @GET("/posts")
    suspend fun getAllPosts():Response< List<ModelPost>>?


    @FormUrlEncoded
    @POST("posts")
    suspend fun postPosts(@FieldMap map: Map<String, String> ): Response<ModelPost>

}