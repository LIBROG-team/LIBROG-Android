package com.example.librog.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text
import java.sql.Timestamp


@Entity
data class User(
    @PrimaryKey(autoGenerate = true) var idx: Int = 0,
    var email: String,
    var password: String?,
    var name: String,
    var salt: String?, //로그인 암호화 도움
    var exp: Int?, //경험치 총합
    var status: String?, //유저 상태
//    var createdAt: Timestamp,
//    var updatedAt: Timestamp,
    var introduction: String?, //자기소개
    var profileImgUrl: String //프사 이미지 url
)
