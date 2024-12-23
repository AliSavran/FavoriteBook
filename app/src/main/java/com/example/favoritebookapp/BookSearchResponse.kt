package com.example.favoritebookapp

import com.google.gson.annotations.SerializedName

data class BookSearchResponse(

    @SerializedName("docs") val books : List<BookSearchResult>
)

