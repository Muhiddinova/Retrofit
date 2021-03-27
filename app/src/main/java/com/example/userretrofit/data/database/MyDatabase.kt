package com.example.userretrofit.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.userretrofit.model.*


@Database(entities = [ModelUser::class, ModelPost::class,ModelComments::class], version = 3,exportSchema = false)
@TypeConverters(Converter::class)
abstract  class MyDatabase ():RoomDatabase() {
    abstract val userDao:UserDao

companion object{
    private var INSTANCE: MyDatabase?=null

    fun createDatabase(context: Context):MyDatabase{
        if (INSTANCE != null) {
            return INSTANCE!!
        }

        synchronized(this) {
            INSTANCE = Room.databaseBuilder(context, MyDatabase::class.java, "User").build()
            return INSTANCE!!
        }


    }
}
}