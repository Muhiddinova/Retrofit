package com.example.userretrofit.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class ModelPost(
    val userId:Int=-1,
    @PrimaryKey
    val id:Int =-1,
    val title:String="",
    val body:String=""
)