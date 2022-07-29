package com.example.librog.data.remote.book

import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("documents") var documents: ArrayList<Documents>?,
    @SerializedName("meta") val meta: Meta?
)

data class Meta(
    @SerializedName("is_end") var isEnd: Boolean?,
    @SerializedName("pageable_count") var pageableCount: Int?,
    @SerializedName("total_count") var totalCount: Int?
)

data class Documents(

    @SerializedName("authors") var authors: ArrayList<String> = arrayListOf(),
    @SerializedName("contents") var contents: String? = null,
    @SerializedName("datetime") var datetime: String? = null,
    @SerializedName("isbn") var isbn: String? = null,
    @SerializedName("price") var price: Int? = null,
    @SerializedName("publisher") var publisher: String? = null,
    @SerializedName("sale_price") var salePrice: Int? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("thumbnail") var thumbnail: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("translators") var translators: ArrayList<String> = arrayListOf(),
    @SerializedName("url") var url: String? = null

)