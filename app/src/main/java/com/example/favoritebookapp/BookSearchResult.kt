package com.example.favoritebookapp

import com.google.gson.annotations.SerializedName

class BookSearchResult (

    @SerializedName("key") val key: String,
    @SerializedName("title") val title: String,
    @SerializedName("author_name") val authors: List<String>? = listOf(),
    @SerializedName("cover_i") val coverId: Int? = null)


