package com.example.librog.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp


@Entity
data class User(
    var name: String,
    var email: String,
//    var id: String,
    var password: String,
//    var salt: String, //로그인 암호화 도움
//    var exp: Int, //경험치 총합
//    var status: String, //유저 상태
//    var createdAt: Timestamp,
//    var updatedAt: Timestamp,
//    var introduction: String, //자기소개
//    var profileImgUrl: String //프사 이미지 url
)    {@PrimaryKey(autoGenerate = true) var idx: Int = 0}
