package com.example.librog.data.local

import android.provider.ContactsContract
import androidx.room.*
import com.example.librog.data.entities.FlowerData
import com.example.librog.data.entities.User


@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getUserList(): List<User>

    @Insert
    fun insert(user:User)

//    @Query("INSERT INTO User (email, password, name) VALUES (:email, :password, :name)")
//    fun insertUser(email: String, password:String, name: String)

    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    fun getUser(email:String, password:String) : User?

//    @Query("SELECT * FROM User WHERE email = :email AND password = :password AND name = :name")
//    fun getUser2(email:String, password:String, name:String) : User?
//
//    @Query("SELECT email,password FROM User WHERE email = :email AND password = :password AND name = :name")
//    fun getUser3(email:String, password:String, name:String) : User?
}