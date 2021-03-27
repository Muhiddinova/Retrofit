package com.example.userretrofit.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
class ModelComments(
    val postId:Int=-1,
    @PrimaryKey
    val id:Int =-1,
    val name:String="",
    val email:String="",
    val body:String=""
)