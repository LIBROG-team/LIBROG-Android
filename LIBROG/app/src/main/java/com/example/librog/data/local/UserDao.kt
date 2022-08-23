package com.example.librog.data.local

import android.provider.ContactsContract
import androidx.room.*
import com.example.librog.data.entities.FlowerData
import com.example.librog.data.entities.User
import org.w3c.dom.Text


@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getUserList(): List<User>

    @Insert
    fun insert(user:User)

    @Query("SELECT * FROM User WHERE email = :email")
    fun getUserByEmail(email:String) : User?


    @Query("INSERT INTO USER (email,profileImgUrl) VALUES (:email,:profileImgUrl)")
    fun insertImgUrl(email: String, profileImgUrl: String)

    @Query("UPDATE USER SET profileImgUrl=:profileImgUrl WHERE email=:email")
    fun updateImgUrl(email: String, profileImgUrl: String)

    //프로필에 정보 업데이트
    @Query("SELECT profileImgUrl FROM User WHERE email=:email")
    fun getImgUrl(email: String) : String

    //존재할 경우만 DB에 넣어주도록
    @Query("SELECT EXISTS (SELECT * FROM User WHERE email=:email)")
    fun isUserExist(email: String) : Boolean

    @Query("DELETE FROM User WHERE email=:email")
    fun deleteUser(email: String)

}