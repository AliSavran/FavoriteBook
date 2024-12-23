package com.example.favoritebookapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val coverUrl: String? = null,
) : Parcelable