package com.example.librog.data.local

import android.provider.ContactsContract
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface UserDao {
    @Insert
    fun insert(profile: ContactsContract.Profile)

    @Update
    fun update(profile: ContactsContract.Profile)

    @Delete
    fun delete(profile: ContactsContract.Profile)

}