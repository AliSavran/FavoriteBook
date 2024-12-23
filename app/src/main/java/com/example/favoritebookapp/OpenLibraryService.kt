package com.example.favoritebookapp

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibraryService {

    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("limit") limit : Int = 100
    ): BookSearchResponse
    companion object{
        const val BASE_URL = "https://openlibrary.org/"
    }
}