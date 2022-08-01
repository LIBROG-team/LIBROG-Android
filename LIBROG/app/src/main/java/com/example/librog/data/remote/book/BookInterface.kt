package com.example.librog.data.remote.book

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface BookInterface {

    @GET("/v3/search/book")
    fun getBooksByName(
        @Header("Authorization") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int? = 1,
        @Query("target") target: String? = "title",
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = null


    ): Call<BookResponse>
}