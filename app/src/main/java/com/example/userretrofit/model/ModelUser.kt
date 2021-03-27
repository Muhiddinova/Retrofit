package com.example.userretrofit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson

@Entity(tableName = "users")
data class ModelUser(
    @PrimaryKey
    val id: Int = -1,
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val website: String = "",
    @TypeConverters(Converter::class)
    val address: Address? = null
)

data class Address(
    val street: String = "",
    val suite: String = "",
    val city: String = "",
    val zipcode: String = "",

    )

data class company(
    val name: String = "",
    val catchPhrase: String = "",
    val bs: String = ""

)

@TypeConverters
class Converter{
    @TypeConverter
    fun addressToString(model:Address): String {
        return Gson().toJson(model)
    }


    @TypeConverter
    fun stringToAddress(string:String):Address{
        return Gson().fromJson(string,Address::class.java )
    }
}


