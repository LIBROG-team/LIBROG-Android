package com.example.librog.data.local

import android.provider.ContactsContract
import androidx.room.*
import com.example.librog.data.entities.FlowerData
import com.example.librog.data.entities.User


@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getUserList(): List<User>

}