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


    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    fun getUser(email:String, password:String) : User?

    @Query("SELECT * FROM User WHERE email = :email")
    fun getUserByEmail(email:String) : User?


    @Query("INSERT INTO USER (email, idx, name,profileImgUrl) VALUES (:email, :idx, :name, :profileImgUrl)")
    fun insertUserKakaoLogin(email: String, idx: Int, name: String, profileImgUrl: String)

    //존재할 경우만 DB에 넣어주도록
    @Query("SELECT EXISTS (SELECT * FROM User WHERE idx=:idx)")
    fun isUserExist(idx: Int) : Boolean

    //프로필에 정보 업데이트
    @Query("SELECT profileImgUrl FROM User WHERE idx=:idx")
    fun getUserImg(idx: Int) : String

    @Query("SELECT name FROM User WHERE idx=:idx")
    fun getUserName(idx: Int) : String

    @Query("SELECT introduction FROM User WHERE idx=:idx")
    fun getUserIntro(idx: Int) : String


}