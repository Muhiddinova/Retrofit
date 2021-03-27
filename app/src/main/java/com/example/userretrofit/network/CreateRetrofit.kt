package com.example.userretrofit.network

import com.example.userretrofit.data.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CreateRetrofit {
    private val loggingInterceptor = HttpLoggingInterceptor()
    private var retrofit: Retrofit? = null

//        private val interceptor = HttpLoggingInterceptor()
//
//
//        private val client = OkHttpClient.Builder()
//            .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
//            .connectTimeout(15, TimeUnit.SECONDS)
//            .readTimeout(15, TimeUnit.SECONDS)
//            .writeTimeout(15, TimeUnit.SECONDS)
//            .build()
//
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
fun getRetrofit(interceptor: Interceptor): Retrofit {
    if (retrofit != null) {
        return retrofit!!
    }
    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
//            .addInterceptor(interceptor)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit!!

}

}