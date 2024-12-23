package com.example.favoritebookapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: BookRepository) : ViewModel() {
    val favoriteBooks = repository.getFavoriteBooks()

    fun removeFromFavorites(book: Book) {
        viewModelScope.launch {
            repository.removeFromFavorites(book)
        }
    }

    class Factory(private val repository: BookRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FavoriteViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}